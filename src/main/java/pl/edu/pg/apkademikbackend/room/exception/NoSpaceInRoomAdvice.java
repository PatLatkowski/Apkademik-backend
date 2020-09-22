package pl.edu.pg.apkademikbackend.room.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoSpaceInRoomAdvice {
    @ResponseBody
    @ExceptionHandler(NoSpaceInRoomException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String NoSpaceInRoomHandler(NoSpaceInRoomException ex){
        return ex.getMessage();
    }
}

