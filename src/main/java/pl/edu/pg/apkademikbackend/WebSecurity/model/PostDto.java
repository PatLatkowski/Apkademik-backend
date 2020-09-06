package pl.edu.pg.apkademikbackend.WebSecurity.model;

import java.time.LocalDateTime;

public class PostDto {

    private long id;
    private  String title;
    private  String text;
    private LocalDateTime date;
    private  String author;

    public PostDto(Long id, String title,String text,LocalDateTime date,String authorName,String authorSurname){
        this.id=id;
        this.title=title;
        this.text=text;
        this.date=date;
        this.author=authorName+" "+authorSurname;
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


}
