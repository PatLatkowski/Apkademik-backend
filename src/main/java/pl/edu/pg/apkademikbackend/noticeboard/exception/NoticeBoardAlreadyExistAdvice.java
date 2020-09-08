package pl.edu.pg.apkademikbackend.noticeboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoticeBoardAlreadyExistAdvice {
    @ResponseBody
    @ExceptionHandler(NoticeBoardAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String NoticeBoardAlreadyExistHandler(NoticeBoardAlreadyExistException ex){
        return ex.getMessage();
    }
}
