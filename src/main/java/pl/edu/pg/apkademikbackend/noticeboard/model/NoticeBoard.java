package pl.edu.pg.apkademikbackend.noticeboard.model;

import pl.edu.pg.apkademikbackend.dorm.model.Dorm;

import javax.persistence.*;

@Entity
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="noticeBoard_id")
    private long id;
    @Column
    private  String name;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "dorm_id",referencedColumnName = "dorm_id")
    private Dorm dorm;

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

}
