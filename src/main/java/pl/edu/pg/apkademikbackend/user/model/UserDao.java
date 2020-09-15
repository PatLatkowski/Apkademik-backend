package pl.edu.pg.apkademikbackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.model.*;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    @JsonIgnore
    private Set<Role> roles;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private List<WashingReservation> washingReservations  = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private List<CommonSpaceReservation> commonSpaceReservations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dorm_id")
    @JsonIgnore
    private Dorm dorm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Room room;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String fullName) {
        this.surname = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<WashingReservation> getWashingReservations() {
        return washingReservations;
    }

    public void setWashingReservations(List<WashingReservation> washingReservations) {
        this.washingReservations = washingReservations;
    }

    public List<CommonSpaceReservation> getCommonSpaceReservations() {
        return commonSpaceReservations;
    }

    public void setCommonSpaceReservations(List<CommonSpaceReservation> commonSpaceReservations) {
        this.commonSpaceReservations = commonSpaceReservations;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addWashingReservation(WashingReservation washingReservation){
        washingReservations.add(washingReservation);
    }
    public void removeWashingReservation(WashingReservation washingReservation){
        washingReservations.remove(washingReservation);
    }
    public void addWashingReservations(WashingReservation[] newWashingReservations){
        washingReservations.addAll(Arrays.asList(newWashingReservations));
    }
}
