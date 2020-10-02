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

    public Comment saveComment(Comment newComment, long postId, String userEmail){
        LocalDateTime date=LocalDateTime.now();
        newComment.setDate(date);

        newComment.setUser(jwtUserDetailsService.getUser(userEmail));

        Post post=postService.getPostById(postId);
        if(post==null)
            throw new PostNotFoundException(postId);

        newComment.setPost(post);
        List<Comment> comments=post.getComments();
        comments.add(newComment);
        commentRepository.save(newComment);
        return newComment;
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

            if(comment.getUsers()==thisUser)
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

    public Comment updateCommentById(long id, Comment newComment){
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

    private CommentDto returnCommentData(Comment comment){
        CommentDto newComment=new CommentDto(comment.getId(),comment.getText(),comment.getDate(),comment.getUsers().getName(),comment.getUsers().getSurname());
        return newComment;
    }

}
