package pl.edu.pg.apkademikbackend.dorm;

public class DormAlreadyExistException extends RuntimeException{
    public DormAlreadyExistException(String dormName){
        super("Dorm with name: "+dormName+" already exists");
    }
}

