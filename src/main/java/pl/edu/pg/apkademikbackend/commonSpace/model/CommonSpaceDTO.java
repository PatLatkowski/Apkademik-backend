package pl.edu.pg.apkademikbackend.commonSpace.model;

import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineDTO;

import java.util.ArrayList;
import java.util.List;

public class CommonSpaceDTO {
    private int number;
    private int size;
    private String name;
    private CommonSpaceType type;
    private List<WashingMachineDTO> washingMachines  = new ArrayList<>();

    public CommonSpaceDTO(int number, int size, String name, CommonSpaceType type, List<WashingMachineDTO> washingMachines) {
        this.number = number;
        this.size = size;
        this.name = name;
        this.type = type;
        this.washingMachines = washingMachines;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommonSpaceType getType() {
        return type;
    }

    public void setType(CommonSpaceType type) {
        this.type = type;
    }

    public List<WashingMachineDTO> getWashingMachines() {
        return washingMachines;
    }

    public void setWashingMachines(List<WashingMachineDTO> washingMachines) {
        this.washingMachines = washingMachines;
    }
}
