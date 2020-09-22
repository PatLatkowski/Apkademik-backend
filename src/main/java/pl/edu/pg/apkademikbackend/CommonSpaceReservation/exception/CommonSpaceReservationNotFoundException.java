package pl.edu.pg.apkademikbackend.CommonSpaceReservation.exception;

import java.time.LocalDate;

public class CommonSpaceReservationNotFoundException extends RuntimeException{
    public CommonSpaceReservationNotFoundException(LocalDate date){
        super("CommonSpace reservation not found "+date);
    }
    public CommonSpaceReservationNotFoundException(long iD){
        super("CommonSpace reservation not found "+iD);
    }
}