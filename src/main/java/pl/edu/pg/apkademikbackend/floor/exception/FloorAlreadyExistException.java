package pl.edu.pg.apkademikbackend.floor.exception;

public class FloorAlreadyExistException extends RuntimeException{
    public FloorAlreadyExistException(Integer number){
        super("Floor with that number: "+number+" already exists");
    }
}
