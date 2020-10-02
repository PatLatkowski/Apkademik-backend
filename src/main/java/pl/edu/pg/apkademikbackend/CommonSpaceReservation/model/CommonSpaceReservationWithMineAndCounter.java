package pl.edu.pg.apkademikbackend.CommonSpaceReservation.model;

import java.time.LocalTime;
import java.util.Objects;

public class CommonSpaceReservationWithMineAndCounter {
    private boolean isMine;
    private long reservationCounter;
    private LocalTime start;
    private LocalTime end;

    public CommonSpaceReservationWithMineAndCounter(boolean isMine, long reservationCounter, LocalTime start, LocalTime end) {
        this.isMine = isMine;
        this.reservationCounter = reservationCounter;
        this.start = start;
        this.end = end;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public long getReservationCounter() {
        return reservationCounter;
    }

    public void setReservationCounter(long reservationCounter) {
        this.reservationCounter = reservationCounter;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonSpaceReservationWithMineAndCounter that = (CommonSpaceReservationWithMineAndCounter) o;
        return isMine == that.isMine &&
                reservationCounter == that.reservationCounter &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isMine, reservationCounter, start, end);
    }
}
