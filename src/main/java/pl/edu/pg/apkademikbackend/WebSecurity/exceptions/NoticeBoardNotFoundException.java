package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

public class NoticeBoardNotFoundException extends RuntimeException{
    public NoticeBoardNotFoundException(String noticeBoardName){
        super("Notice board not found "+noticeBoardName);
    }
}
