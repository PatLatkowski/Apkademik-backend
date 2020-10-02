package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.room.RoomService;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class UserDetailsServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder bcryptEncoder;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private DormService dormService;
    @Mock
    private RoomService roomService;
    @InjectMocks
    private JwtUserDetailsService userDetailsService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUserByIdOkTest(){
        UserDao userDao = new UserDao();
        when(userRepository.findById(userDao.getId())).thenReturn(userDao);
        assertEquals(userDao.getId(),userDetailsService.getUser(userDao.getId()).getId());
    }

    @Test
    void expectedUserNotFoundException(){
        UserDao userDao = new UserDao();
        when(userRepository.findById(userDao.getId())).thenReturn(null);
        assertThrows(UserNotFoundException.class,() -> userDetailsService.getUser(userDao.getId()));
    }
}
