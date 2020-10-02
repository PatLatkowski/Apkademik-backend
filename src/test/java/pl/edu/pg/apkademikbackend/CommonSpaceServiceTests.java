package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceNotFoundException;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceType;
import pl.edu.pg.apkademikbackend.commonSpace.repository.CommonSpaceRepository;
import pl.edu.pg.apkademikbackend.floor.FloorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

public class CommonSpaceServiceTests {
    @Mock
    private CommonSpaceRepository commonSpaceRepository;
    @Mock
    private FloorService floorService;
    @InjectMocks
    private CommonSpaceService commonSpaceService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void commonSpaceGetOkTest(){
        CommonSpace commonSpace = new CommonSpace(1,2,"pralnia one", CommonSpaceType.LAUNDRY);
        when(commonSpaceRepository.findById(commonSpace.getId())).thenReturn(commonSpace);
        assertEquals(commonSpace.getId(),commonSpaceService.getCommonSpaceById(commonSpace.getId()).getId());
    }

    @Test
    void expectCommonSpaceNotFoundException(){
        CommonSpace commonSpace = new CommonSpace();
        when(commonSpaceRepository.findById(commonSpace.getId())).thenReturn(null);
        assertThrows(CommonSpaceNotFoundException.class,() -> commonSpaceService.getCommonSpaceById(commonSpace.getId()));
    }

}
