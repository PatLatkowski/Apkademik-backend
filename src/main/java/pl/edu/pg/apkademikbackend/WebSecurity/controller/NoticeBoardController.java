package pl.edu.pg.apkademikbackend.WebSecurity.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.WebSecurity.config.JwtTokenUtil;
import pl.edu.pg.apkademikbackend.WebSecurity.exceptions.RoleAlreadyExistException;
import pl.edu.pg.apkademikbackend.WebSecurity.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Post;
import pl.edu.pg.apkademikbackend.WebSecurity.model.PostDto;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Role;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.NoticeBoardRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.PostRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class NoticeBoardController {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;


    @PostMapping("/addNoticeBoard")
    public ResponseEntity<?> addNoticeBoard(@RequestBody NoticeBoard newNoticeBoard) throws Exception{

        return ResponseEntity.ok(noticeBoardRepository.save(newNoticeBoard));
    }

}
