package pl.edu.pg.apkademikbackend.washingMachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineDto;
import pl.edu.pg.apkademikbackend.washingReservation.WashingReservationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;


@RestController
@CrossOrigin(origins = "*")
public class WashingMachineController {
    @Autowired
    private WashingMachineService washingMachineService;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private WashingReservationService washingReservationService;

    @PostMapping("/washingMachine")
    public ResponseEntity<?> addWashingMachine(@RequestBody WashingMachineDto washingMachine){
        return ResponseEntity.ok(washingMachineService.saveWashingMachine(washingMachine));
    }

    @GetMapping("/washingMachine/{id}")
    public ResponseEntity<?>getWashingMachineById(@PathVariable long id){
        return ResponseEntity.ok(washingMachineService.getWashingMachineById(id));
    }

    @PutMapping("/washingMachine/{id}")
    public ResponseEntity<?>updateWashingMachineById(@PathVariable long id, @RequestBody WashingMachine washingMachine){
        return ResponseEntity.ok(washingMachineService.updateWashingMachineById(id,washingMachine));
    }

    @DeleteMapping("/washingMachine/{id}")
    public ResponseEntity<?>deleteWashingMachineById(@PathVariable long id){
        washingMachineService.deleteWashingMachineById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/washingMachine/{washingMachineId}/washingReservations/fiveDays")
    public ResponseEntity<?> getAllWashingReservationsByDateFromFiveDays(HttpServletRequest request, @PathVariable long washingMachineId,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        String email = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(washingReservationService.getWashingReservationFromFiveDays(email,washingMachineId,date));
    }

    @GetMapping("/washingMachine/{washingMachineId}/washingReservations")
    public ResponseEntity<?> getWashingReservations(@PathVariable long washingMachineId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        return ResponseEntity.ok(washingReservationService.getWashingReservations(washingMachineId,localDate));
    }

}