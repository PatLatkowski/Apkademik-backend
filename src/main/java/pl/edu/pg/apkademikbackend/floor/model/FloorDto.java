package pl.edu.pg.apkademikbackend.floor.model;

public class FloorDto {
    private long dormId;
    private Integer number;

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
