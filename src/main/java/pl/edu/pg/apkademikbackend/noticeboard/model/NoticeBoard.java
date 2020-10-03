package pl.edu.pg.apkademikbackend.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.post.model.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private  String name;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "dorm_id",referencedColumnName = "id")
    @JsonIgnore
    private Dorm dorm;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "noticeBoard_id")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    public NoticeBoard(){

    }
    public NoticeBoard(long id,String name){
        this.id=id;
        this.name=name;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public Dorm getDorm(){
          return dorm;
      }
    public void setDorm(Dorm dorm){
           this.dorm=dorm;
       }

    public List<Post> getPosts() {
        return posts;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
