package pl.edu.pg.apkademikbackend.floor.model;

import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpacesWithWashingMachines;

import java.util.List;

public class FloorWithWashingMachines {
    private long id;
    private int number;
    private List<CommonSpacesWithWashingMachines> commonSpacesWithWashingMachines;

    public FloorWithWashingMachines(long id, int number ,List<CommonSpacesWithWashingMachines> commonSpacesWithWashingMachines) {
        this.id = id;
        this.number = number;
        this.commonSpacesWithWashingMachines = commonSpacesWithWashingMachines;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CommonSpacesWithWashingMachines> getCommonSpacesWithWashingMachines() {
        return commonSpacesWithWashingMachines;
    }

    public void setCommonSpacesWithWashingMachines(List<CommonSpacesWithWashingMachines> commonSpacesWithWashingMachines) {
        this.commonSpacesWithWashingMachines = commonSpacesWithWashingMachines;
    }
}
