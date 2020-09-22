package pl.edu.pg.apkademikbackend.noticeboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.commonSpace.model.CommonSpace;
import pl.edu.pg.apkademikbackend.dorm.model.Dorm;
import pl.edu.pg.apkademikbackend.noticeboard.NoticeBoardService;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardAlreadyExistException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;

@RestController
@CrossOrigin(origins = "*")
public class NoticeBoardController {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;

    @Autowired
    NoticeBoardService noticeBoardService;

    @PostMapping("/noticeBoard")
    public ResponseEntity<?> addNoticeBoard(@RequestBody NoticeBoard noticeBoard){
        return ResponseEntity.ok(noticeBoardService.saveNoticeBoard(noticeBoard));
    }


    @GetMapping("/noticeBoard/{id}")
    public ResponseEntity<?>getNoticeBoardById(@PathVariable long id){
        return ResponseEntity.ok(noticeBoardService.getNoticeBoardById(id));
    }

    @PutMapping("/noticeBoard/{id}")
    public ResponseEntity<?>updateCommonSpaceById(@PathVariable long id, @RequestBody NoticeBoard noticeBoard){
        return ResponseEntity.ok(noticeBoardService.updateNoticeBoardById(id,noticeBoard));
    }

    @DeleteMapping("/noticeBoard/{id}")
    public ResponseEntity<?>deleteNoticeBoardById(@PathVariable long id){
        noticeBoardService.deleteNoticeBoardById(id);
        return ResponseEntity.ok().build();
    }


}
