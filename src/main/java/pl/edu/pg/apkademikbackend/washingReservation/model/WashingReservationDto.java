package pl.edu.pg.apkademikbackend.washingReservation.model;

import java.util.ArrayList;
import java.util.List;

public class WashingReservationDto {
    private long washingMachineId;
    private List<WashingReservation> washingReservations = new ArrayList<>();

    public long getWashingMachineId() {
        return washingMachineId;
    }

    public void setWashingMachineId(long washingMachineId) {
        this.washingMachineId = washingMachineId;
    }

    public List<WashingReservation> getWashingReservations() {
        return washingReservations;
    }

    public void setWashingReservations(List<WashingReservation> washingReservations) {
        this.washingReservations = washingReservations;
    }
}
