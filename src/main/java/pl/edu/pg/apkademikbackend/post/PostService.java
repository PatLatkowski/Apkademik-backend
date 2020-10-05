package pl.edu.pg.apkademikbackend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.noticeboard.NoticeBoardService;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.post.exception.PostNotFoundException;
import pl.edu.pg.apkademikbackend.post.exception.PostNotOnThisNoticeBoardException;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.model.PostDto;
import pl.edu.pg.apkademikbackend.post.repository.PostRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostService {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final PostRepository postRepository;

    private final NoticeBoardService noticeBoardService;

    @Autowired
    public PostService( JwtUserDetailsService jwtUserDetailsService,PostRepository postRepository,NoticeBoardService noticeBoardService){
        this.jwtUserDetailsService=jwtUserDetailsService;
        this.postRepository=postRepository;
        this.noticeBoardService=noticeBoardService;

    }

    public List<Post> savePost(PostDto newPost,long noticeBoard, String userEmail){
        UserDao user=jwtUserDetailsService.getUser(userEmail);
        if(user==null)
            throw new UserNotFoundException(userEmail);

        NoticeBoard testNoticeBoard= noticeBoardService.getNoticeBoardById(noticeBoard);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoard);

        Integer countPosts= postRepository.countPosts(testNoticeBoard.getId());

        Post post=new Post();

        LocalDateTime date=LocalDateTime.now();
        post.setDate(date);
        post.setPage(countPosts/10);
        post.setUser(user);
        post.setNoticeBoard(testNoticeBoard);
        post.setText(newPost.getText());
        post.setTitle(newPost.getTitle());

        List<Post> posts=testNoticeBoard.getPosts();
        posts.add(post);
        postRepository.save(post);
        return posts;
    }

    public List<PostDto> getPosts(long noticeBoard){
        NoticeBoard testNoticeBoard= noticeBoardService.getNoticeBoardById(noticeBoard);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoard);

        List  <Post> posts=postRepository.findAllByDate(testNoticeBoard.getId());
        List<PostDto> postsData=new ArrayList<PostDto>();
        for (Post post:posts ) {
            PostDto newPostDto= returnPostData(post);
            postsData.add(newPostDto);
        }

        return postsData;
    }

    public List<PostDto> getPostsFromPage(long noticeBoard,Integer page){
        NoticeBoard testNoticeBoard=noticeBoardService.getNoticeBoardById(noticeBoard);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoard);

        List  <Post> posts=postRepository.findAllByDateAndPage(testNoticeBoard.getId(),page);
        List<PostDto> postsData=new ArrayList<PostDto>();
        for (Post post:posts ) {
            PostDto newPostDto= returnPostData(post);
            postsData.add(newPostDto);
        }

        return postsData;
    }

    public PostDto getPost(long id,long noticeBoardId, String userEmail ){

        NoticeBoard testNoticeBoard= noticeBoardService.getNoticeBoardById(noticeBoardId);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoardId);

        Post post=postRepository.findById(id);
        if(post==null)
            throw new PostNotFoundException(id);


        if(post.getNoticeBoard().getId()!=testNoticeBoard.getId())
            throw new PostNotOnThisNoticeBoardException(id,noticeBoardId);

        PostDto postDto=returnPostData(post);

        if(post.getUser()== jwtUserDetailsService.getUser(userEmail))
            postDto.setIsAuthor(true);

        return postDto;

    }

    public Integer getPages(long noticeBoard){
        NoticeBoard testNoticeBoard= noticeBoardService.getNoticeBoardById(noticeBoard);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoard);

        return postRepository.getPage(testNoticeBoard.getId());
    }

    public Post getPostById(long id){
        Post post = postRepository.findById(id);
        if(post == null)
            throw new PostNotFoundException(id);
        return post;
    }

    public Post updatePostById(long id, PostDto newPost){
        Post post = postRepository.findById(id);
        if(post == null)
            throw new PostNotFoundException(id);
        if(newPost.getTitle()!=null)
            post.setTitle(newPost.getTitle());
        if(newPost.getText()!=null)
            post.setText(newPost.getText());
        if(newPost.getDate()!=null)
            post.setDate(newPost.getDate());
        return postRepository.save(post);
    }

    public void deletePostById(long id){
        Post post = postRepository.findById(id);
        if(post == null)
            throw new  PostNotFoundException(id);
        postRepository.delete(post);
    }

    public PostDto returnPostData(Post post){
        PostDto postDto=new PostDto(post.getId(),post.getTitle(),post.getText(),post.getDate(),post.getUser().getName(),post.getUser().getSurname(),post.getUser().getRoom().getNumber());
        return postDto;
    }
}