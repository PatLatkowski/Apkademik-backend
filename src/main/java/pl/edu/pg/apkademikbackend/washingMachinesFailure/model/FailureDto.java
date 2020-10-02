package pl.edu.pg.apkademikbackend.washingMachinesFailure.model;

public class FailureDto {
    private long washingMachineId;
    private FailureStatus failureStatus;
    private String description;

    public long getWashingMachineId() {
        return washingMachineId;
    }

    public void setWashingMachineId(long washingMachineId) {
        this.washingMachineId = washingMachineId;
    }

    public FailureStatus getFailureStatus() {
        return failureStatus;
    }

    public void setFailureStatus(FailureStatus failureStatus) {
        this.failureStatus = failureStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
