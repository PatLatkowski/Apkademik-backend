package pl.edu.pg.apkademikbackend.room.exception;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String number){
        super("Room with that number: "+number+" not found");
    }
    public RoomNotFoundException(long id){
        super("Room with id: "+id+" not found");
    }
}

