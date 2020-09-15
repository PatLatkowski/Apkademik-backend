package pl.edu.pg.apkademikbackend.dorm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin(origins = "*")
public class DormController {
    @Autowired
    private DormService dormService;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private FloorService floorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/dorm")
    public ResponseEntity<?> addDorm(@RequestBody Dorm dorm){
        return ResponseEntity.ok(dormService.saveDorm(dorm));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dorm/{dormName}")
    public ResponseEntity<?> getDorm(@PathVariable String dormName){
        return ResponseEntity.ok(dormService.getDorm(dormName));
    }

    @GetMapping("/dorm/{id}")
    public ResponseEntity<?>getDormById(@PathVariable long id){
        return ResponseEntity.ok(dormService.getDormById(id));
    }

    @GetMapping("/dorms")
    public ResponseEntity<?> getAllDorms(){
        return ResponseEntity.ok(dormService.getAllDorms());
    }

    @PutMapping("/dorm/{id}")
    public ResponseEntity<?>updateDormById(@PathVariable long id, @RequestBody Dorm dorm){
        return ResponseEntity.ok(dormService.updateDormById(id,dorm));
    }

    @DeleteMapping("/dorm/{id}")
    public ResponseEntity<?>deleteDormById(@PathVariable long id){
        dormService.deleteDormById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/dorm")
    public ResponseEntity<?> getDorm(HttpServletRequest request){
        String email = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(dormService.getDormByUserEmail(email));
    }
    @GetMapping("/dorm/{id}/noticeboards")
    public ResponseEntity<?> getAllNoticeBoardsFromDorm(@PathVariable long id){
        return ResponseEntity.ok(dormService.getAllDorms());
    }
    @GetMapping("/dorm/{id}/floors")
    public ResponseEntity<?> getAllFloorsFromDorm(@PathVariable long id){
        return ResponseEntity.ok(floorService.getAllFloorsFromDorm(id));
    }
}