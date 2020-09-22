package pl.edu.pg.apkademikbackend.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.room.RoomService;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.room.model.RoomDto;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/room")
    public ResponseEntity<?> addRoom(@RequestBody RoomDto room){
        return ResponseEntity.ok(roomService.saveRoom(room));
    }

    @PostMapping("/room/{roomId}/user/{userId}")
    public ResponseEntity<?> addUserToRoom(@PathVariable long roomId, @PathVariable long userId){
        return ResponseEntity.ok(roomService.addUserToRoom(roomId,userId));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable long id){
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable long id, @RequestBody Room room){
        return ResponseEntity.ok(roomService.updateRoom(id,room));
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable long id){
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/room")
    public ResponseEntity<?> getRoom(HttpServletRequest request){
        String email = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(roomService.getRoomFromUserEmail(email));
    }

    @GetMapping("/room/{roomId}/users")
    public ResponseEntity<?> getUsersFromRoom(@PathVariable long roomId){
        return ResponseEntity.ok(roomService.getUsersFromRoom(roomId));
    }

    @GetMapping("/freeRooms")
    public ResponseEntity<?> getAllFreeRooms(){
        return ResponseEntity.ok(roomService.getAllRomsWithLeftSpace());
    }
}
