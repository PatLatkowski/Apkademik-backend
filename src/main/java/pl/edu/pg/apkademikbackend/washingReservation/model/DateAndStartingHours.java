package pl.edu.pg.apkademikbackend.washingReservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DateAndStartingHours{
    private LocalDate date;
    private List<LocalTime> startingHours;

    public DateAndStartingHours(LocalDate date) {
        this.date = date;
        startingHours = new ArrayList<>();
    }

    public DateAndStartingHours(LocalDate date, List<LocalTime> startingHours) {
        this.date = date;
        this.startingHours = startingHours;
    }

    public DateAndStartingHours() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LocalTime> getStartingHours() {
        return startingHours;
    }

    public void setStartingHours(List<LocalTime> startingHours) {
        this.startingHours = startingHours;
    }

    public void addStartingHours(LocalTime start){
        this.startingHours.add(start);
    }
}
