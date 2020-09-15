package pl.edu.pg.apkademikbackend.washingMachine.exception;

public class WashingMachineAlreadyExistException extends RuntimeException{
    public WashingMachineAlreadyExistException(Integer number){
        super("WashingMachine with the number: " + number+  " already exists");
    }
}
