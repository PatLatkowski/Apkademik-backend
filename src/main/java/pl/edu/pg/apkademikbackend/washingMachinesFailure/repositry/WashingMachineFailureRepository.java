package pl.edu.pg.apkademikbackend.washingMachinesFailure.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.model.WashingMachineFailure;

public interface WashingMachineFailureRepository extends JpaRepository<WashingMachineFailure,Long> {
    WashingMachineFailure findById(long id);
}
