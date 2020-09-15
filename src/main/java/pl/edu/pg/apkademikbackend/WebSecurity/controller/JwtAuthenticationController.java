package pl.edu.pg.apkademikbackend.WebSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.user.exception.UserAlreadyExistException;
import pl.edu.pg.apkademikbackend.WebSecurity.model.JwtRequest;
import pl.edu.pg.apkademikbackend.WebSecurity.model.JwtResponse;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.model.UserDto;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {

        UserDao userToCheck = userDao.findByEmail(user.getEmail());
        if(userToCheck != null){
            throw new UserAlreadyExistException(user.getEmail());
        }

        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}