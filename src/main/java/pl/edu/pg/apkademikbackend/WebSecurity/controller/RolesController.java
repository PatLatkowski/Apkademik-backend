package pl.edu.pg.apkademikbackend.WebSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.RoleService;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleAlreadyExistException;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.RoleRepository;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class RolesController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody Role newRole) throws Exception{

        Role testRole = roleRepository.findByName(newRole.getName());

        if(testRole != null)
            throw new RoleAlreadyExistException(newRole.getName());

        return ResponseEntity.ok(roleRepository.save(newRole));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.ok(roleService.getRoles());
    }
}
