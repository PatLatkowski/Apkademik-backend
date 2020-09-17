package pl.edu.pg.apkademikbackend.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.comment.exception.CommentNotFoundException;
import pl.edu.pg.apkademikbackend.comment.model.Comment;
import pl.edu.pg.apkademikbackend.comment.repository.CommentRepository;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationNotFoundException;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

@Component
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

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
}
