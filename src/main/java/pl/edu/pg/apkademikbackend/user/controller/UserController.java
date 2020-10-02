package pl.edu.pg.apkademikbackend.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.RoleService;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserToAuthorize;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(userDetailsService.getUser(userEmail));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserToAuthorize user, HttpServletRequest request){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(userDetailsService.save(user.getUser(),userEmail,user.getOldPassword()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        userDetailsService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        userDetailsService.deleteByEmail(userEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id){
        return ResponseEntity.ok(userDetailsService.getUser(id));
    }

    @GetMapping("/user/{userId}/roles")
    public ResponseEntity<?> getUserRoles(@PathVariable long userId){
        return ResponseEntity.ok(userDetailsService.getRoles(userId));
    }

    @PostMapping("/user/{userId}/role/{roleId}")
    public ResponseEntity<?> setRoleToUser(@PathVariable long userId, @PathVariable long roleId){
        return ResponseEntity.ok(roleService.setRoleToRole(userId,roleId));
    }
    @GetMapping("/user/{userId}/floor")
    public ResponseEntity<?> getFloorFromUser(@PathVariable long userId){
        return ResponseEntity.ok(userDetailsService.getFloor(userId));
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userDetailsService.getAllUsers());
    }

    @GetMapping("/users/roles")
    public ResponseEntity<?> getAllUsersWithRoles(){
        return ResponseEntity.ok(userDetailsService.getAllUsersWithRoles());
    }
}
