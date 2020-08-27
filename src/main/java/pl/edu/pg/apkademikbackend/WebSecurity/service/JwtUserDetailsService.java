package pl.edu.pg.apkademikbackend.WebSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pg.apkademikbackend.WebSecurity.model.UserDao;
import pl.edu.pg.apkademikbackend.WebSecurity.model.UserDto;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDao user = userDao.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthority(user));
    }

    private Set<GrantedAuthority> getAuthority(UserDao user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public UserDao save(UserDto user){
        UserDao newUser = new UserDao();
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        return userDao.save(newUser);
    }


}
