package pl.edu.pg.apkademikbackend.commonSpace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonSpaceNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CommonSpaceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String CommonSpaceNotFoundHandler(CommonSpaceNotFoundException ex){
        return ex.getMessage();
    }
}

