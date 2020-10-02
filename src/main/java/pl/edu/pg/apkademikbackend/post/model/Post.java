package pl.edu.pg.apkademikbackend.post.model;



import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column
    private  String title;
    @Column
    private  String text;
    @Column
    private  LocalDateTime date;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserDao user;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "noticeBoard_id",referencedColumnName = "id")
    private NoticeBoard noticeBoard;

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

    public NoticeBoard getNoticeBoard(){
        return noticeBoard;
    }
    public void setNoticeBoard(NoticeBoard noticeBoard){
        this.noticeBoard=noticeBoard;
    }

    public UserDao getUser() {
        return user;
    }
}
