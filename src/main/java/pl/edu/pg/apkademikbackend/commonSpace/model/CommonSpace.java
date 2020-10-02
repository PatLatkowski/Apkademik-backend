package pl.edu.pg.apkademikbackend.commonSpace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservation;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="common_space")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class CommonSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int number;
    @Column
    private int size;
    @Column
    private String name;
    @Column
    private CommonSpaceType type;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "common_space_id")
    @JsonIgnore
    private List<WashingMachine> washingMachines  = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Floor floor;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "common_space_id")
    @JsonIgnore
    private List<CommonSpaceReservation> commonSpaceReservations  = new ArrayList<>();

    public CommonSpace() {
    }

    public CommonSpace(int number, int size, String name, CommonSpaceType type) {
        this.number = number;
        this.size = size;
        this.name = name;
        this.type = type;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WashingMachine> getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(List<WashingMachine> washingMachines) {
        this.washingMachines = washingMachines;
    }

    public CommonSpaceType getType() {
        return type;
    }

    public void setType(CommonSpaceType type) {
        this.type = type;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public List<CommonSpaceReservation> getCommonSpaceReservations() {
        return commonSpaceReservations;
    }

    public void setCommonSpaceReservations(List<CommonSpaceReservation> commonSpaceReservations) {
        this.commonSpaceReservations = commonSpaceReservations;
    }

    public void addWashingMachine(WashingMachine washingMachine){
        this.washingMachines.add(washingMachine);
    }

    public void removeWashingMachine(WashingMachine washingMachine){
        this.washingMachines.remove(washingMachine);
    }

    public void addCommonSpaceReservations(List<CommonSpaceReservation> commonSpaceReservations1){
        this.commonSpaceReservations.addAll(commonSpaceReservations1);
    }

    public void removeCommonSpaceReservation(CommonSpaceReservation commonSpaceReservation){
        this.commonSpaceReservations.remove(commonSpaceReservation);
    }

    

}
