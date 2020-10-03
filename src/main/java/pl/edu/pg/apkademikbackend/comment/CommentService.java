package pl.edu.pg.apkademikbackend.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.comment.exception.CommentNotFoundException;
import pl.edu.pg.apkademikbackend.comment.model.Comment;
import pl.edu.pg.apkademikbackend.comment.model.CommentDto;
import pl.edu.pg.apkademikbackend.comment.repository.CommentRepository;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.post.PostService;
import pl.edu.pg.apkademikbackend.post.exception.PostNotFoundException;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.model.PostDto;
import pl.edu.pg.apkademikbackend.post.repository.PostRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationNotFoundException;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentService {

    private final CommentRepository commentRepository;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository,JwtUserDetailsService jwtUserDetailsService,PostService postService){
        this.commentRepository=commentRepository;
        this.jwtUserDetailsService=jwtUserDetailsService;
        this.postService=postService;
    }

    public List<Comment> saveComment(CommentDto newComment, long postId, String userEmail){
        UserDao user=jwtUserDetailsService.getUser(userEmail);
        if(user==null)
            throw new UserNotFoundException(userEmail);

        Post post=postService.getPostById(postId);
        if(post==null)
            throw new PostNotFoundException(postId);

        List<Comment> comments=post.getComments();
        Comment comment=new Comment();
        LocalDateTime date=LocalDateTime.now();
        comment.setDate(date);
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(newComment.getText());

        comments.add(comment);
        commentRepository.save(comment);
        return comments;
    }

    public List<CommentDto> getComments(long postId,String userEmail){
        Post testPost= postService.getPostById(postId);
        if(testPost == null)
            throw new PostNotFoundException(postId);

        UserDao thisUser=jwtUserDetailsService.getUser(userEmail);

        Iterable<Comment> comments=commentRepository.findAllByDate(testPost.getId());
        List<CommentDto> commentsData=new ArrayList<CommentDto>();
        for (Comment comment:comments ) {
            CommentDto newComment=returnCommentData(comment);

            if(comment.getUser()==thisUser)
                newComment.setIsAuthor(true);
            commentsData.add(newComment);
        }

        return commentsData;
    }


    public Comment getCommentById(long id){
        Comment comment = commentRepository.findById(id);
        if(comment == null)
            throw new CommentNotFoundException(id);
        return comment;
    }

    public Comment updateCommentById(long id, CommentDto newComment){
        Comment comment = commentRepository.findById(id);
        if(comment == null)
            throw new WashingReservationNotFoundException(id);
        if(newComment.getText()!=null)
            comment.setText(newComment.getText());
        if(newComment.getDate()!=null)
            comment.setDate(newComment.getDate());
        return commentRepository.save(comment);
    }

    public void deleteCommentById(long id){
        Comment comment =commentRepository.findById(id);
        if(comment == null)
            throw new  CommentNotFoundException(id);
        commentRepository.delete(comment);
    }

    public CommentDto returnCommentData(Comment comment){
        CommentDto newComment=new CommentDto(comment.getId(),comment.getText(),comment.getDate(),comment.getUser().getName(),comment.getUser().getSurname());
        return newComment;
    }

}
