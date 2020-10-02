package pl.edu.pg.apkademikbackend.dorm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.exception.DormAlreadyExistException;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.dorm.repository.DormRepository;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class DormService {
    @Autowired
    private DormRepository dormRepository;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

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
        Dorm dorm = jwtUserDetailsService.getUser(email).getDorm();
        if(dorm == null)
            throw new DormNotFoundException(0);
        return dorm;
    }

    public List<Dorm> getAllDorms(){
        return dormRepository.findAll();
    }

    public List<NoticeBoard> getAllNoticeBoards(long id){
        return dormRepository.findById(id).getNoticeBoards();
    }

    public List<NoticeBoard> getNoticeBoards( HttpServletRequest request){
        String userEmail=jwtUserDetailsService.getUserEmailFromToken(request);

        Dorm dorm =getDormByUserEmail(userEmail);
        if(dorm==null)
            throw new DormNotFoundException(0);

        List<NoticeBoard> noticeBoards=dorm.getNoticeBoards();
        if(noticeBoards==null)
            throw new NoticeBoardNotFoundException(0);

        return noticeBoards;
    }
}