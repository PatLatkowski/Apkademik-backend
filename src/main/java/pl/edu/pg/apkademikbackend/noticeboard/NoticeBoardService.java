package pl.edu.pg.apkademikbackend.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardAlreadyExistException;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoardDto;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class NoticeBoardService {


    private final NoticeBoardRepository noticeBoardRepository;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final DormService dormService;

@Autowired
public NoticeBoardService(NoticeBoardRepository noticeBoardRepository,JwtUserDetailsService jwtUserDetailsService,DormService dormService){
    this.noticeBoardRepository=noticeBoardRepository;
    this.jwtUserDetailsService=jwtUserDetailsService;
    this.dormService=dormService;
}

    public List<NoticeBoard> saveNoticeBoard(NoticeBoardDto newNoticeBoard){
        Dorm dorm = dormService.getDormById(newNoticeBoard.getDormId());
        if(dorm==null)
            throw new DormNotFoundException(newNoticeBoard.getDormId());

        List<NoticeBoard> noticeBoards=dorm.getNoticeBoards();
        if(newNoticeBoard.getName() == null)
            throw new NoticeBoardNotFoundException(null);
        if(noticeBoards.stream().anyMatch(noticeBoard1->noticeBoard1.getName()== newNoticeBoard.getName()))
            throw new NoticeBoardAlreadyExistException(newNoticeBoard.getName());

        NoticeBoard noticeBoard=new NoticeBoard();
        noticeBoard.setName(newNoticeBoard.getName());
        noticeBoard.setDorm(dorm);
        noticeBoards.add(noticeBoard);
        dorm.setNoticeBoards(noticeBoards);
        noticeBoardRepository.save(noticeBoard);
        return noticeBoards;
    }


    public NoticeBoard getNoticeBoardById(long id){
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id);
        if(noticeBoard == null)
            throw new NoticeBoardNotFoundException(id);
        return noticeBoard;
    }

    public NoticeBoard getNoticeBoardByName(String name){
        NoticeBoard noticeBoard = noticeBoardRepository.findByName(name);
        if(noticeBoard == null)
            throw new NoticeBoardNotFoundException(name);
        return noticeBoard;
    }


    public NoticeBoard updateNoticeBoardById(long id, NoticeBoardDto newNoticeBoard){
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id);
        if(noticeBoard == null)
            throw new NoticeBoardNotFoundException(id);
        if(newNoticeBoard.getName()!=null)
            noticeBoard.setName(newNoticeBoard.getName());
        return noticeBoardRepository.save(noticeBoard);
    }

    public void deleteNoticeBoardById(long id){
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id);
        if(noticeBoard == null)
            throw new  NoticeBoardNotFoundException(id);
        noticeBoardRepository.delete(noticeBoard);
    }

    public boolean amImemberOfNoticeBoard(long id,String userEmail){
        NoticeBoard testNoticeBoard=getNoticeBoardById(id);
        if(testNoticeBoard==null)
            throw new NoticeBoardNotFoundException(id);

        Dorm dorm =dormService.getDormByUserEmail(userEmail);
        if(dorm==null)
            throw new DormNotFoundException(0);

        List<NoticeBoard> noticeBoards=dorm.getNoticeBoards();
        if(noticeBoards==null)
            throw new NoticeBoardNotFoundException(0);

        for (NoticeBoard noticeBoard:noticeBoards) {
            if(noticeBoard==testNoticeBoard) return true;
        }
        return false;
    }

}