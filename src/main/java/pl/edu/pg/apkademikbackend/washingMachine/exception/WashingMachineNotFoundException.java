package pl.edu.pg.apkademikbackend.washingMachine.exception;

public class WashingMachineNotFoundException extends RuntimeException{
    public WashingMachineNotFoundException(long number){
        super("WashingMachine not found "+number);
    }
}

