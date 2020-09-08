package pl.edu.pg.apkademikbackend.user.repositry;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

public interface UserRepository extends CrudRepository<UserDao,Integer> {
    public UserDao findByEmail(String email);
    public void deleteByEmail(String email);
}
