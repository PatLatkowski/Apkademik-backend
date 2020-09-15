package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidPasswordAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String InvalidPasswordHandler(InvalidPasswordException ex){
        return ex.getMessage();
    }
}
