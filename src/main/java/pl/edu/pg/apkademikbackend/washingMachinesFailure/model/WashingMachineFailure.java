package pl.edu.pg.apkademikbackend.washingMachinesFailure.model;

import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;

import javax.persistence.*;

@Entity
public class WashingMachineFailure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private FailureStatus failureStatus;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "washing_machine_id")
    private WashingMachine washingMachine;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDao userDao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WashingMachine getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(WashingMachine washingMachine) {
        this.washingMachine = washingMachine;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public FailureStatus getFailureStatus() {
        return failureStatus;
    }

    public void setFailureStatus(FailureStatus failureStatus) {
        this.failureStatus = failureStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
