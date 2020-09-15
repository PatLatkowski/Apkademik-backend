package pl.edu.pg.apkademikbackend.floor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;


@RestController
@CrossOrigin(origins = "*")
public class FloorController {
    @Autowired
    private FloorService floorService;

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
}
