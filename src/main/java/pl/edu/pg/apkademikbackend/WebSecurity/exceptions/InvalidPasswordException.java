package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(){super("Invalid password");}
}
