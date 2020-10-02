package pl.edu.pg.apkademikbackend.washingMachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WashingMachineUnavailableAdvice {
    @ResponseBody
    @ExceptionHandler(WashingMachineUnavailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String WashingMachineUnavailableHandler(WashingMachineUnavailableException ex){
        return ex.getMessage();
    }
}
