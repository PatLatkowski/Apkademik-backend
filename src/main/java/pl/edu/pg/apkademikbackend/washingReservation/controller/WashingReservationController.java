package pl.edu.pg.apkademikbackend.washingReservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.washingReservation.WashingReservationService;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservationDto;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class WashingReservationController {
    @Autowired
    private WashingReservationService washingReservationsService;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/washingReservation")
    public ResponseEntity<?> addWashingReservation(HttpServletRequest request, @RequestBody WashingReservationDto washingReservations){
        String email = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(washingReservationsService.saveWashingReservation(email,washingReservations));
    }

    @GetMapping("/washingReservation/{id}")
    public ResponseEntity<?> getWashingReservation(@PathVariable long id){
        return ResponseEntity.ok(washingReservationsService.getWashingReservationById(id));
    }

    @PutMapping("/washingReservation/{id}")
    public ResponseEntity<?> updateWashingReservation(@PathVariable long id, @RequestBody WashingReservation washingReservation){
        return ResponseEntity.ok(washingReservationsService.updateWashingReservationById(id,washingReservation));
    }

    @DeleteMapping("/washingReservation/{id}")
    public ResponseEntity<?> deleteWashingReservation(@PathVariable long id){
        washingReservationsService.deleteWashingReservationById(id);
        return ResponseEntity.ok().build();
    }
}
