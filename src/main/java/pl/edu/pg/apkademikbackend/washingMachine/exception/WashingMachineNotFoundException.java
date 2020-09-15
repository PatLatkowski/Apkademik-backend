package pl.edu.pg.apkademikbackend.washingMachine.exception;

public class WashingMachineNotFoundException extends RuntimeException{
    public WashingMachineNotFoundException(Integer number){
        super("WashingMachine not found "+number);
    }
}

