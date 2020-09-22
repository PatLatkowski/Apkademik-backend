package pl.edu.pg.apkademikbackend.CommonSpaceReservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonSpaceReservationCollideAdvice {
    @ResponseBody
    @ExceptionHandler(CommonSpaceReservationCollideException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String CommonSpaceReservationCollideHandler(CommonSpaceReservationCollideException ex){
        return ex.getMessage();
    }
}
