package pl.edu.pg.apkademikbackend.noticeboard.exception;

public class NoticeBoardNotFoundException extends RuntimeException{
    public NoticeBoardNotFoundException(long noticeBoard){
        super("Notice board not found "+noticeBoard);
    }
    public NoticeBoardNotFoundException(String noticeBoard){
        super("Notice board not found "+noticeBoard);
    }
}
