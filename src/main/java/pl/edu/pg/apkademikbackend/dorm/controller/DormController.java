package pl.edu.pg.apkademikbackend.dorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.exception.DormAlreadyExistException;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;


@RestController
@CrossOrigin(origins = "*")
public class DormController {
    @Autowired
    private DormService dormService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/dorm")
    public ResponseEntity<?> addDorm(@RequestBody Dorm dorm){
        return ResponseEntity.ok(dormService.saveDorm(dorm));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("dorm/{dormName}")
    public ResponseEntity<?> getDorm(@PathVariable String dormName){
        return ResponseEntity.ok(dormService.getDorm(dormName));
    }
}
