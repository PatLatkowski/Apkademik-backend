package pl.edu.pg.apkademikbackend.dorm;

public class DormNotFoundException extends RuntimeException{
    public DormNotFoundException(String dormName){
        super("Dorm not found "+dormName);
    }
}

