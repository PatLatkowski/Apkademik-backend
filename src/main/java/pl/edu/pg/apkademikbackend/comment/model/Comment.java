package pl.edu.pg.apkademikbackend.comment.model;


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

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "post_id",referencedColumnName = "post_id")
    private Post post;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserDao user;


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

    public UserDao getUsers(){
        return user;
    }
    public void setUser(UserDao user){
        this.user=user;
    }
}