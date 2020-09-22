package pl.edu.pg.apkademikbackend.room.exception;

public class RoomAlreadyExistException extends RuntimeException{
    public RoomAlreadyExistException(long number){
        super("Room with that number: "+number+" already exists");
    }
    public RoomAlreadyExistException(String number){
        super("Room with that number: "+number+" already exists");
    }
}
