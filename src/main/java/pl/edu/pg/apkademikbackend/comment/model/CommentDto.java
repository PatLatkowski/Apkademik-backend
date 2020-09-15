package pl.edu.pg.apkademikbackend.comment.model;


import java.time.LocalDateTime;

public class CommentDto {

    private long id;
    private  String text;
    private LocalDateTime date;
    private  String author;
    private boolean isAuthor;

    public CommentDto(Long id, String text,LocalDateTime date,String authorName,String authorSurname){
        this.id=id;
        this.text=text;
        this.date=date;
        this.author=authorName+" "+authorSurname;
        this.isAuthor=false;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
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
    public boolean getIsAuthor(){return isAuthor;}

    public void setIsAuthor(boolean isAuthor){this.isAuthor=isAuthor;}
}

