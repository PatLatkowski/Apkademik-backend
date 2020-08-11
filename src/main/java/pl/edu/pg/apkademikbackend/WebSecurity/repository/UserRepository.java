package pl.edu.pg.apkademikbackend.WebSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.model.UserDao;

public interface UserRepository extends CrudRepository<UserDao,Integer> {
    public UserDao findByEmail(String email);
}
