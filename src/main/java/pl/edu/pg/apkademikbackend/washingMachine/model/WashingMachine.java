package pl.edu.pg.apkademikbackend.washingMachine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="washing_machine")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class WashingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int number;
    @Column
    private WashingMachineStatus status;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "washing_machine_id")
    @JsonIgnore
    private List<WashingReservation> washingReservations = new ArrayList<>();

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

    public WashingMachineStatus getStatus() {
        return status;
    }

    public void setStatus(WashingMachineStatus status) {
        this.status = status;
    }

    public List<WashingReservation> getWashingReservations() {
        return washingReservations;
    }

    public void setWashingReservations(List<WashingReservation> washingReservations) {
        this.washingReservations = washingReservations;
    }
    public void addWashingReservations(List<WashingReservation> newWashingReservations){
        this.washingReservations.addAll(newWashingReservations);
    }

}
