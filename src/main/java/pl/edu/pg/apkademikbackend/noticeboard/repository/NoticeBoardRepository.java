package pl.edu.pg.apkademikbackend.noticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Integer> {
    public NoticeBoard findByName(String name);
    public NoticeBoard findById(long id);
}
