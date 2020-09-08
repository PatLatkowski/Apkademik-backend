package pl.edu.pg.apkademikbackend.noticeboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.apkademikbackend.noticeboard.exception.NoticeBoardAlreadyExistException;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;
import pl.edu.pg.apkademikbackend.noticeboard.repository.NoticeBoardRepository;

@RestController
@CrossOrigin(origins = "*")
public class NoticeBoardController {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;


    @PostMapping("/noticeBoard")
    public ResponseEntity<?> addNoticeBoard(@RequestBody NoticeBoard newNoticeBoard) throws Exception{

        if(noticeBoardRepository.findByName(newNoticeBoard.getName()) != null)
            throw new NoticeBoardAlreadyExistException(newNoticeBoard.getName());

        return ResponseEntity.ok(noticeBoardRepository.save(newNoticeBoard));
    }

}
