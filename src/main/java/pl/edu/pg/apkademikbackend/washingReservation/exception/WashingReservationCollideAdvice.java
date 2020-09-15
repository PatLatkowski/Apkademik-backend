package pl.edu.pg.apkademikbackend.washingReservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineAlreadyExistException;

@ControllerAdvice
public class WashingReservationCollideAdvice {
    @ResponseBody
    @ExceptionHandler(WashingReservationCollideException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String WashingReservationCollideHandler(WashingReservationCollideException ex){
        return ex.getMessage();
    }
}
