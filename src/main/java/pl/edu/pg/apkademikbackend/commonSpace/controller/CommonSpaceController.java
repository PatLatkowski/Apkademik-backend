package pl.edu.pg.apkademikbackend.commonSpace.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;

import pl.edu.pg.apkademikbackend.washingReservation.WashingReservationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
public class CommonSpaceController {
    @Autowired
    private CommonSpaceService commonSpaceService;
    @Autowired
    private WashingReservationService washingReservationService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/commonSpace")
    public ResponseEntity<?> addCommonSpace(@RequestParam String dormName, @RequestParam Integer floorNumber, @RequestBody CommonSpace commonSpace){
        return ResponseEntity.ok(commonSpaceService.saveCommonSpace(dormName,floorNumber,commonSpace));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/commonSpaces")
    public ResponseEntity<?> getCommonSpaces(@RequestParam String dormName, @RequestParam Integer floorNumber){
        return ResponseEntity.ok(commonSpaceService.getCommonSpaces(dormName,floorNumber));
    }

    /*@GetMapping("/commonSpace/washingReservation")
    public ResponseEntity<?> getAllWashingReservationsByDate(@RequestParam String dormName, @RequestParam Integer floorNumber,
                                                             @RequestParam Integer commonSpaceNumber, @RequestParam LocalDate date){
        return ResponseEntity.ok(washingReservationService.getWashingReservationsFromCommonSpaceByDate(dormName,floorNumber, commonSpaceNumber,date));
    }*/
    @GetMapping("/commonSpace/washingReservations")
    public ResponseEntity<?> getAllWashingReservationsByDate(HttpServletRequest request,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        String userEmail = getUserEmailFromToken(request);
        return ResponseEntity.ok(washingReservationService.getWashingReservationsFromCommonSpaceByDate(userEmail,date));
    }
    @GetMapping("/commonSpace/washingReservations/fiveDays")
    public ResponseEntity<?> getAllWashingReservationsByDateFromFiveDays(HttpServletRequest request,@RequestParam Integer commonSpaceNumber,
                                                                         @RequestParam Integer washingMachineNumber,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        String email = getUserEmailFromToken(request);
        return ResponseEntity.ok(washingReservationService.getWashingReservationFromFiveDays(email,commonSpaceNumber,washingMachineNumber,date));
    }

    private String getUserEmailFromToken(HttpServletRequest request){
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
        return userName;
    }

    @GetMapping("/commonSpace/{id}")
    public ResponseEntity<?>getCommonSpaceById(@PathVariable long id){
        return ResponseEntity.ok(commonSpaceService.getCommonSpaceById(id));
    }

    @PutMapping("/commonSpace/{id}")
    public ResponseEntity<?>updateCommonSpaceById(@PathVariable long id, @RequestBody CommonSpace commonSpace){
        return ResponseEntity.ok(commonSpaceService.updateCommonSpaceById(id,commonSpace));
    }

    @DeleteMapping("/commonSpace/{id}")
    public ResponseEntity<?>deleteCommonSpaceById(@PathVariable long id){
        commonSpaceService.deleteCommonSpaceById(id);
        return ResponseEntity.ok().build();
    }


}