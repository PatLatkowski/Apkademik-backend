package pl.edu.pg.apkademikbackend.WebSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.WebSecurity.model.UserDao;

public interface RoleRepository extends CrudRepository<Role,Integer> {
    public Role findByName(String name);
}
