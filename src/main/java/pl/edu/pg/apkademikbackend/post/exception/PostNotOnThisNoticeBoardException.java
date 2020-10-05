package pl.edu.pg.apkademikbackend.post.exception;

public class PostNotOnThisNoticeBoardException extends RuntimeException{
    public PostNotOnThisNoticeBoardException(long post,long noticeBoard){
        super("Post: "+post +" not found on noticeBoard: "+noticeBoard);
    }
}
