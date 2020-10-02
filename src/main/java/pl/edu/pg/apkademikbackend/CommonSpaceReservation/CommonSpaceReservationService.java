package pl.edu.pg.apkademikbackend.CommonSpaceReservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.exception.CommonSpaceReservationCollideException;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.exception.CommonSpaceReservationNotFoundException;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservation;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservationDto;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservationWithMineAndCounter;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservationsFromDay;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.repository.CommonSpaceReservationRepository;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonSpaceReservationService {
    private final CommonSpaceReservationRepository commonSpaceReservationRepository;
    private final JwtUserDetailsService userDetailsService;
    private final CommonSpaceService commonSpaceService;

    @Autowired
    public CommonSpaceReservationService(CommonSpaceReservationRepository commonSpaceReservationRepository, JwtUserDetailsService userDetailsService,
                                         CommonSpaceService commonSpaceService) {
        this.commonSpaceReservationRepository = commonSpaceReservationRepository;
        this.userDetailsService = userDetailsService;
        this.commonSpaceService = commonSpaceService;
    }

    public CommonSpaceReservation getCommonSpaceReservationById(long id){
        CommonSpaceReservation commonSpaceReservation = commonSpaceReservationRepository.findById(id);
        if(commonSpaceReservation == null)
            throw new CommonSpaceReservationNotFoundException(id);
        return commonSpaceReservation;
    }

    public List<CommonSpaceReservation> saveCommonSpaceReservations(String userEmail, CommonSpaceReservationDto commonSpaceReservationDto){
        UserDao user = userDetailsService.getUser(userEmail);
        CommonSpace commonSpace = commonSpaceService.getCommonSpaceById(commonSpaceReservationDto.getCommonSpaceId());
        List<CommonSpaceReservation> commonSpaceReservationsNow = commonSpace.getCommonSpaceReservations();
        List<CommonSpaceReservation> commonSpaceReservations = commonSpaceReservationDto.getCommonSpaceReservations();
        for (CommonSpaceReservation commonSpaceReservation:
                commonSpaceReservations) {
            long reservationCounter =commonSpaceReservationsNow.stream()
                    .filter(commonSpaceReservation1 -> commonSpaceReservation1.collide(commonSpaceReservation))
                    .count();
            if(reservationCounter >= commonSpace.getSize())
                throw new CommonSpaceReservationCollideException(LocalDateTime.of(commonSpaceReservation.getDate(),commonSpaceReservation.getStart()));
        }
        commonSpace.addCommonSpaceReservations(commonSpaceReservations);
        user.addCommonSpaceReservations(commonSpaceReservations);
        return commonSpaceReservationRepository.saveAll(commonSpaceReservations);
    }

    public void deleteCommonSpaceReservation(long id){
        CommonSpaceReservation reservation = this.getCommonSpaceReservationById(id);
        commonSpaceReservationRepository.delete(reservation);
    }

    public CommonSpaceReservation updateCommonSpaceReservation(long id, CommonSpaceReservation updatedCommonSpaceReservation){
        CommonSpaceReservation commonSpaceReservation = this.getCommonSpaceReservationById(id);
        if(updatedCommonSpaceReservation.getStatus() != null)
            commonSpaceReservation.setStatus(updatedCommonSpaceReservation.getStatus());
        if(updatedCommonSpaceReservation.getDate() != null)
            commonSpaceReservation.setDate(updatedCommonSpaceReservation.getDate());
        if(updatedCommonSpaceReservation.getStart() != null)
            commonSpaceReservation.setStart(updatedCommonSpaceReservation.getStart());
        if(updatedCommonSpaceReservation.getEnd() != null)
            commonSpaceReservation.setEnd(updatedCommonSpaceReservation.getEnd());
        return commonSpaceReservationRepository.save(commonSpaceReservation);
    }

    public List<CommonSpaceReservationsFromDay> getCommonSpaceReservationsFromFiveDays(String email, long commonSpaceId, LocalDate date){
        UserDao user = userDetailsService.getUser(email);
        List<CommonSpaceReservation> allCommonSpaceReservations = commonSpaceService.getCommonSpaceById(commonSpaceId).getCommonSpaceReservations().stream()
                .filter(commonSpaceReservation -> isWithinTwoDays(commonSpaceReservation.getDate(),date))
                .sorted(Comparator.comparing(CommonSpaceReservation::getDate).thenComparing(CommonSpaceReservation::getStart))
                .collect(Collectors.toList());
        List<CommonSpaceReservationsFromDay> reservationsByDates = new ArrayList<>();
        reservationsByDates.add(new CommonSpaceReservationsFromDay(date.minus(2,ChronoUnit.DAYS)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(date.minus(1,ChronoUnit.DAYS)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(date));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(date.plus(1,ChronoUnit.DAYS)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(date.plus(2,ChronoUnit.DAYS)));

        for(CommonSpaceReservation commonSpaceReservation:
        allCommonSpaceReservations){
            reservationsByDates.stream()
                    .filter(reservationsByDate -> reservationsByDate.getDate().equals(commonSpaceReservation.getDate()))
                    .findAny()
                    .ifPresent(reservationsByDate -> reservationsByDate.addReservation(
                            new CommonSpaceReservationWithMineAndCounter(false,1,commonSpaceReservation.getStart(),
                                    commonSpaceReservation.getEnd())));
        }
        List<CommonSpaceReservation> myReservations = user.getCommonSpaceReservations();
        for(CommonSpaceReservation commonSpaceReservation:
        myReservations){
            for (CommonSpaceReservationsFromDay commonSpaceReservationByDate:
                 reservationsByDates) {
                if(commonSpaceReservation.getDate().equals(commonSpaceReservationByDate.getDate()))
                    commonSpaceReservationByDate.getReservations().stream()
                            .filter(reservation -> reservation.getStart().equals(commonSpaceReservation.getStart()))
                            .forEach(reservation -> reservation.setMine(true));
            }
        }
        return reservationsByDates;
    }

    private boolean isWithinTwoDays(LocalDate testDate, LocalDate dateToCompare) {
        return testDate.isAfter(dateToCompare.minus(3, ChronoUnit.DAYS)) && testDate.isBefore(dateToCompare.plus(3,ChronoUnit.DAYS));
    }
}
