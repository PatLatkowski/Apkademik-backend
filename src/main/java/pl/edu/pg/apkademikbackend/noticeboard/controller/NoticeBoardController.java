package pl.edu.pg.apkademikbackend.noticeboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.noticeboard.NoticeBoardService;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoardDto;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;
import pl.edu.pg.apkademikbackend.user.JwtUserDetailsService;
import pl.edu.pg.apkademikbackend.user.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class NoticeBoardController {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;

    @Autowired
    NoticeBoardService noticeBoardService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/noticeBoard")
    public ResponseEntity<?> addNoticeBoard(@RequestBody NoticeBoardDto noticeBoard){
        return ResponseEntity.ok(noticeBoardService.saveNoticeBoard(noticeBoard));
    }

    @GetMapping("/noticeBoard/{id}")
    public ResponseEntity<?>getNoticeBoardById(@PathVariable long id){
        return ResponseEntity.ok(noticeBoardService.getNoticeBoardById(id));
    }

    @PutMapping("/noticeBoard/{id}")
    public ResponseEntity<?>updateCommonSpaceById(@PathVariable long id, @RequestBody NoticeBoardDto noticeBoard){
        return ResponseEntity.ok(noticeBoardService.updateNoticeBoardById(id,noticeBoard));
    }

    @DeleteMapping("/noticeBoard/{id}")
    public ResponseEntity<?>deleteNoticeBoardById(@PathVariable long id){
        noticeBoardService.deleteNoticeBoardById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/noticeBoard/{noticeBoard}/member")
    public ResponseEntity<?>amIMemberOfNoticeBoard(@PathVariable String noticeBoard, HttpServletRequest request){
        String userEmail= jwtUserDetailsService.getUserEmailFromToken(request);
        if(userEmail==null)
            throw new UserNotFoundException(userEmail);
        return ResponseEntity.ok(noticeBoardService.amImemberOfNoticeBoard(noticeBoard,userEmail));
    }

}
