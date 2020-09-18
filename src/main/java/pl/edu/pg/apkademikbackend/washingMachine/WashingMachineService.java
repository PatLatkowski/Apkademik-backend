package pl.edu.pg.apkademikbackend.washingMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceNotFoundException;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineAlreadyExistException;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineNotFoundException;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineDto;
import pl.edu.pg.apkademikbackend.washingMachine.repository.WashingMachineRepository;

import java.util.List;

@Component
public class WashingMachineService {
    @Autowired
    private WashingMachineRepository washingMachineRepository;

    @Autowired
    private CommonSpaceService commonSpaceService;

    /*public List<WashingMachine> getWashingMachines(String dormName, Integer floorNumber, Integer commonSpaceNumber){
        return commonSpaceService.getCommonSpace(dormName,floorNumber,commonSpaceNumber)
                .getWashingMachines();
    }*/
    public List<WashingMachine> getWashingMachines(long commonSpaceId){
        return commonSpaceService.getCommonSpaceById(commonSpaceId)
                .getWashingMachines();
    }
    /*public WashingMachine getWashingMachine(String dormName, Integer floorNumber, Integer commonSpaceNumber, Integer washingMachineNumber){
        return this.getWashingMachines(dormName,floorNumber,commonSpaceNumber).stream()
                .filter(washingMachine1 -> washingMachineNumber == washingMachine1.getNumber())
                .findAny()
                .orElseThrow(() -> new WashingMachineNotFoundException(washingMachineNumber));
    }*/

    public List<WashingMachine> saveWashingMachine(WashingMachineDto washingMachine){
        CommonSpace commonSpace = commonSpaceService.getCommonSpaceById(washingMachine.getCommonSpaceId());
        List<WashingMachine> washingMachines = commonSpace.getWashingMachines();
        if(washingMachines.stream()
                .anyMatch(washingMachine1 -> washingMachine.getNumber() == washingMachine1.getNumber()))
            throw new WashingMachineAlreadyExistException(washingMachine.getNumber());
        WashingMachine newWashingMachine = new WashingMachine();
        newWashingMachine.setNumber(washingMachine.getNumber());
        newWashingMachine.setStatus(washingMachine.getStatus());
        washingMachines.add(newWashingMachine);
        commonSpace.addWashingMachine(newWashingMachine);
        washingMachineRepository.save(newWashingMachine);
        return washingMachines;
    }

    public WashingMachine getWashingMachineById(long id){
        WashingMachine washingMachine = washingMachineRepository.findById(id);
        if(washingMachine == null)
            throw new WashingMachineNotFoundException(id);
        return washingMachine;
    }

    public WashingMachine updateWashingMachineById(long id, WashingMachine newWashingMachine){
        WashingMachine washingMachine = washingMachineRepository.findById(id);
        if(washingMachine == null)
            throw new CommonSpaceNotFoundException(id);
        if(newWashingMachine.getNumber()!=0)
            washingMachine.setNumber(newWashingMachine.getNumber());
        if(newWashingMachine.getStatus()!=null)
            washingMachine.setStatus(newWashingMachine.getStatus());
        return washingMachineRepository.save(washingMachine);
    }

    public void deleteWashingMachineById(long id){
        WashingMachine washingMachine = washingMachineRepository.findById(id);
        if(washingMachine == null)
            throw new  WashingMachineNotFoundException(id);
        washingMachineRepository.delete(washingMachine);
    }


}
