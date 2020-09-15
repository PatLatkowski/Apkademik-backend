package pl.edu.pg.apkademikbackend.dorm.exception;

public class DormNotFoundException extends RuntimeException{
    public DormNotFoundException(String dormName){
        super("Dorm not found "+dormName);
    }
    public DormNotFoundException(long iD){
        super("Dorm not found "+iD);
    }
}

