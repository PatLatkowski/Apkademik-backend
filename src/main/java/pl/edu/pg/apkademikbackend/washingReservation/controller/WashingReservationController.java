package pl.edu.pg.apkademikbackend.washingReservation.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.washingReservation.WashingReservationService;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WashingReservationController {
    @Autowired
    private WashingReservationService washingReservationsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /*@PostMapping("/washingReservation")
    public ResponseEntity<?> addWashingReservation(@RequestParam String dormName, @RequestParam Integer floorNumber, @RequestParam Integer commonSpaceNumber,
                                            @RequestParam Integer washingMachineNumber, @RequestBody WashingReservation washingReservation){
        return ResponseEntity.ok(washingReservationsService.saveWashingReservation(dormName,floorNumber,commonSpaceNumber,washingMachineNumber,washingReservation));
    }*/
    @PostMapping("/washingReservation")
    public ResponseEntity<?> addWashingReservation(HttpServletRequest request , @RequestParam Integer commonSpaceNumber,
                                                   @RequestParam Integer washingMachineNumber, @RequestBody WashingReservation[] washingReservation){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        jwtToken = requestTokenHeader.substring(7);
        try {
            userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        return ResponseEntity.ok(washingReservationsService.saveWashingReservation(userName,commonSpaceNumber,washingMachineNumber,washingReservation));
    }

    @GetMapping("/washingReservations")
    public ResponseEntity<?> getWashingReservations(@RequestParam String dormName, @RequestParam Integer floorNumber, @RequestParam Integer commonSpaceNumber,
                                                    @RequestParam Integer washingMachineNumber, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        return ResponseEntity.ok(washingReservationsService.getWashingReservations(dormName,floorNumber,commonSpaceNumber,washingMachineNumber,localDate));
    }
}