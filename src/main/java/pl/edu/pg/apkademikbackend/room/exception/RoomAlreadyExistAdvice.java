package pl.edu.pg.apkademikbackend.room.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoomAlreadyExistAdvice {
    @ResponseBody
    @ExceptionHandler(RoomAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String RoomAlreadyExistHandler(RoomAlreadyExistException ex){
        return ex.getMessage();
    }
}

