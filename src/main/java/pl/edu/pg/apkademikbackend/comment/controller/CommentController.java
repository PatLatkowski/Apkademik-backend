package pl.edu.pg.apkademikbackend.comment.controller;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.comment.CommentService;
import pl.edu.pg.apkademikbackend.comment.model.Comment;
import pl.edu.pg.apkademikbackend.comment.model.CommentDto;
import pl.edu.pg.apkademikbackend.comment.repository.CommentRepository;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.post.exception.PostNotFoundException;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.repository.PostRepository;
import pl.edu.pg.apkademikbackend.user.model.UserDao;
import pl.edu.pg.apkademikbackend.user.repositry.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> addComment(@PathVariable long postId, @RequestBody Comment newComment, HttpServletRequest request){
        LocalDateTime date=LocalDateTime.now();
        newComment.setDate(date);


        String email = getUserEmailFromToken(request);
        newComment.setUser(userRepository.findByEmail(email));


        Post post=postRepository.findById(postId);
        if(post==null)
            throw new PostNotFoundException(postId);

        newComment.setPost(post);

        return ResponseEntity.ok(commentRepository.save(newComment));
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<?> findAllComments(@PathVariable long postId,HttpServletRequest request)  throws Exception{

        Post testPost= postRepository.findById(postId);
        if(testPost == null)
            throw new PostNotFoundException(postId);

        String email = getUserEmailFromToken(request);
        UserDao thisUser=userRepository.findByEmail(email);


        Iterable<Comment> comments=commentRepository.findAllByDate(testPost.getId());
        List<CommentDto> commentsData=new ArrayList<CommentDto>();
        for (Comment comment:comments ) {
            CommentDto newComment=returnCommentData(comment);

            if(comment.getUsers()==thisUser)
                newComment.setIsAuthor(true);
            commentsData.add(newComment);
        }

        return ResponseEntity.ok(commentsData);
    }

    public CommentDto returnCommentData(Comment comment){
        CommentDto newComment=new CommentDto(comment.getId(),comment.getText(),comment.getDate(),comment.getUsers().getName(),comment.getUsers().getSurname());
        return newComment;
    }

    private String getUserEmailFromToken(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        jwtToken = requestTokenHeader.substring(7);
        try {
            userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        return userName;
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<?>getCommentById(@PathVariable long id){
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?>updateCommentById(@PathVariable long id, @RequestBody Comment comment){
        return ResponseEntity.ok(commentService.updateCommentById(id,comment));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?>deleteCommentById(@PathVariable long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().build();
    }

}
