package pl.edu.pg.apkademikbackend.noticeboard.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.noticeboard.model.NoticeBoard;

public interface NoticeBoardRepository extends CrudRepository<NoticeBoard,Integer> {
    public NoticeBoard findByName(String name);
    public NoticeBoard findById(long id);
}
