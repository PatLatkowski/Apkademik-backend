package pl.edu.pg.apkademikbackend.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.room.repository.RoomRepository;

@RestController
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @PostMapping("/room")
    public ResponseEntity<?> addRoom(@RequestBody Room newRoom){

        return ResponseEntity.ok(roomRepository.save(newRoom));
    }

}
