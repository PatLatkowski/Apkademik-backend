package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.CommonSpaceReservationService;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.exception.CommonSpaceReservationNotFoundException;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservation;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservationWithMineAndCounter;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.model.CommonSpaceReservationsFromDay;
import pl.edu.pg.apkademikbackend.CommonSpaceReservation.repository.CommonSpaceReservationRepository;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class CommonSpaceReservationTests {
    @Mock
    private CommonSpaceReservationRepository commonSpaceReservationRepository;
    @Mock
    private JwtUserDetailsService userDetailsService;
    @Mock
    private CommonSpaceService commonSpaceService;
    @InjectMocks
    private CommonSpaceReservationService commonSpaceReservationService;
    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCommonSpaceReservationOk(){
        CommonSpaceReservation commonSpaceReservation = new CommonSpaceReservation();
        when(commonSpaceReservationRepository.findById(commonSpaceReservation.getId())).thenReturn(commonSpaceReservation);
        assertEquals(commonSpaceReservation.getId(),commonSpaceReservationService.getCommonSpaceReservationById(commonSpaceReservation.getId()).getId());
    }

    @Test
    void expectedCommonSpaceReservationNotFoundException(){
        CommonSpaceReservation commonSpaceReservation = new CommonSpaceReservation();
        when(commonSpaceReservationRepository.findById(commonSpaceReservation.getId())).thenReturn(null);
        assertThrows(CommonSpaceReservationNotFoundException.class,() -> commonSpaceReservationService.getCommonSpaceReservationById(commonSpaceReservation.getId()));
    }


    @Test
    void properReservationsFromFiveDays(){
        UserDao user = new UserDao();
        CommonSpace commonSpace = new CommonSpace();
        commonSpace.setSize(10);
        List<CommonSpaceReservation> commonSpaceReservations = new ArrayList<>();

        for(int i=0;i<10;i++) {
            for (int j = 0; j < 2; j++) {
                CommonSpaceReservation commonSpaceReservation = new CommonSpaceReservation();
                commonSpaceReservation.setDate(LocalDate.of(2010, 10, 10+i));
                commonSpaceReservation.setStart(LocalTime.of(7+j, 0));
                commonSpaceReservation.setEnd(LocalTime.of(8+j, 0));
                commonSpaceReservations.add(commonSpaceReservation);
            }
        }
        commonSpace.setCommonSpaceReservations(commonSpaceReservations);
        user.addCommonSpaceReservations(commonSpaceReservations);
        when(userDetailsService.getUser("test@test.pl")).thenReturn(user);
        when(commonSpaceService.getCommonSpaceById(commonSpace.getId())).thenReturn(commonSpace);


        List<CommonSpaceReservationsFromDay> reservationsByDates = new ArrayList<>();
        reservationsByDates.add(new CommonSpaceReservationsFromDay(LocalDate.of(2010, 10, 12)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(LocalDate.of(2010, 10, 13)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(LocalDate.of(2010, 10, 14)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(LocalDate.of(2010, 10, 15)));
        reservationsByDates.add(new CommonSpaceReservationsFromDay(LocalDate.of(2010, 10, 16)));
        for(int i=0;i<5;i++) {
            List<CommonSpaceReservationWithMineAndCounter> list = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                list.add(new CommonSpaceReservationWithMineAndCounter(true,1,LocalTime.of(7+j, 0),LocalTime.of(8+j, 0)));
            }
            reservationsByDates.get(i).setReservations(list);
        }
        List<CommonSpaceReservationsFromDay> commonSpaceReservationsFromFiveDays = commonSpaceReservationService.getCommonSpaceReservationsFromFiveDays("test@test.pl",
                commonSpace.getId(), LocalDate.of(2010, 10, 14));
        assertEquals(reservationsByDates,commonSpaceReservationsFromFiveDays);
    }

}
