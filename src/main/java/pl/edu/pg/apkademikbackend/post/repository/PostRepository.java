package pl.edu.pg.apkademikbackend.post.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pg.apkademikbackend.post.model.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
    public Post findById(long id);

    @Query(value = "select c.* from post as c where c.notice_board_id= :noticeBoard order by c.date desc", nativeQuery = true)
    public List<Post> findAllByDate(@Param("noticeBoard") long noticeBoard);
}
