package pl.edu.pg.apkademikbackend.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.exception.DormNotFoundException;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardAlreadyExistException;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class NoticeBoardService {

    @Autowired
    private  NoticeBoardRepository noticeBoardRepository;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private DormService dormService;


    public NoticeBoard saveNoticeBoard(NoticeBoard noticeBoard){
        NoticeBoard newNoticeBoard = noticeBoardRepository.findByName(noticeBoard.getName());
        if(newNoticeBoard != null)
            throw new NoticeBoardAlreadyExistException(noticeBoard.getName());
        return noticeBoardRepository.save(noticeBoard);
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


    public NoticeBoard updateNoticeBoardById(long id, NoticeBoard newNoticeBoard){
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id);
        if(noticeBoard == null)
            throw new NoticeBoardNotFoundException(id);
        if(newNoticeBoard.getName()!=null)
            noticeBoard.setName(newNoticeBoard.getName());
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

    public boolean amImemberOfNoticeBoard(String noticeBoardName,HttpServletRequest request){
        NoticeBoard testNoticeBoard=getNoticeBoardByName(noticeBoardName);
        if(testNoticeBoard==null)
            throw new NoticeBoardNotFoundException(noticeBoardName);

        String userEmail=jwtUserDetailsService.getUserEmailFromToken(request);

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