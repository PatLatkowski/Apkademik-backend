package pl.edu.pg.apkademikbackend.washingReservation.model;

import java.util.List;

public class WashingReservationFromFiveDays {
    private List<DateAndStartingHours> dateAndStartingHours;

    public WashingReservationFromFiveDays(List<DateAndStartingHours> dateAndStartingHours) {
        this.dateAndStartingHours = dateAndStartingHours;
    }

    public WashingReservationFromFiveDays() {
    }

    public List<DateAndStartingHours> getDateAndStartingHours() {
        return dateAndStartingHours;
    }

    public void setDateAndStartingHours(List<DateAndStartingHours> dateAndStartingHours) {
        this.dateAndStartingHours = dateAndStartingHours;
    }
}
