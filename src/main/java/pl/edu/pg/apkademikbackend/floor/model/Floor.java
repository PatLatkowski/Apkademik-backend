package pl.edu.pg.apkademikbackend.floor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.room.model.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="floor")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int number;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "floor_id")
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "floor_id")
    @JsonIgnore
    private List<CommonSpace> commonSpaces = new ArrayList<>();

    public Floor() {
    }

    public Floor(int number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<CommonSpace> getCommonSpaces() {
        return commonSpaces;
    }

    public void setCommonSpaces(List<CommonSpace> commonSpaces) {
        this.commonSpaces = commonSpaces;
    }

    public void addCommonSpace(CommonSpace commonSpace){
        commonSpaces.add(commonSpace);
        commonSpace.setFloor(this);
    }

    public void removeCommonSpace(CommonSpace commonSpace){
        commonSpaces.remove(commonSpace);
        commonSpace.setFloor(null);
    }


}
