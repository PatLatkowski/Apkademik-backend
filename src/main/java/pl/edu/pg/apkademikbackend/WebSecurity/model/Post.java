package pl.edu.pg.apkademikbackend.WebSecurity.model;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private  String title;
    @Column
    private  String text;
    @Column
    private  LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDao user;


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

    public UserDao getUsers(){
        return user;
    }
    public void setUser(UserDao user){
        this.user=user;
    }

}
