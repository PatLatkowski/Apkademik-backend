package pl.edu.pg.apkademikbackend.commonSpace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceAlreadyExistException;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceNotFoundException;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.commonSpace.repository.CommonSpaceRepository;
import pl.edu.pg.apkademikbackend.floor.FloorService;
import pl.edu.pg.apkademikbackend.floor.model.Floor;

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

    public List<CommonSpace> getCommonSpaces(String dormName, Integer floorNumber){
        return floorService.getFloor(dormName,floorNumber)
                .getCommonSpaces();
    }
    public CommonSpace getCommonSpace(String dormName, Integer floorNumber, Integer commonSpaceNumber){
        return this.getCommonSpaces(dormName,floorNumber)
                .stream()
                .filter(commonSpace1 -> commonSpaceNumber == commonSpace1.getNumber())
                .findAny()
                .orElseThrow(()-> new CommonSpaceNotFoundException(commonSpaceNumber));
    }
    public List<CommonSpace> saveCommonSpace(String dormName, Integer floorNumber,CommonSpace commonSpace){
        Floor floor = floorService.getFloor(dormName,floorNumber);
        List<CommonSpace> commonSpaces = floor.getCommonSpaces();

        if(commonSpaces.stream()
                .anyMatch(commonSpace1 -> commonSpace.getNumber() == commonSpace1.getNumber()))
            throw new CommonSpaceAlreadyExistException(commonSpace.getNumber());

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

}
