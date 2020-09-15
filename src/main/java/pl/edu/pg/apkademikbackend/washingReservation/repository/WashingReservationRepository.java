package pl.edu.pg.apkademikbackend.washingReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

public interface WashingReservationRepository extends JpaRepository<WashingReservation,Integer> {

}

