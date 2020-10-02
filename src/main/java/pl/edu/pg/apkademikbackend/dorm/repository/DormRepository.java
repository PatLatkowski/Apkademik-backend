package pl.edu.pg.apkademikbackend.dorm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;

public interface DormRepository extends JpaRepository<Dorm,Long> {
    Dorm findByName(String name);
    Dorm findById(long id);
}
