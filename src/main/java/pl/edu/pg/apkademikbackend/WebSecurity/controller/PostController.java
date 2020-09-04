package pl.edu.pg.apkademikbackend.WebSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Post;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.PostRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.UserRepository;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@RequestBody Post newPost) throws Exception{
        LocalDateTime date=LocalDateTime.now();
        newPost.setDate(date);
        return ResponseEntity.ok(postRepository.save(newPost));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> findAllPosts() {

        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> findPost(@PathVariable String id) {

        return ResponseEntity.ok(postRepository.findById(Integer.parseInt(id)));
    }


}
