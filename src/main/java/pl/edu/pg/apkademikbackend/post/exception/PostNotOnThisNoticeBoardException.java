package pl.edu.pg.apkademikbackend.post.exception;

public class PostNotOnThisNoticeBoardException extends RuntimeException{
    public PostNotOnThisNoticeBoardException(String postName,String noticeBoardName){
        super("Post: "+postName +" not found on noticeBoard: "+noticeBoardName);
    }
}
