package pl.edu.pg.apkademikbackend.WebSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.model.NoticeBoard;

public interface NoticeBoardRepository extends CrudRepository<NoticeBoard,Integer> {
    public NoticeBoard findByName(String name);
}
