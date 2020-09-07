package pl.edu.pg.apkademikbackend.dorm;


import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.model.Dorm;

public interface DormRepository extends CrudRepository<Dorm,Integer> {
    public Dorm findByName(String name);
}
