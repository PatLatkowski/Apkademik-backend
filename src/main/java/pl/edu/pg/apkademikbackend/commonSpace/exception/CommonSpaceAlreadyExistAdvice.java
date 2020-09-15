package pl.edu.pg.apkademikbackend.commonSpace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonSpaceAlreadyExistAdvice {
    @ResponseBody
    @ExceptionHandler(CommonSpaceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String CommonSpaceAlreadyExistHandler(CommonSpaceAlreadyExistException ex){
        return ex.getMessage();
    }
}

