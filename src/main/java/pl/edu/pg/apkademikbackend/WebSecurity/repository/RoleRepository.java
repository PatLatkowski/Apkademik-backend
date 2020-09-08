package pl.edu.pg.apkademikbackend.WebSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;

public interface RoleRepository extends CrudRepository<Role,Integer> {
    public Role findByName(String name);
}
