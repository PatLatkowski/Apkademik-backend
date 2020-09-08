package pl.edu.pg.apkademikbackend.post.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String postName){
        super("Post not found "+postName);
    }
}
