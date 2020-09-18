package pl.edu.pg.apkademikbackend.WebSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleNotFoundException;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.RoleRepository;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import java.util.List;
import java.util.Set;

@Component
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Set<Role> setRoleToRole(long userId,long roleId){
        Role role = roleRepository.findById(roleId);
        if(role== null)
            throw new RoleNotFoundException(roleId);
        UserDao user = userRepository.findById(userId);
        if(user== null)
            throw new UserNotFoundException(userId);
        Set<Role> roles = user.addNewRole(role);
        userRepository.save(user);
        return roles;
    }
}
