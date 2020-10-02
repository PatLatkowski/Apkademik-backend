package pl.edu.pg.apkademikbackend.dorm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dorm")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Dorm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private int floorCount;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "dorm_id")
    @JsonIgnore
    private List<Floor> floors = new ArrayList<>();
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "dorm_id")
    @JsonIgnore
    private List<NoticeBoard> noticeBoards  = new ArrayList<>();

    public Dorm() {
    }

    public Dorm(long id, String name, String address, int floorCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.floorCount = floorCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<NoticeBoard> getNoticeBoards() {
        return noticeBoards;
    }

    public void setNoticeBoards(List<NoticeBoard> noticeBoards) {
        this.noticeBoards = noticeBoards;
    }
}