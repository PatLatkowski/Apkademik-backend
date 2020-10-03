package pl.edu.pg.apkademikbackend.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.post.PostService;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.model.PostDto;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/noticeBoard/{noticeBoard}/post")
    public ResponseEntity<?> addPost(@PathVariable String noticeBoard,@RequestBody PostDto newPost,HttpServletRequest request) throws Exception{
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);

        return ResponseEntity.ok(postService.savePost(newPost,noticeBoard,userEmail));
    }

    @GetMapping("/noticeBoard/{noticeBoard}/posts")
    public ResponseEntity<?> getPosts(@PathVariable String noticeBoard)  throws Exception{
        return ResponseEntity.ok(postService.getPosts(noticeBoard));
    }

    @GetMapping("/noticeBoard/{noticeBoard}/pages")
    public ResponseEntity<?> getPages(@PathVariable String noticeBoard)  throws Exception{
        return ResponseEntity.ok(postService.getPages(noticeBoard));
    }

    @GetMapping("/noticeBoard/{noticeBoard}/page={page}")
    public List<PostDto> getPostsFromPage(@PathVariable String noticeBoard, @PathVariable Integer page)  throws Exception{
        return postService.getPostsFromPage(noticeBoard,page);
    }

    @GetMapping("/noticeBoard/{noticeBoard}/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable long id,@PathVariable String noticeBoard,HttpServletRequest request)  throws Exception{
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);

        return ResponseEntity.ok(postService.getPost(id,noticeBoard,userEmail));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?>getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?>updatePostById(@PathVariable long id, @RequestBody PostDto post){
        return ResponseEntity.ok(postService.updatePostById(id,post));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?>deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }


}