package pl.edu.pg.apkademikbackend.dorm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.exception.DormAlreadyExistException;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import java.util.List;

@Component
public class DormService {
    @Autowired
    private DormRepository dormRepository;
    @Autowired
    private UserRepository userRepository;

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

    public Dorm getDormById(long id){
        Dorm dorm = dormRepository.findById(id);
        if(dorm == null)
            throw new DormNotFoundException(id);
        return dorm;
    }

    public Dorm updateDormById(long id, Dorm newDorm){
        Dorm dorm = dormRepository.findById(id);
        if(dorm == null)
            throw new DormNotFoundException(id);
        if(newDorm.getName()!=null)
            dorm.setName(newDorm.getName());
        if(newDorm.getAddress()!=null)
            dorm.setAddress(newDorm.getAddress());
        if(newDorm.getFloorCount()!=0)
            dorm.setFloorCount(newDorm.getFloorCount());
        return dormRepository.save(dorm);
    }

    public void deleteDormById(long id){
        Dorm dorm = dormRepository.findById(id);
        if(dorm == null)
            throw new DormNotFoundException(id);
        dormRepository.delete(dorm);
    }

    public Dorm getDormByUserEmail(String email){
        UserDao user = userRepository.findByEmail(email);
        return this.getDormById(user.getDorm().getId());
    }

    public List<Dorm> getAllDorms(){
        return dormRepository.findAll();
    }

    public List<NoticeBoard> getAllNoticeBoards(long id){
        return dormRepository.findById(id).getNoticeBoards();
    }
}