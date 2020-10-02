package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineAlreadyExistException;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineNotFoundException;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineDto;
import pl.edu.pg.apkademikbackend.washingMachine.repository.WashingMachineRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class WashingMachineServiceTests {
    @Mock
    private WashingMachineRepository washingMachineRepository;
    @Mock
    private CommonSpaceService commonSpaceService;
    @InjectMocks
    private WashingMachineService washingMachineService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getWashingMachineByIdOk(){
        WashingMachine washingMachine = new WashingMachine();
        when(washingMachineRepository.findById(washingMachine.getId())).thenReturn(washingMachine);
        assertEquals(washingMachine.getId(),washingMachineService.getWashingMachineById(washingMachine.getId()).getId());
    }

    @Test
    void expectedWashingMachineNotFoundException(){
        WashingMachine washingMachine = new WashingMachine();
        when(washingMachineRepository.findById(washingMachine.getId())).thenReturn(null);
        assertThrows(WashingMachineNotFoundException.class,() -> washingMachineService.getWashingMachineById(washingMachine.getId()));
    }

    @Test
    void expectedWashingMachineAlreadyExistException(){
        CommonSpace commonSpace = new CommonSpace();
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setNumber(1);
        commonSpace.addWashingMachine(washingMachine);
        when(commonSpaceService.getCommonSpaceById(commonSpace.getId())).thenReturn(commonSpace);
        WashingMachineDto washingMachineDto = new WashingMachineDto();
        washingMachineDto.setNumber(1);
        washingMachineDto.setCommonSpaceId(commonSpace.getId());
        assertThrows(WashingMachineAlreadyExistException.class,() -> washingMachineService.saveWashingMachine(washingMachineDto));
    }
}
