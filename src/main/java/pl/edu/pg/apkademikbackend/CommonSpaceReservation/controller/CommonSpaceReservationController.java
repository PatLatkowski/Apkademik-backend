package pl.edu.pg.apkademikbackend.CommonSpaceReservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.CommonSpaceReservationService;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservation;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservationDto;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@CrossOrigin(origins = "*")
public class CommonSpaceReservationController {
    @Autowired
    CommonSpaceReservationService commonSpaceReservationService;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping("/commonSpaceReservation/{id}")
    public ResponseEntity<?> getCommonSpaceReservation(@PathVariable long id){
        return ResponseEntity.ok(commonSpaceReservationService.getCommonSpaceReservationById(id));
    }

    @PostMapping("commonSpaceReservation")
    public ResponseEntity<?> addCommonSpaceReservation(HttpServletRequest request, @RequestBody CommonSpaceReservationDto commonSpaceReservationDto){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(commonSpaceReservationService.saveCommonSpaceReservations(userEmail,commonSpaceReservationDto));
    }

    @DeleteMapping("/commonSpaceReservation/{id}")
    public ResponseEntity<?> deleteCommonSpaceReservation(@PathVariable long id){
        commonSpaceReservationService.deleteCommonSpaceReservation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/commonSpaceReservation/{id}")
    public ResponseEntity<?> updateCommonSpaceReservation(@PathVariable long id, @RequestBody CommonSpaceReservation commonSpaceReservation){
        return ResponseEntity.ok(commonSpaceReservationService.updateCommonSpaceReservation(id,commonSpaceReservation));
    }

    @GetMapping("/commonSpace/commonSpaceReservations")
    public ResponseEntity<?> getAllCommonSpaceReservationsByDate(HttpServletRequest request,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok("");
    }

    @GetMapping("/commonSpace/{commonSpaceId}/commonSpaceReservations/fiveDays")
    public ResponseEntity<?> getAllCommonSpaceReservationsByDateFromFiveDays(HttpServletRequest request, @PathVariable long commonSpaceId,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        String email = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(commonSpaceReservationService.getCommonSpaceReservationsFromFiveDays(email,commonSpaceId,date));
    }
}
