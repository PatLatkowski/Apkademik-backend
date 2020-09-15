package pl.edu.pg.apkademikbackend.floor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.floor.exception.FloorAlreadyExistException;
import pl.edu.pg.apkademikbackend.floor.exception.FloorNotFoundException;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.repository.FloorRepository;

import java.util.List;

@Component
public class FloorService {
    @Autowired
    FloorRepository floorRepository;
    @Autowired
    DormService dormService;

    public List<Floor> getFloors(String dormName){
        return dormService.getDorm(dormName)
                .getFloors();
    }

    public Floor getFloor(String dormName, Integer floorNumber){
        return this.getFloors(dormName).stream()
                .filter(floor1 -> floorNumber.equals(floor1.getNumber()))
                .findAny()
                .orElseThrow(() -> new FloorNotFoundException(floorNumber));
    }

    public Floor getFloor(long id){
        Floor floor = floorRepository.findById(id);
        if(floor==null)
            throw new FloorNotFoundException(id);
        return floor;
    }

    public List<Floor> saveFloor(String dormName, Floor floor){
        Dorm dorm = dormService.getDorm(dormName);
        List<Floor> floors = dorm.getFloors();

        if (floors.stream()
                .anyMatch(floor1 -> floor1.getNumber() == floor.getNumber()))
            throw new FloorAlreadyExistException(floor.getNumber());

        floors.add(floor);
        dorm.setFloors(floors);
        floorRepository.save(floor);
        return floors;
    }

    public Floor updateFloor(long id, Floor updatedFloor){
        Floor floor = this.getFloor(id);
        if(updatedFloor.getNumber()!=0)
            floor.setNumber(updatedFloor.getNumber());
        return floor;
    }

    public void deleteFloor(long id){
        Floor floor = this.getFloor(id);
        floorRepository.delete(floor);
    }

    public List<Floor> getAllFloorsFromDorm(long id){
        return dormService.getDormById(id).getFloors();
    }
}
