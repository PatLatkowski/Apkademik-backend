package pl.edu.pg.apkademikbackend.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.model.FloorAndRooms;
import pl.edu.pg.apkademikbackend.room.exception.NoSpaceInRoomException;
import pl.edu.pg.apkademikbackend.room.exception.RoomAlreadyExistException;
import pl.edu.pg.apkademikbackend.room.exception.RoomNotFoundException;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.room.model.RoomDto;
import pl.edu.pg.apkademikbackend.room.repository.RoomRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomService {
    private final RoomRepository roomRepository;
    private final FloorService floorService;
    private final UserRepository userRepository;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public RoomService(RoomRepository roomRepository, FloorService floorService, UserRepository userRepository, JwtUserDetailsService userDetailsService) {
        this.roomRepository = roomRepository;
        this.floorService = floorService;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
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
        if(room.getSize()<=room.getResidents().size())
            throw new NoSpaceInRoomException(roomId);
        UserDao user = userDetailsService.getUser(userId);
        room.addResident(user);
        userRepository.save(user);
        roomRepository.save(room);
        return room.getResidents();
    }

    public Room updateRoom(long id, Room updatedRoom){
        Room room = this.getRoom(id);
        if(updatedRoom.getNumber()!=null)
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

    public List<FloorAndRooms> getAllRoomsFromDorm(long dormId){
        List<FloorAndRooms> floorAndRooms = new ArrayList<>();
        for (Floor floor : floorService.getAllFloorsFromDorm(dormId)) {
            floorAndRooms.add(new FloorAndRooms(floor.getId(), floor.getNumber(), floor.getRooms()));
        }
        return floorAndRooms;
    }

    public List<FloorAndRooms> getAllRoomsWithLeftSpaceFromDorm(long dormId){
        List<FloorAndRooms> allRoomsFromDorm = this.getAllRoomsFromDorm(dormId);
        for (FloorAndRooms floorAndRoom:
             allRoomsFromDorm) {
            List<Room> updatedRoomsList = floorAndRoom.getRooms().stream()
                    .filter(room -> room.getSize() > room.getResidents().size())
                    .collect(Collectors.toList());
            floorAndRoom.setRooms(updatedRoomsList);
        }
        return allRoomsFromDorm;
    }

    public List<Room> getAllRomsWithLeftSpace(){
        return roomRepository.findAll().stream()
                .filter(room -> room.getSize()>room.getResidents().size())
                .collect(Collectors.toList());
    }

}
