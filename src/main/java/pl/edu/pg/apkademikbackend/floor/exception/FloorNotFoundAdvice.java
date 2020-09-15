package pl.edu.pg.apkademikbackend.floor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FloorNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(FloorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String FloorNotFoundHandler(FloorNotFoundException ex){
        return ex.getMessage();
    }
}
