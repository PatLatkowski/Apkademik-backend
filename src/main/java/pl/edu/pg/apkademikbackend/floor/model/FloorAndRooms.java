package pl.edu.pg.apkademikbackend.floor.model;

import pl.edu.pg.apkademikbackend.room.model.Room;

import java.util.List;

public class FloorAndRooms {
    private long floorId;
    private int floorNumber;
    private List<Room> rooms;

    public FloorAndRooms(long floorId, int floorNumber, List<Room> rooms) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.rooms = rooms;
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
