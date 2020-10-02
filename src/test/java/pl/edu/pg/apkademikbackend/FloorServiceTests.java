package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.exception.FloorNotFoundException;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.model.FloorDto;
import pl.edu.pg.apkademikbackend.floor.repository.FloorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class FloorServiceTests {

    @Mock
    private FloorRepository floorRepository;
    @Mock
    private DormService dormService;

    @InjectMocks
    private FloorService floorService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getFloorNumberEqualsOkTest() {
        Floor floor = new Floor(1);
        when(floorRepository.findById(1L)).thenReturn(floor);

        Integer floorNumber = floorService.getFloorById(1L).getNumber();
        assertEquals(floorNumber,1);
    }

    @Test
    void saveFloorIdEqualsOkTest(){
        Floor floor = new Floor(1);
        Dorm dorm = new Dorm(1L,"test name","test address", 1);
        FloorDto floorDto = new FloorDto(1L, 1);
        when(dormService.getDormById(floorDto.getDormId())).thenReturn(dorm);
        when(floorRepository.save(floor)).thenReturn(floor);

        List<Floor> savedFloor = floorService.saveFloor(floorDto);
        assertEquals(savedFloor.get(0).getId(),floor.getId());
    }
    @Test
    void expectedFloorNotFoundException(){
        when(floorRepository.findById(1L)).thenReturn(null);
        assertThrows(FloorNotFoundException.class,() -> floorService.getFloorById(1L));
    }
}
