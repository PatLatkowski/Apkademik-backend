package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String postName){
        super("Post not found "+postName);
    }
}
