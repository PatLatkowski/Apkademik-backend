package pl.edu.pg.apkademikbackend.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PostNotOnThisNoticeBoardAdvice {
    @ResponseBody
    @ExceptionHandler(PostNotOnThisNoticeBoardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String PostNotOnThisNoticeBoardHandler(PostNotOnThisNoticeBoardException ex){
        return ex.getMessage();
    }
}