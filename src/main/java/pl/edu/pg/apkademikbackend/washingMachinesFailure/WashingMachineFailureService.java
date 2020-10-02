package pl.edu.pg.apkademikbackend.washingMachinesFailure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.washingMachine.WashingMachineService;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachine;
import pl.edu.pg.apkademikbackend.washingMachine.model.WashingMachineStatus;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.exception.WashingMachineFailureNotFoundException;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.model.FailureDto;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.model.FailureStatus;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.model.WashingMachineFailure;
import pl.edu.pg.apkademikbackend.washingMachinesFailure.repositry.WashingMachineFailureRepository;

import java.util.List;

@Service
public class WashingMachineFailureService {

    private final WashingMachineFailureRepository washingMachineFailureRepository;
    private final WashingMachineService washingMachineService;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public WashingMachineFailureService(WashingMachineFailureRepository washingMachineFailureRepository, WashingMachineService washingMachineService,JwtUserDetailsService userDetailsService) {
        this.washingMachineFailureRepository = washingMachineFailureRepository;
        this.washingMachineService = washingMachineService;
        this.userDetailsService = userDetailsService;
    }

    public WashingMachineFailure getWashingMachineFailureById(long failureId){
        WashingMachineFailure washingMachineFailure = washingMachineFailureRepository.findById(failureId);
        if(washingMachineFailure == null)
            throw new WashingMachineFailureNotFoundException(failureId);
        return washingMachineFailure;
    }

    public List<WashingMachineFailure> getAllWashingMachineFailures(){
        return washingMachineFailureRepository.findAll();
    }

    public WashingMachineFailure updateWashingMachineFailure(long failureId,FailureDto updatedWashingMachineFailure){
        WashingMachineFailure washingMachineFailure = this.getWashingMachineFailureById(failureId);
        if(updatedWashingMachineFailure.getDescription()!=null)
            washingMachineFailure.setDescription(updatedWashingMachineFailure.getDescription());
        if (updatedWashingMachineFailure.getFailureStatus()!=null) {
            if(updatedWashingMachineFailure.getFailureStatus() == FailureStatus.IN_PROGRESS)
                washingMachineService.updateWashingMachineStatus(updatedWashingMachineFailure.getWashingMachineId(),WashingMachineStatus.UNAVAILABLE);
            else if(updatedWashingMachineFailure.getFailureStatus() == FailureStatus.SOLVED)
                washingMachineService.updateWashingMachineStatus(updatedWashingMachineFailure.getWashingMachineId(),WashingMachineStatus.OK);
            washingMachineFailure.setFailureStatus(updatedWashingMachineFailure.getFailureStatus());
        }
        return washingMachineFailureRepository.save(washingMachineFailure);
    }

    public WashingMachineFailure saveWashingMachineFailure(String userEmail,FailureDto failure){
        WashingMachineFailure washingMachineFailure = new WashingMachineFailure();
        WashingMachine washingMachine = washingMachineService.getWashingMachineById(failure.getWashingMachineId());
        UserDao user = userDetailsService.getUser(userEmail);
        washingMachineFailure.setFailureStatus(failure.getFailureStatus());
        washingMachineFailure.setDescription(failure.getDescription());
        washingMachineFailure.setWashingMachine(washingMachine);
        washingMachineFailure.setUserDao(user);
        return washingMachineFailureRepository.save(washingMachineFailure);
    }

    public void deleteWashingMachineFailure(long id){
        WashingMachineFailure washingMachineFailure = this.getWashingMachineFailureById(id);
        washingMachineFailureRepository.delete(washingMachineFailure);
    }
}
