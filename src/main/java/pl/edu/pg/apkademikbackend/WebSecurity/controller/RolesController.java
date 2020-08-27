package pl.edu.pg.apkademikbackend.WebSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleAlreadyExistException;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleNotFoundException;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.UserNotFoundException;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.WebSecurity.model.UserDao;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.RoleRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.UserRepository;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class RolesController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{email}/role")
    public ResponseEntity<?> userRole(@PathVariable String email){
        return ResponseEntity.ok(userRepository.findByEmail(email).getRoles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody Role newRole) throws Exception{

        Role testRole = roleRepository.findByName(newRole.getName());

        if(testRole != null)
            throw new RoleAlreadyExistException(newRole.getName());

        return ResponseEntity.ok(roleRepository.save(newRole));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{email}/role/{roleName}")
    public ResponseEntity<?> connectUserAndRole(@PathVariable String email, @PathVariable String roleName) throws Exception{

        UserDao user = userRepository.findByEmail(email);
        if(user == null)
            throw new UserNotFoundException(email);
        Role role = roleRepository.findByName(roleName);
        if(role == null)
            throw new RoleNotFoundException(roleName);
        Set<Role> newRoles = user.getRoles();
        if(newRoles.contains(role))
            throw new RoleAlreadyExistException(role.getName());
        newRoles.add(role);
        user.setRoles(newRoles);

        return ResponseEntity.ok(newRoles);
    }

}
