package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.WashingMachineFailureService;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.exception.WashingMachineFailureNotFoundException;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.model.WashingMachineFailure;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.repositry.WashingMachineFailureRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


public class WashingMachineFailureTests {
    @Mock
    private WashingMachineFailureRepository washingMachineFailureRepository;
    @Mock
    private WashingMachineService washingMachineService;
    @Mock
    private JwtUserDetailsService userDetailsService;
    @InjectMocks
    private WashingMachineFailureService failureService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getWashingMachineFailureByIdOkTest(){
        WashingMachineFailure washingMachineFailure = new WashingMachineFailure();
        when(washingMachineFailureRepository.findById(washingMachineFailure.getId())).thenReturn(washingMachineFailure);
        assertEquals(washingMachineFailure.getId(),failureService.getWashingMachineFailureById(washingMachineFailure.getId()).getId());
    }

    @Test
    void expectedWashingMachineFailureNotFoundException(){
        WashingMachineFailure washingMachineFailure = new WashingMachineFailure();
        when(washingMachineFailureRepository.findById(washingMachineFailure.getId())).thenReturn(null);
        assertThrows(WashingMachineFailureNotFoundException.class,() -> failureService.getWashingMachineFailureById(washingMachineFailure.getId()));
    }
}
