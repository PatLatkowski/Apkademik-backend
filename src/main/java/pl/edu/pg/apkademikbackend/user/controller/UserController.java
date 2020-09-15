package pl.edu.pg.apkademikbackend.user.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){

        UserDao user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException(email);
        }

        return ResponseEntity.ok(user);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        jwtToken = requestTokenHeader.substring(7);
        try {
            userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }

        UserDao user = userRepository.findByEmail(userName);

        if(user == null){
            throw new UserNotFoundException(userName);
        }

        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{email}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto user, @PathVariable String email){
        return ResponseEntity.ok(userDetailsService.save(user,email));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserToAuthorize user, HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        jwtToken = requestTokenHeader.substring(7);
        try {
            userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }

        return ResponseEntity.ok(userDetailsService.save(user.getUser(),userName,user.getOldPassword()));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        userRepository.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        jwtToken = requestTokenHeader.substring(7);
        try {
            userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        userRepository.deleteByEmail(userName);
        return ResponseEntity.noContent().build();
    }
}
