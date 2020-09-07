package pl.edu.pg.apkademikbackend.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="common_space_reservation")
public class CommonSpaceReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private CommonSapceReservationStatus status;
    @Column
    private LocalTime start;
    @Column
    private LocalTime end;
    @Column
    private LocalDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CommonSapceReservationStatus getStatus() {
        return status;
    }

    public void setStatus(CommonSapceReservationStatus status) {
        this.status = status;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
