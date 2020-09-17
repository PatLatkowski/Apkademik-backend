package pl.edu.pg.apkademikbackend.washingReservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WashingReservationNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(WashingReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String WashingReservationNotFoundHandler(WashingReservationNotFoundException ex){
        return ex.getMessage();
    }

}
