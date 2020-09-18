package pl.edu.pg.apkademikbackend.user;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.InvalidPasswordException;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.room.RoomService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.model.UserDto;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DormService dormService;

    @Autowired
    private RoomService roomService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        UserDao user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException(email);
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
        return saveUserDao(newUser,user);
    }

    public UserDao save(UserDto user, String email){
        UserDao updatedUser = userRepository.findByEmail(email);
        if(updatedUser == null){
            throw new UserNotFoundException(email);
        }
        return saveUserDao(updatedUser,user);
    }
    public UserDao save(UserDto user, String email, String oldPassword){
        UserDao updatedUser = userRepository.findByEmail(email);
        if(updatedUser == null){
            throw new UserNotFoundException(email);
        }
        if(!bcryptEncoder.matches(oldPassword,updatedUser.getPassword()))
            throw new InvalidPasswordException();
        return saveUserDao(updatedUser,user);
    }
    private UserDao saveUserDao(UserDao updatedUser,UserDto user){
        if(user.getPassword()!= null)
            updatedUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        if(user.getName()!= null)
            updatedUser.setName(user.getName());
        if(user.getSurname()!= null)
            updatedUser.setSurname(user.getSurname());
        if(user.getEmail()!=null)
            updatedUser.setEmail(user.getEmail());
        if(user.getDormId()!=null)
            updatedUser.setDorm(dormService.getDormById(user.getDormId()));
        if(user.getRoomId()!=null)
            roomService.getRoom(user.getRoomId()).addResident(updatedUser);
        return userRepository.save(updatedUser);
    }
    public String getUserEmailFromToken(HttpServletRequest request ){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;
        jwtToken = requestTokenHeader.substring(7);
        try {
            userEmail = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        return userEmail;
    }

    public UserDao getUser(String email){
        UserDao user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException(email);
        }
        return user;
    }
    public UserDao getUser(long id){
        UserDao user = userRepository.findById(id);
        if(user == null)
            throw new UserNotFoundException(id);
        return user;
    }

    @Transactional
    public void deleteByEmail(String email){
        UserDao user = getUser(email);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteById(long id){
        UserDao user = this.getUser(id);
        userRepository.delete(user);
    }

    public Set<Role> getRoles(long id){
        return userRepository.findById(id).getRoles();
    }
}
