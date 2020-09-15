package pl.edu.pg.apkademikbackend.washingMachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WashingMachineNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(WashingMachineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String WashingMachineNotFoundHandler(WashingMachineNotFoundException ex){
        return ex.getMessage();
    }
}

