package pl.edu.pg.apkademikbackend.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.room.RoomService;
import pl.edu.pg.apkademikbackend.room.model.Room;

@RestController
@CrossOrigin(origins = "*")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/room")
    public ResponseEntity<?> addRoom(@RequestParam String dormName,@RequestParam Integer floorNumber, @RequestBody Room room){
        return ResponseEntity.ok(roomService.saveRoom(dormName,floorNumber,room));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms(@RequestParam String dormName, @RequestParam Integer floorNumber){
        return ResponseEntity.ok(roomService.getRooms(dormName,floorNumber));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/room/{userEmail}")
    public ResponseEntity<?> addUserToRoom(@RequestParam String dormName,@RequestParam Integer floorNumber,
                                           @RequestParam Integer roomNumber, @PathVariable String userEmail){
        return ResponseEntity.ok(roomService.addUserToRoom(dormName,floorNumber,roomNumber,userEmail));
    }
}
