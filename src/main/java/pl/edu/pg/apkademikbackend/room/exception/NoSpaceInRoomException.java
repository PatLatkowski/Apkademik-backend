package pl.edu.pg.apkademikbackend.room.exception;

public class NoSpaceInRoomException extends RuntimeException{
    public NoSpaceInRoomException(long id){
        super("There is no more space in room with id: "+ id);
    }
}