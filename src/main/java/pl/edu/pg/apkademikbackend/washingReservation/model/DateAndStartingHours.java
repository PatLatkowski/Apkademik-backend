package pl.edu.pg.apkademikbackend.washingReservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DateAndStartingHours{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    private List<StartingHour> startingHours;

    public DateAndStartingHours(LocalDate date) {
        this.date = date;
        startingHours = new ArrayList<>();
    }

    public DateAndStartingHours(LocalDate date, List<StartingHour> startingHours) {
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

    public List<StartingHour> getStartingHours() {
        return startingHours;
    }

    public void setStartingHours(List<StartingHour> startingHours) {
        this.startingHours = startingHours;
    }

    public void addStartingHours(StartingHour start){
        this.startingHours.add(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateAndStartingHours that = (DateAndStartingHours) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(startingHours, that.startingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startingHours);
    }
}
