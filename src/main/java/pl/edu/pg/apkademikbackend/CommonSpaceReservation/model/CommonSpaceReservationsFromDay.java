package pl.edu.pg.apkademikbackend.CommonSpaceReservation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommonSpaceReservationsFromDay {
    private LocalDate date;
    private List<CommonSpaceReservationWithMineAndCounter> reservations = new ArrayList<>();

    public CommonSpaceReservationsFromDay(LocalDate date, List<CommonSpaceReservationWithMineAndCounter> reservations) {
        this.date = date;
        this.reservations = reservations;
    }

    public CommonSpaceReservationsFromDay(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<CommonSpaceReservationWithMineAndCounter> getReservations() {
        return reservations;
    }

    public void setReservations(List<CommonSpaceReservationWithMineAndCounter> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(CommonSpaceReservationWithMineAndCounter commonSpaceReservationWithMineAndCounter){
        if(reservations.stream()
        .anyMatch(reservation -> reservation.getStart().equals(commonSpaceReservationWithMineAndCounter.getStart())))
            reservations.stream()
            .filter(reservation -> reservation.getStart().equals(commonSpaceReservationWithMineAndCounter.getStart()))
            .findAny()
            .ifPresent(reservation -> reservation.setReservationCounter(reservation.getReservationCounter()+1));
        else reservations.add(commonSpaceReservationWithMineAndCounter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonSpaceReservationsFromDay that = (CommonSpaceReservationsFromDay) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, reservations);
    }
}
