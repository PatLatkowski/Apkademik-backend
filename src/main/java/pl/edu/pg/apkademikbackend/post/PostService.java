package pl.edu.pg.apkademikbackend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;
import pl.edu.pg.apkademikbackend.post.exception.PostNotFoundException;
import pl.edu.pg.apkademikbackend.post.exception.PostNotOnThisNoticeBoardException;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.model.PostDto;
import pl.edu.pg.apkademikbackend.post.repository.PostRepository;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    public Post savePost(Post newPost,String noticeBoard, String userEmail){
        LocalDateTime date=LocalDateTime.now();
        newPost.setDate(date);

        newPost.setUser(userRepository.findByEmail(userEmail));

        NoticeBoard testNoticeBoard= noticeBoardRepository.findByName(noticeBoard);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoard);

        newPost.setNoticeBoard(testNoticeBoard);

        Integer countPosts= postRepository.countPosts(testNoticeBoard.getId());

        newPost.setPage(countPosts/10);
        List<Post> posts=testNoticeBoard.getPosts();
        posts.add(newPost);
        postRepository.save(newPost);

        return newPost;
    }

    public List<PostDto> getPosts(String noticeBoard){
        NoticeBoard testNoticeBoard= noticeBoardRepository.findByName(noticeBoard);
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

    public List<PostDto> getPostsFromPage(String noticeBoard,Integer page){
        NoticeBoard testNoticeBoard= noticeBoardRepository.findByName(noticeBoard);
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

    public PostDto getPost(long id,String noticeBoard, String userEmail ){

        NoticeBoard testNoticeBoard= noticeBoardRepository.findByName(noticeBoard);
        if(testNoticeBoard == null)
            throw new NoticeBoardNotFoundException(noticeBoard);

        Post post=postRepository.findById(id);
        if(post==null)
            throw new PostNotFoundException(id);


        if(post.getNoticeBoard().getId()!=testNoticeBoard.getId())
            throw new PostNotOnThisNoticeBoardException(id,noticeBoard);

        PostDto postDto=returnPostData(post);

        if(post.getUser()==userRepository.findByEmail(userEmail))
            postDto.setIsAuthor(true);

        return postDto;

    }

    public Integer getPages(String noticeBoard){
        NoticeBoard testNoticeBoard= noticeBoardRepository.findByName(noticeBoard);
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

    public Post updatePostById(long id, Post newPost){
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

    private PostDto returnPostData(Post post){
        PostDto postDto=new PostDto(post.getId(),post.getTitle(),post.getText(),post.getDate(),post.getUser().getName(),post.getUser().getSurname(),post.getUser().getRoom().getNumber());
        return postDto;
    }
}