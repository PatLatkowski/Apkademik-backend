package pl.edu.pg.apkademikbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.exception.DormAlreadyExistException;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DormServiceTests {
	@Mock
	private DormRepository mockedDormRepository;

	@InjectMocks
	private DormService dormService;

	@BeforeEach
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getDormNameEqualsOkTest() {
		Dorm dorm = new Dorm(1L,"test name","test address", 1);
		when(mockedDormRepository.findById(1L)).thenReturn(dorm);

		String dormName = dormService.getDormById(1L).getName();
		assertEquals(dormName,"test name");
	}

	@Test
	void saveDormAddressEqualsOkTest(){
		Dorm dorm = new Dorm(1L,"test name","test address", 1);
		when(mockedDormRepository.findByName(dorm.getName())).thenReturn(null);
		when(mockedDormRepository.save(dorm)).thenReturn(dorm);

		Dorm savedDorm = dormService.saveDorm(dorm);
		String dormAddress = savedDorm.getAddress();
		assertEquals(dormAddress,"test address");
	}
	@Test
	void updateDormByIdFloorEqualsOkTest(){
		Dorm dorm = new Dorm(1L,"test name","test address", 1);
		Dorm dormToUpdate = new Dorm(1L,"test name","test address", 2);
		when(mockedDormRepository.findById(dorm.getId())).thenReturn(dorm);
		when(mockedDormRepository.save(dorm)).thenReturn(dormToUpdate);
		Dorm updatedDorm = dormService.updateDormById(1L,dormToUpdate);
		Integer floorCount = updatedDorm.getFloorCount();
		assertEquals(floorCount,2);
	}

	@Test
	void expectedDormNotFoundException(){
		Dorm dorm = new Dorm(1L,"test name","test address", 1);
		Dorm dormToUpdate = new Dorm(1L,"test name","test address", 2);
		when(mockedDormRepository.findById(dorm.getId())).thenReturn(null);
		when(mockedDormRepository.save(dorm)).thenReturn(dormToUpdate);
		assertThrows(DormNotFoundException.class,() -> dormService.updateDormById(1L,dormToUpdate));
	}

	@Test
	void expectedDormAlreadyExistException(){
		Dorm dorm = new Dorm(1L,"test name","test address", 1);
		when(mockedDormRepository.findByName(dorm.getName())).thenReturn(dorm);
		when(mockedDormRepository.save(dorm)).thenReturn(dorm);
		assertThrows(DormAlreadyExistException.class,() -> dormService.saveDorm(dorm));
	}
}
