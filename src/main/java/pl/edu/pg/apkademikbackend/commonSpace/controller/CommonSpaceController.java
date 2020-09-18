package pl.edu.pg.apkademikbackend.commonSpace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceDto;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
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
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private WashingMachineService washingMachineService;

    @PostMapping("/commonSpace")
    public ResponseEntity<?> addCommonSpace(@RequestBody CommonSpaceDto commonSpace){
        return ResponseEntity.ok(commonSpaceService.saveCommonSpace(commonSpace));
    }

    @GetMapping("/commonSpace/washingReservations")
    public ResponseEntity<?> getAllWashingReservationsByDate(HttpServletRequest request,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        String userEmail = userDetailsService.getUserEmailFromToken(request);
        return ResponseEntity.ok(washingReservationService.getWashingReservationsFromCommonSpaceByDate(userEmail,date));
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

    @GetMapping("/commonSpace/{commonSpaceId}/washingMachines")
    public ResponseEntity<?> getWashingMachines(@PathVariable long commonSpaceId){
        return ResponseEntity.ok(washingMachineService.getWashingMachines(commonSpaceId));
    }
}