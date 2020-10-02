package pl.edu.pg.apkademikbackend.washingReservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalTime;
import java.util.Objects;

public class StartingHour {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime startingHour;
    private boolean isMine;

    public StartingHour(LocalTime startingHour, boolean isMine) {
        this.startingHour = startingHour;
        this.isMine = isMine;
    }

    public StartingHour() {
    }

    public LocalTime getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(LocalTime startingHour) {
        this.startingHour = startingHour;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartingHour that = (StartingHour) o;
        return isMine == that.isMine &&
                Objects.equals(startingHour, that.startingHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startingHour, isMine);
    }
}
