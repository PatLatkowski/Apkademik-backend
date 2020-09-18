package pl.edu.pg.apkademikbackend.commonSpace.model;

import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineActualReservationsDOT;

import java.util.ArrayList;
import java.util.List;

public class CommonSpacesWithWashingMachines {
    private CommonSpace commonSpace;
    private List<WashingMachineActualReservationsDOT> washingMachines  = new ArrayList<>();

    public CommonSpacesWithWashingMachines(CommonSpace commonSpace, List<WashingMachineActualReservationsDOT> washingMachines) {
        this.commonSpace = commonSpace;
        this.washingMachines = washingMachines;
    }

    public CommonSpace getCommonSpace() {
        return commonSpace;
    }

    public void setCommonSpace(CommonSpace commonSpace) {
        this.commonSpace = commonSpace;
    }

    public List<WashingMachineActualReservationsDOT> getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(List<WashingMachineActualReservationsDOT> washingMachines) {
        this.washingMachines = washingMachines;
    }
}
