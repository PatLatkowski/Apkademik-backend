package pl.edu.pg.apkademikbackend.washingReservation.model;

public class WashingReservationIsMine {
    private boolean isMine;
    WashingReservation washingReservation;

    public WashingReservationIsMine(boolean isMine, WashingReservation washingReservation) {
        this.isMine = isMine;
        this.washingReservation = washingReservation;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public WashingReservation getWashingReservation() {
        return washingReservation;
    }

    public void setWashingReservation(WashingReservation washingReservation) {
        this.washingReservation = washingReservation;
    }
}
