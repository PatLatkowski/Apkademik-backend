package pl.edu.pg.apkademikbackend.commonSpace.exception;

public class CommonSpaceAlreadyExistException extends RuntimeException{
    public CommonSpaceAlreadyExistException(Integer number){
        super("CommonSpace with that number: "+number+" already exists");
    }
}

