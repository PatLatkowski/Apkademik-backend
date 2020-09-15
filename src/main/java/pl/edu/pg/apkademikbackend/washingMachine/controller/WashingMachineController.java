package pl.edu.pg.apkademikbackend.washingMachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;


@RestController
@CrossOrigin(origins = "*")
public class WashingMachineController {
    @Autowired
    private WashingMachineService washingMachineService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/washingMachine")
    public ResponseEntity<?> addWashingMachine(@RequestParam String dormName, @RequestParam Integer floorNumber, @RequestParam Integer commonSpaceNumber,
                                            @RequestBody WashingMachine washingMachine){
        return ResponseEntity.ok(washingMachineService.saveWashingMachines(dormName,floorNumber,commonSpaceNumber,washingMachine));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/washingMachines")
    public ResponseEntity<?> getWashingMachines(@RequestParam String dormName, @RequestParam Integer floorNumber, @RequestParam Integer commonSpaceNumber){
        return ResponseEntity.ok(washingMachineService.getWashingMachines(dormName,floorNumber,commonSpaceNumber));
    }

}