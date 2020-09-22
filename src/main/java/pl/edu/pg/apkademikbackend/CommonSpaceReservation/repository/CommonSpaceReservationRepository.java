package pl.edu.pg.apkademikbackend.CommonSpaceReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservation;

public interface CommonSpaceReservationRepository extends JpaRepository<CommonSpaceReservation,Long> {
    CommonSpaceReservation findById(long id);
}
