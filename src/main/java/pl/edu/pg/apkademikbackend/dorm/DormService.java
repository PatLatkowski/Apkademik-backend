package pl.edu.pg.apkademikbackend.dorm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.exception.DormAlreadyExistException;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;

@Component
public class DormService {
    @Autowired
    private DormRepository dormRepository;

    public Dorm getDorm(String dormName){
        Dorm dorm = dormRepository.findByName(dormName);
        if(dorm == null)
            throw new DormNotFoundException(dormName);
        return dorm;
    }

    public Dorm saveDorm(Dorm dorm){
        Dorm newDorm = dormRepository.findByName(dorm.getName());
        if(newDorm != null)
            throw new DormAlreadyExistException(dorm.getName());
        return dormRepository.save(dorm);
    }
}
