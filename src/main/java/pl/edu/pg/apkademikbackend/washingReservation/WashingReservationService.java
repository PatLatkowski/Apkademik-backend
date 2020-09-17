package pl.edu.pg.apkademikbackend.washingReservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceNotFoundException;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceDTO;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceType;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineDTO;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationCollideException;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationNotFoundException;
import pl.edu.pg.apkademikbackend.washingReservation.model.DateAndStartingHours;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;
import pl.edu.pg.apkademikbackend.washingReservation.repository.WashingReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class WashingReservationService {
    @Autowired
    private WashingReservationRepository washingReservationRepository;
    @Autowired
    private WashingMachineService washingMachineService;
    @Autowired
    private UserRepository userRepository;

    public List<WashingReservation> getWashingReservations(String dormName, Integer floorNumber, Integer commonSpaceNumber,
                                                           Integer washingMachineNumber, LocalDate localDate){
        WashingMachine washingMachine = washingMachineService.getWashingMachine(dormName, floorNumber, commonSpaceNumber, washingMachineNumber);
        return washingMachine.getWashingReservations().stream()
                .filter(washingReservation -> washingReservation.getDate().equals(localDate))
                .collect(Collectors.toList());
    }

    public List<WashingReservation> getWashingReservationsFromWashingMachine(){
        return null;
    }


    public List<WashingReservation> saveWashingReservation(String dormName, Integer floorNumber, Integer commonSpaceNumber,
                                                           Integer washingMachineNumber, WashingReservation washingReservation){
        WashingMachine washingMachine = washingMachineService.getWashingMachine(dormName,floorNumber,commonSpaceNumber,washingMachineNumber);
        List<WashingReservation> washingReservations = washingMachine.getWashingReservations();
        if(washingReservations.stream()
                .anyMatch(washingReservation1 -> washingReservation1.collide(washingReservation)))
            throw new WashingReservationCollideException(LocalDateTime.of(washingReservation.getDate(),washingReservation.getStart()));
        washingReservations.add(washingReservation);
        washingMachine.setWashingReservations(washingReservations);
        washingReservationRepository.save(washingReservation);
        return washingReservations;
    }

    public List<WashingReservation> saveWashingReservation(String userEmail, Integer commonSpaceNumber, Integer washingMachineNumber, WashingReservation[] washingReservations){
        UserDao user = userRepository.findByEmail(userEmail);
        Dorm dorm = user.getDorm();
        CommonSpace commonSpace = null;
        for (Floor floor : dorm.getFloors()) {
            for (CommonSpace commonSpace1:
                 floor.getCommonSpaces()) {
                if(commonSpace1.getNumber() == commonSpaceNumber && commonSpace1.getFloor().getNumber() == floor.getNumber())
                    commonSpace = commonSpace1;
            }
        }
        if(commonSpace == null)
            throw new CommonSpaceNotFoundException(commonSpaceNumber);

        WashingMachine washingMachine = washingMachineService.getWashingMachine(dorm.getName(),commonSpace.getFloor().getNumber(),commonSpaceNumber,washingMachineNumber);
        List<WashingReservation> washingReservationsNow = washingMachine.getWashingReservations();
        for (WashingReservation washingReservation:
             washingReservations) {
        if(washingReservationsNow.stream()
                .anyMatch(washingReservation1 -> washingReservation1.collide(washingReservation)))
            throw new WashingReservationCollideException(LocalDateTime.of(washingReservation.getDate(),washingReservation.getStart()));
        washingReservationsNow.add(washingReservation);
        }
        washingMachine.addWashingReservations(washingReservations);
        user.addWashingReservations(washingReservations);
        return washingReservationRepository.saveAll(Arrays.asList(washingReservations));
    }

    public List<CommonSpaceDTO> getWashingReservationsFromCommonSpaceByDate(String userEmail, LocalDate date){
        UserDao user = userRepository.findByEmail(userEmail);
        Dorm dorm = user.getDorm();
        List<CommonSpaceDTO> laundries = new ArrayList<>();
        for (Floor floor1:
                dorm.getFloors()) {
            for (CommonSpace commonSpace:
                    floor1.getCommonSpaces()) {
                if(commonSpace.getType() == CommonSpaceType.LAUNDRY){
                    List<WashingMachineDTO> washingMachinesToSend = new ArrayList<>();
                    for (WashingMachine washingMachine:
                         commonSpace.getWashingMachines()) {
                        List<WashingReservation> washingReservations = washingMachine.getWashingReservations().stream()
                                .filter(washingReservation -> washingReservation.getDate().equals(date))
                                .collect(Collectors.toList());
                        washingMachinesToSend.add(new WashingMachineDTO(washingMachine.getNumber(),washingMachine.getStatus(),washingReservations));
                    }
                    laundries.add(new CommonSpaceDTO(commonSpace.getNumber(),commonSpace.getSize(),commonSpace.getName(),commonSpace.getType(),washingMachinesToSend));
                }
            }
        }
        return laundries;
    }

    public List<DateAndStartingHours> getWashingReservationFromFiveDays(String userEmail, Integer commonSpaceNumber, Integer washingMachineNumber, LocalDate date) {
        UserDao user = userRepository.findByEmail(userEmail);
        Dorm dorm = user.getDorm();
        CommonSpace commonSpace = null;
        for (Floor floor : dorm.getFloors()) {
            for (CommonSpace commonSpace1 :
                    floor.getCommonSpaces()) {
                if (commonSpace1.getNumber() == commonSpaceNumber && commonSpace1.getFloor().getNumber() == floor.getNumber())
                    commonSpace = commonSpace1;
            }
        }
        if (commonSpace == null)
            throw new CommonSpaceNotFoundException(commonSpaceNumber);

        WashingMachine washingMachine = washingMachineService.getWashingMachine(dorm.getName(), commonSpace.getFloor().getNumber(), commonSpaceNumber, washingMachineNumber);
        List<WashingReservation> washingReservationsWithinFiveDays = washingMachine.getWashingReservations().stream()
                .filter(washingReservation -> isWithinTwoDays(washingReservation.getDate(),date))
                .sorted(Comparator.comparing(WashingReservation::getDate))
                .collect(Collectors.toList());
        List<DateAndStartingHours> dateAndStartingHours = new ArrayList<>();
        dateAndStartingHours.add(new DateAndStartingHours(date.minus(2,ChronoUnit.DAYS)));
        dateAndStartingHours.add(new DateAndStartingHours(date.minus(1,ChronoUnit.DAYS)));
        dateAndStartingHours.add(new DateAndStartingHours(date));
        dateAndStartingHours.add(new DateAndStartingHours(date.plus(1,ChronoUnit.DAYS)));
        dateAndStartingHours.add(new DateAndStartingHours(date.plus(2,ChronoUnit.DAYS)));
        for (WashingReservation washingReservation:
                washingReservationsWithinFiveDays) {
            dateAndStartingHours.stream()
                    .filter(dateAndStartingHours1 -> dateAndStartingHours1.getDate().equals(washingReservation.getDate()))
                    .findAny()
                    .ifPresent(andStartingHours -> andStartingHours.addStartingHours(washingReservation.getStart()));
        }
        return dateAndStartingHours;
    }
    private boolean isWithinTwoDays(LocalDate testDate, LocalDate dateToCompare) {
        return testDate.isAfter(dateToCompare.minus(2, ChronoUnit.DAYS)) && dateToCompare.isBefore(testDate.plus(2,ChronoUnit.DAYS));
    }


    public WashingReservation getWashingReservationById(long id){
        WashingReservation washingReservation = washingReservationRepository.findById(id);
        if(washingReservation == null)
            throw new WashingReservationNotFoundException(id);
        return washingReservation;
    }

    public WashingReservation updateWashingReservationById(long id, WashingReservation newWashingReservation){
        WashingReservation washingReservation = washingReservationRepository.findById(id);
        if(washingReservation == null)
            throw new WashingReservationNotFoundException(id);
        if(newWashingReservation.getStatus()!=null)
            washingReservation.setStatus(newWashingReservation.getStatus());
        if(newWashingReservation.getStart()!=null)
            washingReservation.setStart(newWashingReservation.getStart());
        if(newWashingReservation.getEnd()!=null)
            washingReservation.setEnd(newWashingReservation.getEnd());
        if(newWashingReservation.getDate()!=null)
            washingReservation.setDate(newWashingReservation.getDate());
        return washingReservationRepository.save(washingReservation);
    }

    public void deleteWashingReservationById(long id){
        WashingReservation washingReservation =washingReservationRepository.findById(id);
        if(washingReservation == null)
            throw new  WashingReservationNotFoundException(id);
        washingReservationRepository.delete(washingReservation);
    }

}
