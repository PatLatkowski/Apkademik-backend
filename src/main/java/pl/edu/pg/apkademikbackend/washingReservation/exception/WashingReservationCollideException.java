package pl.edu.pg.apkademikbackend.washingReservation.exception;

import java.time.LocalDateTime;

public class WashingReservationCollideException extends RuntimeException {
    public WashingReservationCollideException(LocalDateTime localDateTime){
        super("Washing reservation at: "+localDateTime+" already exists");
    }
}
