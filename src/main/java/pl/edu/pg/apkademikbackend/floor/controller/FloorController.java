package pl.edu.pg.apkademikbackend.floor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.room.RoomService;


@RestController
@CrossOrigin(origins = "*")
public class FloorController {
    @Autowired
    private FloorService floorService;
    @Autowired
    private RoomService roomService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/floor")
    public ResponseEntity<?> addFloor(@RequestParam String dormName, @RequestBody Floor floor){

        return ResponseEntity.ok(floorService.saveFloor(dormName,floor));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/floors")
    public ResponseEntity<?> getFloors(@RequestParam String dormName){

        return ResponseEntity.ok(floorService.getFloors(dormName));
    }

    @GetMapping("/floor/{id}")
    public ResponseEntity<?> getFloor(@PathVariable long id){
        return ResponseEntity.ok(floorService.getFloor(id));
    }

    @PutMapping("/floor/{id}")
    public ResponseEntity<?> updateFloor(@PathVariable long id, @RequestBody Floor floor){
        return ResponseEntity.ok(floorService.updateFloor(id,floor));
    }
    @DeleteMapping("/floor/{id}")
    public ResponseEntity<?> deleteFloor(@PathVariable long id){
        floorService.deleteFloor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/floor/{floorId}/rooms")
    public ResponseEntity<?> getAllRoomsFromFloor(@PathVariable long floorId){
        return ResponseEntity.ok(roomService.getAllRoomsFromFloor(floorId));
    }
}
