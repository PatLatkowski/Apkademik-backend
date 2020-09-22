package pl.edu.pg.apkademikbackend.CommonSpaceReservation.exception;

import java.time.LocalDateTime;

public class CommonSpaceReservationCollideException extends RuntimeException {
    public CommonSpaceReservationCollideException(LocalDateTime localDateTime){
        super("CommonSpace reservation at: "+localDateTime+" already exists");
    }
}
