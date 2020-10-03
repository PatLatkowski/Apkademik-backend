package pl.edu.pg.apkademikbackend.post.model;

import java.time.LocalDateTime;

public class PostDto {
    private long id;
    private  String title;
    private  String text;
    private LocalDateTime date;
    private  String author;
    private String room;
    private boolean isAuthor;
    private  long noticeBoardId;
    private  long userId;

    public PostDto(){
    }

    public PostDto(Long id, String title,String text,LocalDateTime date,String authorName,String authorSurname,String room){
        this.id=id;
        this.title=title;
        this.text=text;
        this.date=date;
        this.author=authorName+" "+authorSurname;
        this.room=room;
        this.isAuthor=false;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text=text;
    }

    public LocalDateTime getDate(){
        return date;
    }
    public void setDate(LocalDateTime date){
        this.date=date;
    }

    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }

    public String getRoom(){
        return room;
    }
    public void setRoom(String room){
        this.room=room;
    }

    public boolean getIsAuthor(){return isAuthor;}
    public void setIsAuthor(boolean isAuthor){this.isAuthor=isAuthor;}

    public long getNoticeBoardId() {
        return noticeBoardId;
    }
    public void setNoticeBoardId(long noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

}