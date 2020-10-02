package pl.edu.pg.apkademikbackend.washingReservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceType;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpacesWithWashingMachines;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.model.FloorWithWashingMachines;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineUnavailableException;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineActualReservationsDOT;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineStatus;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationCollideException;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationNotFoundException;
import pl.edu.pg.apkademikbackend.washingReservation.model.*;
import pl.edu.pg.apkademikbackend.washingReservation.repository.WashingReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class WashingReservationService {
    private final WashingReservationRepository washingReservationRepository;
    private final WashingMachineService washingMachineService;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public WashingReservationService(WashingReservationRepository washingReservationRepository, WashingMachineService washingMachineService, JwtUserDetailsService userDetailsService) {
        this.washingReservationRepository = washingReservationRepository;
        this.washingMachineService = washingMachineService;
        this.userDetailsService = userDetailsService;
    }

    public List<WashingReservation> getWashingReservations(long washingMachineId, LocalDate localDate){
        WashingMachine washingMachine = washingMachineService.getWashingMachineById(washingMachineId);
        return washingMachine.getWashingReservations().stream()
                .filter(washingReservation -> washingReservation.getDate().equals(localDate))
                .collect(Collectors.toList());
    }

    public List<WashingReservation> saveWashingReservation(String userEmail, WashingReservationDto washingReservationsDto){
        UserDao user = userDetailsService.getUser(userEmail);
        WashingMachine washingMachine = washingMachineService.getWashingMachineById(washingReservationsDto.getWashingMachineId());
        if(washingMachine.getStatus() == WashingMachineStatus.UNAVAILABLE)
            throw new WashingMachineUnavailableException(washingMachine.getId());
        List<WashingReservation> washingReservationsNow = washingMachine.getWashingReservations();
        List<WashingReservation> washingReservations = washingReservationsDto.getWashingReservations();
        for (WashingReservation washingReservation:
             washingReservations) {
        if(washingReservationsNow.stream()
                .anyMatch(washingReservation1 -> washingReservation1.collide(washingReservation)))
            throw new WashingReservationCollideException(LocalDateTime.of(washingReservation.getDate(),washingReservation.getStart()));
        }
        washingMachine.addWashingReservations(washingReservations);
        user.addWashingReservations(washingReservations);
        return washingReservationRepository.saveAll(washingReservations);
    }

    public List<FloorWithWashingMachines> getWashingReservationsFromAllCommonSpacesByDate(String userEmail, LocalDate date){
        UserDao user = userDetailsService.getUser(userEmail);
        Dorm dorm = user.getDorm();
        if(dorm == null)
            throw new DormNotFoundException(0);
        List<FloorWithWashingMachines> floorWithWashingMachines = new ArrayList<>();
        for (Floor floor1:
                dorm.getFloors()) {
            List<CommonSpacesWithWashingMachines> laundries = new ArrayList<>();
            for (CommonSpace commonSpace:
                    floor1.getCommonSpaces()) {
                if(commonSpace.getType() == CommonSpaceType.LAUNDRY){
                    List<WashingMachineActualReservationsDOT> washingMachinesToSend = new ArrayList<>();
                    for (WashingMachine washingMachine:
                         commonSpace.getWashingMachines()) {
                        List<WashingReservation> washingReservations = washingMachine.getWashingReservations().stream()
                                .filter(washingReservation -> washingReservation.getDate().equals(date))
                                .collect(Collectors.toList());
                        List<WashingReservation> washingReservationsMine = user.getWashingReservations().stream()
                                .filter(washingReservation -> washingReservation.getDate().equals(date))
                                .collect(Collectors.toList());
                        List<WashingReservationIsMine> washingReservationIsMines = new ArrayList<>();
                        for (WashingReservation washingReservation:
                             washingReservations) {
                            for(WashingReservation washingReservationMine:
                            washingReservationsMine){
                                if(washingReservation.equals(washingReservationMine))
                                    washingReservationIsMines.add(new WashingReservationIsMine(true,washingReservation));
                                else
                                    washingReservationIsMines.add(new WashingReservationIsMine(false,washingReservation));
                            }
                        }
                        washingMachinesToSend.add(new WashingMachineActualReservationsDOT(washingMachine,washingReservationIsMines));
                    }
                    laundries.add(new CommonSpacesWithWashingMachines(commonSpace,washingMachinesToSend));
                }
            }
            floorWithWashingMachines.add(new FloorWithWashingMachines(floor1.getId(),floor1.getNumber(),laundries));
        }
        return floorWithWashingMachines;
    }

    public List<DateAndStartingHours> getWashingReservationFromFiveDays(String userEmail, long washingMachineId, LocalDate date) {
        UserDao user = userDetailsService.getUser(userEmail);
        WashingMachine washingMachine = washingMachineService.getWashingMachineById(washingMachineId);
        List<WashingReservation> washingReservationsWithinFiveDays = washingMachine.getWashingReservations().stream()
                .filter(washingReservation -> isWithinTwoDays(washingReservation.getDate(),date))
                .sorted(Comparator.comparing(WashingReservation::getDate).thenComparing(WashingReservation::getStart))
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
                    .ifPresent(andStartingHours -> andStartingHours.addStartingHours(new StartingHour(washingReservation.getStart(),false)));
        }
        List<WashingReservation> myReservations = user.getWashingReservations();
        for (WashingReservation washingReservation:
                myReservations) {
            for(DateAndStartingHours dateAndStartingHours1:
            dateAndStartingHours){
                if(dateAndStartingHours1.getDate().equals(washingReservation.getDate()))
                    dateAndStartingHours1.getStartingHours().stream()
                    .filter(startingHour -> startingHour.getStartingHour().equals(washingReservation.getStart()))
                    .forEach(startingHour -> startingHour.setMine(true));
            }
        }
        return dateAndStartingHours;
    }
    private boolean isWithinTwoDays(LocalDate testDate, LocalDate dateToCompare) {
        return testDate.isAfter(dateToCompare.minus(3, ChronoUnit.DAYS)) && testDate.isBefore(dateToCompare.plus(3,ChronoUnit.DAYS));
    }


    public WashingReservation getWashingReservationById(long id){
        WashingReservation washingReservation = washingReservationRepository.findById(id);
        if(washingReservation == null)
            throw new WashingReservationNotFoundException(id);
        return washingReservation;
    }

    public WashingReservation updateWashingReservationById(long id, WashingReservation newWashingReservation){
        WashingReservation washingReservation = this.getWashingReservationById(id);
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
        WashingReservation washingReservation = this.getWashingReservationById(id);
        washingReservationRepository.delete(washingReservation);
    }

}
