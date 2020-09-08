package pl.edu.pg.apkademikbackend.noticeboard.exception;

public class NoticeBoardNotFoundException extends RuntimeException{
    public NoticeBoardNotFoundException(String noticeBoardName){
        super("Notice board not found "+noticeBoardName);
    }
}
