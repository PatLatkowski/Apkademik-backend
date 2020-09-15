package pl.edu.pg.apkademikbackend.comment.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String commentName){
        super("Comment not found "+commentName);
    }
}
