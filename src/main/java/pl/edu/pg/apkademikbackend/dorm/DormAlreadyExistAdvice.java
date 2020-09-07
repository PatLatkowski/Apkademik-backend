package pl.edu.pg.apkademikbackend.dorm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleAlreadyExistException;

@ControllerAdvice
public class DormAlreadyExistAdvice {
    @ResponseBody
    @ExceptionHandler(DormAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String DormAlreadyExistHandler(DormAlreadyExistException ex){
        return ex.getMessage();
    }
}
