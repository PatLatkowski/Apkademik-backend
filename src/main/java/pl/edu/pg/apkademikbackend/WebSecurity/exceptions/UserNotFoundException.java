package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super("User not found "+ email);
    }
}
