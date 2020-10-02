package pl.edu.pg.apkademikbackend.floor.model;

public class FloorDto {
    private long dormId;
    private Integer number;

    public FloorDto() {
    }

    public FloorDto(long dormId, Integer number) {
        this.dormId = dormId;
        this.number = number;
    }

    public long getDormId() {
        return dormId;
    }

    public void setDormId(long dormId) {
        this.dormId = dormId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
