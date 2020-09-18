package pl.edu.pg.apkademikbackend.floor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.model.FloorDto;
import pl.edu.pg.apkademikbackend.room.RoomService;


@RestController
@CrossOrigin(origins = "*")
public class FloorController {
    @Autowired
    private FloorService floorService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private CommonSpaceService commonSpaceService;

    @PostMapping("/floor")
    public ResponseEntity<?> addFloor(@RequestBody FloorDto floor){

        return ResponseEntity.ok(floorService.saveFloor(floor));
    }

    @GetMapping("/floor/{id}")
    public ResponseEntity<?> getFloor(@PathVariable long id){
        return ResponseEntity.ok(floorService.getFloorById(id));
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

    @GetMapping("/floor/{floorId}/commonSpaces")
    public ResponseEntity<?> getCommonSpaces(@PathVariable long floorId){
        return ResponseEntity.ok(commonSpaceService.getCommonSpaces(floorId));
    }
}
