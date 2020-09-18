package pl.edu.pg.apkademikbackend.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.room.exception.RoomAlreadyExistException;
import pl.edu.pg.apkademikbackend.room.exception.RoomNotFoundException;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.room.model.RoomDto;
import pl.edu.pg.apkademikbackend.room.repository.RoomRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import java.util.List;

@Component
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private FloorService floorService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    public List<Room> getRooms(long id){
        return floorService.getFloorById(id).getRooms();
    }

    public Room getRoom(long id){
        Room room = roomRepository.findById(id);
        if(room==null)
            throw new RoomNotFoundException(id);
        return room;
    }

    public Room saveRoom(RoomDto newRoom){
        Floor floor = floorService.getFloorById(newRoom.getFloorId());
        List<Room> rooms = floor.getRooms();
        if(rooms.stream()
                .anyMatch(room1 -> room1.getNumber().equals(newRoom.getNumber())))
            throw new RoomAlreadyExistException(newRoom.getNumber());
        Room room = new Room();
        room.setSize(newRoom.getSize());
        room.setNumber(newRoom.getNumber());
        rooms.add(room);
        floor.setRooms(rooms);
        return roomRepository.save(room);
    }

    public List<UserDao> addUserToRoom(long roomId, long userId){
        Room room = this.getRoom(roomId);
        UserDao user = userDetailsService.getUser(userId);
        room.addResident(user);
        userRepository.save(user);
        roomRepository.save(room);
        return room.getResidents();
    }

    public Room updateRoom(long id, Room updatedRoom){
        Room room = this.getRoom(id);
        if(updatedRoom.getNumber()!=0)
            room.setNumber(updatedRoom.getNumber());
        if(updatedRoom.getSize()!=0)
            room.setSize(updatedRoom.getSize());
        if(updatedRoom.getResidents()!=null)
            room.setResidents(updatedRoom.getResidents());
        return roomRepository.save(room);
    }

    public void deleteRoom(long id){
        Room room = this.getRoom(id);
        roomRepository.delete(room);
    }

    public List<Room> getAllRoomsFromFloor(long id){
        return floorService.getFloorById(id).getRooms();
    }

    public Room getRoomFromUserEmail(String userEmail){
        return this.getRoom(userRepository.findByEmail(userEmail).getRoom().getId());
    }

    public List<UserDao> getUsersFromRoom(long id){
        return this.getRoom(id).getResidents();
    }

}
