package pl.edu.pg.apkademikbackend.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.user.model.UserDto;
import pl.edu.pg.apkademikbackend.user.model.UserToAuthorize;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        return ResponseEntity.ok(userDetailsService.getUser(email));
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(userDetailsService.getUser(userEmail));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{email}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto user, @PathVariable String email){
        return ResponseEntity.ok(userDetailsService.save(user,email));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserToAuthorize user, HttpServletRequest request){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(userDetailsService.save(user.getUser(),userEmail,user.getOldPassword()));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        userRepository.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        userRepository.deleteByEmail(userEmail);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id){
        return ResponseEntity.ok(userDetailsService.getUser(id));
    }
}
