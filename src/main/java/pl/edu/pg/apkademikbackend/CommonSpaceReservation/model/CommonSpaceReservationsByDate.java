package pl.edu.pg.apkademikbackend.CommonSpaceReservation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommonSpaceReservationsByDate {
    private LocalDate date;
    private List<CommonSpaceReservationWithMineAndCounter> reservations = new ArrayList<>();

    public CommonSpaceReservationsByDate(LocalDate date, List<CommonSpaceReservationWithMineAndCounter> reservations) {
        this.date = date;
        this.reservations = reservations;
    }

    public CommonSpaceReservationsByDate(LocalDate date) {
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
}
