package pl.edu.pg.apkademikbackend;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.dorm.DormService;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.noticeboard.NoticeBoardService;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardAlreadyExistException;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardNotFoundException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoardDto;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class NoticeBoardServiceTests {

    @Mock
    private NoticeBoardRepository noticeBoardRepository;
    @Mock
    private DormService dormService;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @InjectMocks
    private NoticeBoardService noticeBoardService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getNameNoticeBOardEqualsOkTest() {
        NoticeBoard noticeBoard = new NoticeBoard(1L,"test name");
        when(noticeBoardRepository.findById(1L)).thenReturn(noticeBoard);

        String noticeBoardName = noticeBoardService.getNoticeBoardById(1L).getName();
        assertEquals(noticeBoardName,"test name");
    }

    @Test
    void expectedNoticeBoardNotFoundException(){
        NoticeBoard noticeBoard = new NoticeBoard(1L,"test name");
        when(noticeBoardRepository.findById(noticeBoard.getId())).thenReturn(null);
        Assert.assertThrows(NoticeBoardNotFoundException.class,() -> noticeBoardService.getNoticeBoardById(noticeBoard.getId()));
    }
    @Test
    void expectedNoticeBoardAlreadyExistException(){
        NoticeBoardDto noticeBoardDto = new NoticeBoardDto("test name",1L);

        Dorm dorm=new Dorm(1L,"test name","test address",2);
        List<NoticeBoard> noticeBoards=new ArrayList<>();
        NoticeBoard noticeBoard=new NoticeBoard(1L,"test name");
        noticeBoards.add(noticeBoard);
        dorm.setNoticeBoards(noticeBoards);

        when(dormService.getDormById(dorm.getId())).thenReturn(dorm);
        when(noticeBoardRepository.save(noticeBoard)).thenReturn(noticeBoard);
        assertThrows(NoticeBoardAlreadyExistException.class,() -> noticeBoardService.saveNoticeBoard(noticeBoardDto));
    }

    @Test
    void getAmIMemberOfNoticeBoardTest(){
        UserDao user=new UserDao();
        user.setEmail("test email");

        NoticeBoard noticeBoard = new NoticeBoard(1L,"test name");

        List<NoticeBoard> noticeBoardList=new ArrayList<>();
        noticeBoardList.add(noticeBoard);

        Dorm dorm =new Dorm();
        dorm.setNoticeBoards(noticeBoardList);

        when(noticeBoardRepository.findByName(noticeBoard.getName())).thenReturn(noticeBoard);
        when(dormService.getDormByUserEmail(user.getEmail())).thenReturn(dorm);

        Boolean amIMemeberOfNoticeBoard=noticeBoardService.amImemberOfNoticeBoard(noticeBoard.getName(),user.getEmail());
        assertEquals(amIMemeberOfNoticeBoard,true);
    }

}
