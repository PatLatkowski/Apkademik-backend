package pl.edu.pg.apkademikbackend.washingMachinesFailure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.WashingMachineFailureService;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.model.FailureDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class WashingMachineFailureController {

    private final WashingMachineFailureService washingMachineFailureService;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public WashingMachineFailureController(WashingMachineFailureService washingMachineFailureService, JwtUserDetailsService userDetailsService) {
        this.washingMachineFailureService = washingMachineFailureService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/washingMachineFailure/{id}")
    public ResponseEntity<?> getWashingMachineFailureById(@PathVariable long id){
        return ResponseEntity.ok(washingMachineFailureService.getWashingMachineFailureById(id));
    }

    @PutMapping("/washingMachineFailure/{id}")
    public ResponseEntity<?> updateWashingMachineFailureById(@PathVariable long id, @RequestBody FailureDto washingMachineFailure){
        return ResponseEntity.ok(washingMachineFailureService.updateWashingMachineFailure(id,washingMachineFailure));
    }

    @DeleteMapping("/washingMachineFailure/{id}")
    public ResponseEntity<?> deleteWashingMachineFailureById(@PathVariable long id){
        washingMachineFailureService.deleteWashingMachineFailure(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/washingMachineFailure")
    public ResponseEntity<?> saveWashingMachineFailure(HttpServletRequest request, FailureDto failureDto){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(washingMachineFailureService.saveWashingMachineFailure(userEmail,failureDto));
    }

    @GetMapping("/washingMachineFailures")
    public ResponseEntity<?> getAllWashingMachineFailures(){
        return ResponseEntity.ok(washingMachineFailureService.getAllWashingMachineFailures());
    }
}
