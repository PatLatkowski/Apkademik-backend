package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.room.RoomService;
import pl.edu.pg.apkademikbackend.room.exception.NoSpaceInRoomException;
import pl.edu.pg.apkademikbackend.room.exception.RoomNotFoundException;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.room.repository.RoomRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private FloorService floorService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUserDetailsService userDetailsService;
    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void expectedRoomNotFoundException(){
        when(roomRepository.findById(1L)).thenReturn(null);
        assertThrows(RoomNotFoundException.class,() -> roomService.getRoom(1L));
    }

    @Test
    void getRoomByIdOkTest(){
        Room room = new Room();
        when(roomRepository.findById(room.getId())).thenReturn(room);
        assertEquals(room.getId(),roomService.getRoom(room.getId()).getId());
    }

    @Test
    void addUserToRoomUserIdOkTest(){
        UserDao user = new UserDao();
        Room room = new Room();
        room.setSize(2);
        when(roomRepository.findById(room.getId())).thenReturn(room);
        when(userDetailsService.getUser(user.getId())).thenReturn(user);
        room.addResident(user);
        assertEquals(room.getResidents().get(0).getId(),roomService.addUserToRoom(room.getId(),user.getId()).get(0).getId());
    }
    @Test
    void expectedNoSpaceInRoomException(){
        UserDao user = new UserDao();
        UserDao secondUser = new UserDao();
        Room room = new Room();
        room.setSize(1);
        when(roomRepository.findById(room.getId())).thenReturn(room);
        when(userDetailsService.getUser(user.getId())).thenReturn(user);
        when(userDetailsService.getUser(secondUser.getId())).thenReturn(secondUser);
        roomService.addUserToRoom(room.getId(),user.getId());
        assertThrows(NoSpaceInRoomException.class,() -> roomService.addUserToRoom(room.getId(),secondUser.getId()));
    }

    @Test
    void getRoomsWithLeftSpaceTest(){
        List <Room> allRooms = new ArrayList<>();
        Room roomOne = new Room();
        roomOne.setSize(0);
        Room roomTwo = new Room();
        roomTwo.setSize(1);
        Room roomThree = new Room();
        roomThree.setSize(0);
        allRooms.add(roomOne);
        allRooms.add(roomTwo);
        allRooms.add(roomThree);
        when(roomRepository.findAll()).thenReturn(allRooms);
        assertEquals(1,roomService.getAllRomsWithLeftSpace().size());
    }
}
