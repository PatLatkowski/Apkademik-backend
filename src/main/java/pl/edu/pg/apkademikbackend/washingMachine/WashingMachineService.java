package pl.edu.pg.apkademikbackend.washingMachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.commonSpace.CommonSpaceService;
import pl.edu.pg.apkademikbackend.commonSpace.exception.CommonSpaceNotFoundException;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineAlreadyExistException;
import pl.edu.pg.apkademikbackend.washingMachine.exception.WashingMachineNotFoundException;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.repository.WashingMachineRepository;

import java.util.List;

@Component
public class WashingMachineService {
    @Autowired
    private WashingMachineRepository washingMachineRepository;

    @Autowired
    private CommonSpaceService commonSpaceService;

    public List<WashingMachine> getWashingMachines(String dormName, Integer floorNumber, Integer commonSpaceNumber){
        return commonSpaceService.getCommonSpace(dormName,floorNumber,commonSpaceNumber)
                .getWashingMachines();
    }
    public WashingMachine getWashingMachine(String dormName, Integer floorNumber, Integer commonSpaceNumber, Integer washingMachineNumber){
        return this.getWashingMachines(dormName,floorNumber,commonSpaceNumber).stream()
                .filter(washingMachine1 -> washingMachineNumber == washingMachine1.getNumber())
                .findAny()
                .orElseThrow(() -> new WashingMachineNotFoundException(washingMachineNumber));
    }
    public List<WashingMachine> saveWashingMachines(String dormName, Integer floorNumber, Integer commonSpaceNumber, WashingMachine washingMachine){
        CommonSpace commonSpace = commonSpaceService.getCommonSpace(dormName,floorNumber,commonSpaceNumber);
        List<WashingMachine> washingMachines = commonSpace.getWashingMachines();
        if(washingMachines.stream()
                .anyMatch(washingMachine1 -> washingMachine.getNumber() == washingMachine1.getNumber()))
            throw new WashingMachineAlreadyExistException(washingMachine.getNumber());

        washingMachines.add(washingMachine);
        commonSpace.setWashingMachines(washingMachines);
        washingMachineRepository.save(washingMachine);
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
