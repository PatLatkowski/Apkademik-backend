package pl.edu.pg.apkademikbackend.floor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.floor.exception.FloorAlreadyExistException;
import pl.edu.pg.apkademikbackend.floor.exception.FloorNotFoundException;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.model.FloorDto;
import pl.edu.pg.apkademikbackend.floor.repository.FloorRepository;

import java.util.List;

@Component
public class FloorService {
    @Autowired
    FloorRepository floorRepository;
    @Autowired
    DormService dormService;

    public List<Floor> getFloors(long dormId){
        return dormService.getDormById(dormId)
                .getFloors();
    }

    public Floor getFloorById(long id){
        Floor floor = floorRepository.findById(id);
        if(floor==null)
            throw new FloorNotFoundException(id);
        return floor;
    }

    public List<Floor> saveFloor(FloorDto newFloor){
        Dorm dorm = dormService.getDormById(newFloor.getDormId());
        List<Floor> floors = dorm.getFloors();
        if(newFloor.getNumber()==null)
            throw new FloorNotFoundException(null);
        if (floors.stream()
                .anyMatch(floor1 -> floor1.getNumber() == newFloor.getNumber()))
            throw new FloorAlreadyExistException(newFloor.getNumber());
        Floor floor = new Floor();
        floor.setNumber(newFloor.getNumber());
        floors.add(floor);
        dorm.setFloors(floors);
        floorRepository.save(floor);
        return floors;
    }

    public Floor updateFloor(long id, Floor updatedFloor){
        Floor floor = this.getFloorById(id);
        if(updatedFloor.getNumber()!=0)
            floor.setNumber(updatedFloor.getNumber());
        return floorRepository.save(floor);
    }

    public void deleteFloor(long id){
        Floor floor = this.getFloorById(id);
        floorRepository.delete(floor);
    }

    public List<Floor> getAllFloorsFromDorm(long id){
        return dormService.getDormById(id).getFloors();
    }
}
