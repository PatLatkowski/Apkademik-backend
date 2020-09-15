package pl.edu.pg.apkademikbackend.floor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.floor.model.Floor;

public interface FloorRepository extends JpaRepository<Floor,Integer> {
    Floor findByRooms_Id(long id);
}
