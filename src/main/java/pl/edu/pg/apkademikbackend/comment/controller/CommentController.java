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
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;
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
    private CommentService commentService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> addComment(@PathVariable long postId, @RequestBody Comment newComment, HttpServletRequest request) throws Exception{
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);

        return ResponseEntity.ok(commentService.saveComment(newComment,postId,userEmail));
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<?> findAllComments(@PathVariable long postId,HttpServletRequest request)  throws Exception{
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);

        return ResponseEntity.ok(commentService.getComments(postId,userEmail));
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
