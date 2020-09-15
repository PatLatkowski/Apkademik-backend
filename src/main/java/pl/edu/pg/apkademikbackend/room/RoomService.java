package pl.edu.pg.apkademikbackend.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.room.exception.RoomAlreadyExistException;
import pl.edu.pg.apkademikbackend.room.exception.RoomNotFoundException;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.room.repository.RoomRepository;
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
    private DormRepository dormRepository;

    public List<Room> getRooms(String dormName, Integer floorNumber){
        return floorService.getFloor(dormName,floorNumber).getRooms();
    }

    public Room getRoom(String dormName, Integer floorNumber, Integer roomNumber){
        return this.getRooms(dormName,floorNumber).stream()
                .filter(room -> room.getNumber() == roomNumber)
                .findAny()
                .orElseThrow(() -> new RoomNotFoundException(roomNumber));
    }

    public Room getRoom(long id){
        Room room = roomRepository.findById(id);
        if(room==null)
            throw new RoomNotFoundException(id);
        return room;
    }

    public Room saveRoom(String dormName, Integer floorNumber, Room room){
        Floor floor = floorService.getFloor(dormName,floorNumber);
        List<Room> rooms = floor.getRooms();
        if(rooms.stream()
                .anyMatch(room1 -> room1.getNumber() == room.getNumber()))
            throw new RoomAlreadyExistException(room.getNumber());
        rooms.add(room);
        floor.setRooms(rooms);
        return roomRepository.save(room);
    }

    public List<UserDao> addUserToRoom(String dormName, Integer floorNumber,
                              Integer roomNumber, String userEmail){
        Room room = this.getRoom(dormName, floorNumber, roomNumber);

        UserDao user = userRepository.findByEmail(userEmail);
        user.setDorm(dormRepository.findByName(dormName));
        room.addResident(user);
        userRepository.save(user);
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
        return room;
    }

    public void deleteRoom(long id){
        Room room = this.getRoom(id);
        roomRepository.delete(room);
    }

    public List<Room> getAllRoomsFromFloor(long id){
        return floorService.getFloor(id).getRooms();
    }

    public Room getRoomFromUserEmail(String userEmail){
        return this.getRoom(userRepository.findByEmail(userEmail).getRoom().getId());
    }

    public List<UserDao> getUsersFromRoom(long id){
        return this.getRoom(id).getResidents();
    }

}
