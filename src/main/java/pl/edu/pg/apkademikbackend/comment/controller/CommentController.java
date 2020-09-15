package pl.edu.pg.apkademikbackend.comment.controller;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.comment.exception.CommentNotFoundException;
import pl.edu.pg.apkademikbackend.comment.model.Comment;
import pl.edu.pg.apkademikbackend.comment.model.CommentDto;
import pl.edu.pg.apkademikbackend.comment.repository.CommentRepository;
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

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> addComment(@PathVariable String postId, @RequestBody Comment newComment, HttpServletRequest request){
        LocalDateTime date=LocalDateTime.now();
        newComment.setDate(date);

        final String requestTokenHeader = request.getHeader("Authorization");

        String user = null;
        String jwtToken = null;

        jwtToken = requestTokenHeader.substring(7);
        try {
            user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        newComment.setUser(userRepository.findByEmail(user));


        Post post=postRepository.findById(Integer.parseInt(postId));
        if(post==null)
            throw new PostNotFoundException(postId);

        newComment.setPost(post);

        return ResponseEntity.ok(commentRepository.save(newComment));
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<?> findAllComments(@PathVariable String postId,HttpServletRequest request)  throws Exception{

        Post testPost= postRepository.findById(Integer.parseInt(postId));
        if(testPost == null)
            throw new PostNotFoundException(postId);

        final String requestTokenHeader = request.getHeader("Authorization");

        String user = null;
        String jwtToken = null;

        jwtToken = requestTokenHeader.substring(7);
        try {
            user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        UserDao thisUser=userRepository.findByEmail(user);


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

    @PatchMapping("/comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable String id,@RequestBody Comment newComment)  throws Exception{

        Comment comment=commentRepository.findById(Integer.parseInt(id));
        if(comment==null)
            throw new CommentNotFoundException(id);

        comment.setText(newComment.getText());
        commentRepository.save(comment);

        return ResponseEntity.ok(returnCommentData(comment));
    }


    public CommentDto returnCommentData(Comment comment){
        CommentDto newComment=new CommentDto(comment.getId(),comment.getText(),comment.getDate(),comment.getUsers().getName(),comment.getUsers().getSurname());
        return newComment;
    }
}
