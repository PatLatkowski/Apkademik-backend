package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoticeBoardNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(NoticeBoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String NoticeBoardNotFoundHandler(NoticeBoardNotFoundException ex){
        return ex.getMessage();
    }
}