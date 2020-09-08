package pl.edu.pg.apkademikbackend.noticeboard.exception;

public class NoticeBoardAlreadyExistException extends RuntimeException{
    public NoticeBoardAlreadyExistException(String noticeBoardName){
        super("Role with name: "+noticeBoardName+" already exists");
    }
}
