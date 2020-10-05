package pl.edu.pg.apkademikbackend;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.noticeboard.NoticeBoardService;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.post.PostService;
import pl.edu.pg.apkademikbackend.post.exception.PostNotFoundException;
import pl.edu.pg.apkademikbackend.post.exception.PostNotOnThisNoticeBoardException;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.repository.PostRepository;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PostServiceTests {

    @Mock
    private PostRepository postRepository;
    @Mock
    private NoticeBoardService noticeBoardService;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTitlePostEqualsOkTest() {
        Post post = new Post(1L,"test title","test text");
        when(postRepository.findById(1L)).thenReturn(post);

        String postTitle = postService.getPostById(1L).getTitle();
        assertEquals(postTitle,"test title");
    }

    @Test
    void expectedPostNotFoundException(){
        Post post = new Post();
        when(postRepository.findById(post.getId())).thenReturn(null);
        Assert.assertThrows(PostNotFoundException.class,() -> postService.getPostById(post.getId()));
    }

    @Test
    void expectedPostNotOnThisNoticeBoardException(){
        Post post = new Post(1L,"test title","test text");
        NoticeBoard noticeBoard=new NoticeBoard(1L,"test name");
        NoticeBoard noticeBoard2=new NoticeBoard(2L,"test name 2");
        post.setNoticeBoard(noticeBoard2);
        UserDao user=new UserDao();
        when(postRepository.findById(post.getId())).thenReturn(post);
        when(noticeBoardService.getNoticeBoardById(noticeBoard.getId())).thenReturn(noticeBoard);
        Assert.assertThrows(PostNotOnThisNoticeBoardException.class,() -> postService.getPost(post.getId(),noticeBoard.getId(),user.getEmail()));
    }

    @Test
    void getPagesTest(){
        NoticeBoard noticeBoard=new NoticeBoard(1L,"test name");
        when(noticeBoardService.getNoticeBoardById(noticeBoard.getId())).thenReturn(noticeBoard);
        when(postRepository.getPage(noticeBoard.getId())).thenReturn(5);

        Integer page = postService.getPages(noticeBoard.getId());
        assertEquals(page,5);
    }

    @Test
    void getPostDtoTest(){
        UserDao user=new UserDao("test name","test surname");
        Room room=new Room();
        room.setNumber("test room");
        user.setRoom(room);
        Post post = new Post(1L, "test title", "test text");
        post.setDate(LocalDateTime.now());
        post.setUser(user);

        String postDtoRoom=postService.returnPostData(post).getRoom();

        assertEquals(postDtoRoom,"test room");
    }




}
