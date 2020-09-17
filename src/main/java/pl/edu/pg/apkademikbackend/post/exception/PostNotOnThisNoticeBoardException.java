package pl.edu.pg.apkademikbackend.post.exception;

public class PostNotOnThisNoticeBoardException extends RuntimeException{
    public PostNotOnThisNoticeBoardException(long post,String noticeBoardName){
        super("Post: "+post +" not found on noticeBoard: "+noticeBoardName);
    }
}
