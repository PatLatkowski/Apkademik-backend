package pl.edu.pg.apkademikbackend.washingReservation.exception;

public class WashingReservationNotFoundException extends RuntimeException{
    public WashingReservationNotFoundException(long number){
        super("WashingReservation not found "+number);
    }
}
