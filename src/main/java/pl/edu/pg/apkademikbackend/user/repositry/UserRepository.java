package pl.edu.pg.apkademikbackend.user.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

public interface UserRepository extends JpaRepository<UserDao,Long> {
    public UserDao findByEmail(String email);
    public void deleteByEmail(String email);
    UserDao findById(long id);
}
