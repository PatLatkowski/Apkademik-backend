package pl.edu.pg.apkademikbackend.post.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.apkademikbackend.comment.model.Comment;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column
    private Integer page;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserDao user;

    @ManyToOne
    @JoinColumn(name = "noticeBoard_id",referencedColumnName = "id")
    private NoticeBoard noticeBoard;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

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

    public Integer getPage(){
        return page;
    }
    public void setPage(Integer page){
        this.page=page;
    }

    public UserDao getUser(){
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
