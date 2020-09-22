package pl.edu.pg.apkademikbackend.commonSpace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceAlreadyExistException;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceNotFoundException;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpaceDto;
import pl.edu.pg.apkademikbackend.commonSpace.repository.CommonSpaceRepository;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;
import pl.edu.pg.apkademikbackend.floor.model.FloorAndCommonSpaces;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonSpaceService {
    @Autowired
    private CommonSpaceRepository commonSpaceRepository;
    @Autowired
    private FloorService floorService;

    public CommonSpace getCommonSpaceById(long id){
        CommonSpace commonSpace = commonSpaceRepository.findById(id);
        if(commonSpace == null)
            throw new CommonSpaceNotFoundException(id);
        return commonSpace;
    }

    public List<CommonSpace> getCommonSpaces(long floorId){
        return floorService.getFloorById(floorId)
                .getCommonSpaces();
    }

    public List<CommonSpace> saveCommonSpace(CommonSpaceDto newCommonSpace){
        Floor floor = floorService.getFloorById(newCommonSpace.getFloorId());
        List<CommonSpace> commonSpaces = floor.getCommonSpaces();

        if(commonSpaces.stream()
                .anyMatch(commonSpace1 -> newCommonSpace.getNumber() == commonSpace1.getNumber()))
            throw new CommonSpaceAlreadyExistException(newCommonSpace.getNumber());
        CommonSpace commonSpace = new CommonSpace();
        commonSpace.setName(newCommonSpace.getName());
        commonSpace.setNumber(newCommonSpace.getNumber());
        commonSpace.setSize(newCommonSpace.getSize());
        commonSpace.setType(newCommonSpace.getType());
        floor.addCommonSpace(commonSpace);
        commonSpaceRepository.save(commonSpace);
        return commonSpaces;
    }

    public void deleteCommonSpaceById(long id){
        CommonSpace commonSpace = commonSpaceRepository.findById(id);
        if(commonSpace == null)
            throw new  CommonSpaceNotFoundException(id);
        commonSpaceRepository.delete(commonSpace);
    }

    public CommonSpace updateCommonSpaceById(long id, CommonSpace newCommonSpace){
        CommonSpace commonSpace = commonSpaceRepository.findById(id);
        if(commonSpace == null)
            throw new CommonSpaceNotFoundException(id);
        if(newCommonSpace.getName()!=null)
            commonSpace.setName(newCommonSpace.getName());
        if(newCommonSpace.getNumber()!=0)
            commonSpace.setNumber(newCommonSpace.getNumber());
        if(newCommonSpace.getSize()!=0)
            commonSpace.setSize(newCommonSpace.getSize());
        if(newCommonSpace.getName()!=null)
            commonSpace.setName(newCommonSpace.getName());
        if(newCommonSpace.getType()!=null)
            commonSpace.setType(newCommonSpace.getType());
        return commonSpaceRepository.save(commonSpace);
    }

    public List<FloorAndCommonSpaces> getAllCommonSpacesFromDorm(long dormId){
        List<FloorAndCommonSpaces> floorAndCommonSpaces = new ArrayList<>();
        for (Floor floor:
             floorService.getAllFloorsFromDorm(dormId)) {
            floorAndCommonSpaces.add(new FloorAndCommonSpaces(floor.getId(),floor.getNumber(),floor.getCommonSpaces()));
        }
        return floorAndCommonSpaces;
    }

}
