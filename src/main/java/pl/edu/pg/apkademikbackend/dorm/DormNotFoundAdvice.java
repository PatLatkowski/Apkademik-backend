package pl.edu.pg.apkademikbackend.dorm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleNotFoundException;

@ControllerAdvice
public class DormNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(DormNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String DormNotFoundHandler(DormNotFoundException ex){
        return ex.getMessage();
    }
}
