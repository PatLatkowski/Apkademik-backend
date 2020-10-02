package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineUnavailableException;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineStatus;
import pl.edu.pg.apkademikbackend.washingReservation.WashingReservationService;
import pl.edu.pg.apkademikbackend.washingReservation.exception.WashingReservationNotFoundException;
import pl.edu.pg.apkademikbackend.washingReservation.model.DateAndStartingHours;
import pl.edu.pg.apkademikbackend.washingReservation.model.StartingHour;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservation;
import pl.edu.pg.apkademikbackend.washingReservation.model.WashingReservationDto;
import pl.edu.pg.apkademikbackend.washingReservation.repository.WashingReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class WashingReservationServiceTests {
    @Mock
    private WashingReservationRepository washingReservationRepository;
    @Mock
    private WashingMachineService washingMachineService;
    @Mock
    private JwtUserDetailsService userDetailsService;
    @InjectMocks
    private WashingReservationService washingReservationService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getWashingReservationOkTest(){
        WashingReservation washingReservation= new WashingReservation();
        when(washingReservationRepository.findById(washingReservation.getId())).thenReturn(washingReservation);
        assertEquals(washingReservation.getId(),washingReservationService.getWashingReservationById(washingReservation.getId()).getId());
    }

    @Test
    void expectedWashingReservationNotFoundException(){
        WashingReservation washingReservation= new WashingReservation();
        when(washingReservationRepository.findById(washingReservation.getId())).thenReturn(null);
        assertThrows(WashingReservationNotFoundException.class,() -> washingReservationService.getWashingReservationById(washingReservation.getId()));
    }

    @Test
    void expectedWashingMachineUnavailableException(){
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setStatus(WashingMachineStatus.UNAVAILABLE);
        when(washingMachineService.getWashingMachineById(washingMachine.getId())).thenReturn(washingMachine);
        WashingReservationDto washingMachineDto = new WashingReservationDto();
        washingMachineDto.setWashingMachineId(washingMachine.getId());
        assertThrows(WashingMachineUnavailableException.class,() -> washingReservationService.saveWashingReservation("test@mail.com",washingMachineDto));
    }

    @Test
    void getReservationsFromFiveDaysOkTest(){
        UserDao user = new UserDao();
        WashingMachine washingMachine = new WashingMachine();
        List<WashingReservation> washingReservations = new ArrayList<>();

        for(int i=0;i<10;i++) {
            for (int j = 0; j < 2; j++) {
                WashingReservation washingReservation = new WashingReservation();
                washingReservation.setDate(LocalDate.of(2010, 10, 10+i));
                washingReservation.setStart(LocalTime.of(7+j, 0));
                washingReservation.setEnd(LocalTime.of(8+j, 0));
                washingReservations.add(washingReservation);
            }
        }
        washingMachine.setWashingReservations(washingReservations);
        user.addWashingReservations(washingReservations);
        when(userDetailsService.getUser("test@test.pl")).thenReturn(user);
        when(washingMachineService.getWashingMachineById(washingMachine.getId())).thenReturn(washingMachine);

        List<DateAndStartingHours> dateAndStartingHours = new ArrayList<>();
        dateAndStartingHours.add(new DateAndStartingHours(LocalDate.of(2010, 10, 12)));
        dateAndStartingHours.add(new DateAndStartingHours(LocalDate.of(2010, 10, 13)));
        dateAndStartingHours.add(new DateAndStartingHours(LocalDate.of(2010, 10, 14)));
        dateAndStartingHours.add(new DateAndStartingHours(LocalDate.of(2010, 10, 15)));
        dateAndStartingHours.add(new DateAndStartingHours(LocalDate.of(2010, 10, 16)));
        for(int i=0;i<5;i++) {
            List<StartingHour> list = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                list.add(new StartingHour(LocalTime.of(7+j, 0),true));
            }
            dateAndStartingHours.get(i).setStartingHours(list);
        }
        List<DateAndStartingHours> washingReservationsFromFiveDays = washingReservationService.getWashingReservationFromFiveDays("test@test.pl",
                washingMachine.getId(), LocalDate.of(2010, 10, 14));
        assertEquals(dateAndStartingHours,washingReservationsFromFiveDays);

    }

}
