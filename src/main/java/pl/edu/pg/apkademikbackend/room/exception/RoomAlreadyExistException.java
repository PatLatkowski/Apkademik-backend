package pl.edu.pg.apkademikbackend.room.exception;

public class RoomAlreadyExistException extends RuntimeException{
    public RoomAlreadyExistException(Integer number){
        super("Room with that number: "+number+" already exists");
    }
}
