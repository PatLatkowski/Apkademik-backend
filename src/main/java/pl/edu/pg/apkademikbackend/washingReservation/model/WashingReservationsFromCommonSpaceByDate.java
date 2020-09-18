package pl.edu.pg.apkademikbackend.washingReservation.model;

import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineActualReservationsDOT;

import java.util.ArrayList;
import java.util.List;

public class WashingReservationsFromCommonSpaceByDate {
    private List<WashingMachineActualReservationsDOT> washingMachines;

    public WashingReservationsFromCommonSpaceByDate() {
        washingMachines = new ArrayList<>();
    }

    public List<WashingMachineActualReservationsDOT> getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(List<WashingMachineActualReservationsDOT> washingMachines) {
        this.washingMachines = washingMachines;
    }

    public void addWashingMachine(WashingMachineActualReservationsDOT washingMachineActualReservationsDOT){
        washingMachines.add(washingMachineActualReservationsDOT);
    }
}
