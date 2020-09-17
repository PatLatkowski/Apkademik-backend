package pl.edu.pg.apkademikbackend.washingMachine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;

public interface WashingMachineRepository extends JpaRepository<WashingMachine,Integer> {
    WashingMachine findById(long id);
}
