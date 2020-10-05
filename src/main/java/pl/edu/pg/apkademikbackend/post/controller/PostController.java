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
    public ResponseEntity<?> addPost(@PathVariable long noticeBoard,@RequestBody PostDto newPost,HttpServletRequest request) throws Exception{
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);

        return ResponseEntity.ok(postService.savePost(newPost,noticeBoard,userEmail));
    }

    @GetMapping("/noticeBoard/{noticeBoard}/posts")
    public ResponseEntity<?> getPosts(@PathVariable long noticeBoard)  throws Exception{
        return ResponseEntity.ok(postService.getPosts(noticeBoard));
    }

    @GetMapping("/noticeBoard/{noticeBoard}/pages")
    public ResponseEntity<?> getPages(@PathVariable long noticeBoard)  throws Exception{
        return ResponseEntity.ok(postService.getPages(noticeBoard));
    }

    @GetMapping("/noticeBoard/{noticeBoardId}/page={page}")
    public List<PostDto> getPostsFromPage(@PathVariable long noticeBoardId, @PathVariable Integer page)  throws Exception{
        return postService.getPostsFromPage(noticeBoardId,page);
    }

    @GetMapping("/noticeBoard/{noticeBoardId}/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable long id,@PathVariable long noticeBoardId,HttpServletRequest request)  throws Exception{
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);

        return ResponseEntity.ok(postService.getPost(id,noticeBoardId,userEmail));
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