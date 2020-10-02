package pl.edu.pg.apkademikbackend.washingMachine.exception;

public class WashingMachineUnavailableException extends RuntimeException {
    public WashingMachineUnavailableException(long id){
        super("WashingMachine is currently unavailable"+id);
    }
}
