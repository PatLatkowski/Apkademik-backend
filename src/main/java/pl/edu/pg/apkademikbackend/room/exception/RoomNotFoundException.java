package pl.edu.pg.apkademikbackend.room.exception;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(Integer number){
        super("Room with that number: "+number+" not found");
    }
}

