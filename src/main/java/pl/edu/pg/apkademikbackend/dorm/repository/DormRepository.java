package pl.edu.pg.apkademikbackend.dorm.repository;


import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;

public interface DormRepository extends CrudRepository<Dorm,Integer> {
    public Dorm findByName(String name);
}
