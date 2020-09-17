package pl.edu.pg.apkademikbackend.post.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(long post){
        super("Post not found "+post);
    }
}
