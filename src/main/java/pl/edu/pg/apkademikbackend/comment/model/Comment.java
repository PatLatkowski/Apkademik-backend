package pl.edu.pg.apkademikbackend.comment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String text;
    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonIgnore
    private UserDao user;

    public Comment(){

    }
    public Comment(long id,String text,LocalDateTime date){
        this.id=id;
        this.text=text;
        this.date=date;
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

    public Post getPost(){
        return post;
    }
    public void setPost(Post post){
        this.post=post;
    }

    public UserDao getUser(){
        return user;
    }
    public void setUser(UserDao user){
        this.user=user;
    }

}