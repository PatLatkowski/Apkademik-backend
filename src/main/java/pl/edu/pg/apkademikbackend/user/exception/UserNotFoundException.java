package pl.edu.pg.apkademikbackend.user.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email){
        super("User not found "+ email);
    }
}
