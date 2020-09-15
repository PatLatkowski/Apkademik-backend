package pl.edu.pg.apkademikbackend.washingReservation.model;

import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineDTO;

import java.util.ArrayList;
import java.util.List;

public class WashingReservationsFromCommonSpaceByDate {
    private List<WashingMachineDTO> washingMachines;

    public WashingReservationsFromCommonSpaceByDate() {
        washingMachines = new ArrayList<>();
    }

    public List<WashingMachineDTO> getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(List<WashingMachineDTO> washingMachines) {
        this.washingMachines = washingMachines;
    }

    public void addWashingMachine(WashingMachineDTO washingMachineDTO){
        washingMachines.add(washingMachineDTO);
    }
}
