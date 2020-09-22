package pl.edu.pg.apkademikbackend.floor.model;

import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;

import java.util.List;

public class FloorAndCommonSpaces {
    private long floorId;
    private int floorNumber;
    private List<CommonSpace> commonSpaces;

    public FloorAndCommonSpaces(long floorId, int floorNumber, List<CommonSpace> commonSpaces) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.commonSpaces = commonSpaces;
    }

    public long getFloorId() {
        return floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<CommonSpace> getCommonSpaces() {
        return commonSpaces;
    }

    public void setCommonSpaces(List<CommonSpace> commonSpaces) {
        this.commonSpaces = commonSpaces;
    }
}
