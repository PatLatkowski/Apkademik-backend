package pl.edu.pg.apkademikbackend.dorm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;

public interface DormRepository extends JpaRepository<Dorm,Integer> {
    Dorm findByName(String name);
    Dorm findByFloors_Id(long id);
}
