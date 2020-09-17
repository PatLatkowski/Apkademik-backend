package pl.edu.pg.apkademikbackend.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardAlreadyExistException;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;

@Component
public class NoticeBoardService {
    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

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

}