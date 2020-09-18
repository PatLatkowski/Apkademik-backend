package pl.edu.pg.apkademikbackend.commonSpace.model;

public class CommonSpaceDto {
    private long floorId;
    private int number;
    private int size;
    private String name;
    private CommonSpaceType type;

    public long getFloorId() {
        return floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
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
}
