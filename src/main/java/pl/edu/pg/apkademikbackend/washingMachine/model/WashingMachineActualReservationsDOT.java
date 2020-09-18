package pl.edu.pg.apkademikbackend.washingMachine.model;

import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservationIsMine;

import java.util.ArrayList;
import java.util.List;

public class WashingMachineActualReservationsDOT {
    private WashingMachine washingMachine;
    private List<WashingReservationIsMine> washingReservations = new ArrayList<>();

    public WashingMachineActualReservationsDOT(WashingMachine washingMachine, List<WashingReservationIsMine> washingReservations) {
        this.washingMachine = washingMachine;
        this.washingReservations = washingReservations;
    }

    public WashingMachine getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(WashingMachine washingMachine) {
        this.washingMachine = washingMachine;
    }

    public List<WashingReservationIsMine> getWashingReservations() {
        return washingReservations;
    }

    public void setWashingReservations(List<WashingReservationIsMine> washingReservations) {
        this.washingReservations = washingReservations;
    }
}
