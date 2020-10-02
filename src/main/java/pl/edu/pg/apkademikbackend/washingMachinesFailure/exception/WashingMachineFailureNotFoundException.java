package pl.edu.pg.apkademikbackend.washingMachinesFailure.exception;

public class WashingMachineFailureNotFoundException extends RuntimeException{
    public WashingMachineFailureNotFoundException(long id){
        super("WashingMachine failure not found "+ id);
    }
}
