package pl.edu.pg.apkademikbackend.washingMachine.model;

public class WashingMachineDto {
    private long commonSpaceId;
    private int number;
    private WashingMachineStatus status;

    public long getCommonSpaceId() {
        return commonSpaceId;
    }

    public void setCommonSpaceId(long commonSpaceId) {
        this.commonSpaceId = commonSpaceId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public WashingMachineStatus getStatus() {
        return status;
    }

    public void setStatus(WashingMachineStatus status) {
        this.status = status;
    }
}
