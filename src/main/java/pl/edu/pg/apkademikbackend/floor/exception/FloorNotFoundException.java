package pl.edu.pg.apkademikbackend.floor.exception;

public class FloorNotFoundException extends RuntimeException{
    public FloorNotFoundException(Integer number){
        super("Floor not found "+number);
    }
    public FloorNotFoundException(long number){
        super("Floor not found "+number);
    }
}


