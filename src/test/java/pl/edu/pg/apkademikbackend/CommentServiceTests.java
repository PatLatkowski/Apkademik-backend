package pl.edu.pg.apkademikbackend;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pg.apkademikbackend.comment.CommentService;
import pl.edu.pg.apkademikbackend.comment.exception.CommentNotFoundException;
import pl.edu.pg.apkademikbackend.comment.model.Comment;
import pl.edu.pg.apkademikbackend.comment.repository.CommentRepository;
import pl.edu.pg.apkademikbackend.post.PostService;
import pl.edu.pg.apkademikbackend.room.model.Room;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.model.UserDao;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostService postService;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTextCommentEqualsOkTest() {
        Comment comment = new Comment(1L,"test text",LocalDateTime.now());
        when(commentRepository.findById(1L)).thenReturn(comment);

        String commentText = commentService.getCommentById(1L).getText();
        assertEquals(commentText,"test text");
    }

    @Test
    void expectedCommentNotFoundException(){
        Comment comment = new Comment();
        when(commentRepository.findById(comment.getId())).thenReturn(null);
        Assert.assertThrows(CommentNotFoundException.class,() -> commentService.getCommentById(comment.getId()));
    }


    @Test
    void getCommentDtoTest(){
        UserDao user=new UserDao("test name","test surname");
        Room room=new Room();
        room.setNumber("test room");
        user.setRoom(room);
        Comment comment = new Comment(1L, "test title",LocalDateTime.now());
        comment.setUser(user);

        String commentDtoText=commentService.returnCommentData(comment).getAuthor();

        assertEquals(commentDtoText,"test name"+" "+"test surname");
    }




}
