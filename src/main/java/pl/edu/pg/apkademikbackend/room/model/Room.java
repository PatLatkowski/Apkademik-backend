package pl.edu.pg.apkademikbackend.room.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private long id;
    @Column
    private Integer number;
    @Column
    private int size;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private List<UserDao> residents = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<UserDao> getResidents() {
        return residents;
    }

    public void setResidents(List<UserDao> residents) {
        this.residents = residents;
    }

    public void addResident(UserDao user){
        residents.add(user);
        user.setRoom(this);
    }

    public void removeResident(UserDao user){
        residents.remove(user);
        user.setRoom(null);
    }
}
