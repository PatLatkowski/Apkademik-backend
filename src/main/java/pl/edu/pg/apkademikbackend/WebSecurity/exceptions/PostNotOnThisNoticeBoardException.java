package pl.edu.pg.apkademikbackend.WebSecurity.exceptions;

public class PostNotOnThisNoticeBoardException extends RuntimeException{
    public PostNotOnThisNoticeBoardException(String postName,String noticeBoardName){
        super("Post: "+postName +" not found on noticeBoard: "+noticeBoardName);
    }
}
