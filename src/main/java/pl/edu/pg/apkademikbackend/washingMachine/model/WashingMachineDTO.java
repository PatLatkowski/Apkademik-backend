package pl.edu.pg.apkademikbackend.washingMachine.model;

import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

import java.util.ArrayList;
import java.util.List;

public class WashingMachineDTO {
    private int number;
    private WashingMachineStatus status;
    private List<WashingReservation> washingReservations = new ArrayList<>();

    public WashingMachineDTO(int number, WashingMachineStatus status, List<WashingReservation> washingReservations) {
        this.number = number;
        this.status = status;
        this.washingReservations = washingReservations;
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
}
