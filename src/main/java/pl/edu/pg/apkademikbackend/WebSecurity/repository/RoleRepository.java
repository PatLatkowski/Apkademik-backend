package pl.edu.pg.apkademikbackend.WebSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
    Role findById(long id);
}
