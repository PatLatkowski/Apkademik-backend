package pl.edu.pg.apkademikbackend.dorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.dorm.exception.DormAlreadyExistException;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;

@RestController
@CrossOrigin(origins = "*")
public class DormController {
    @Autowired
    private DormRepository dormRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/dorm")
    public ResponseEntity<?> addDorm(@RequestBody Dorm dorm){
        Dorm newDorm = dormRepository.findByName(dorm.getName());
        if(newDorm != null)
            throw new DormAlreadyExistException(dorm.getName());

        return ResponseEntity.ok(dormRepository.save(dorm));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("dorm/{dormName}")
    public ResponseEntity<?> getDorm(@PathVariable String dormName){
        Dorm dorm = dormRepository.findByName(dormName);
        if(dorm == null)
            throw new DormNotFoundException(dormName);
        return ResponseEntity.ok(dorm);
    }
}
