package pl.edu.pg.apkademikbackend.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pg.apkademikbackend.post.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
     Post findById(long id);

    @Query(value = "select c.* from post as c where c.notice_board_id= :noticeBoard order by c.date desc", nativeQuery = true)
     List<Post> findAllByDate(@Param("noticeBoard") long noticeBoard);

    @Query(value = "select c.page from post as c where c.notice_board_id= :noticeBoard order by c.date desc limit 1", nativeQuery = true)
     Integer getPage(@Param("noticeBoard") long noticeBoard);

    @Query(value = "select c.* from post as c where c.notice_board_id= :noticeBoard and c.page = :page order by c.date desc", nativeQuery = true)
     List<Post> findAllByDateAndPage(@Param("noticeBoard") long noticeBoard,@Param("page") Integer page);


    @Query(value = "select count(id) from post as c  where c.notice_board_id= :noticeBoard ", nativeQuery = true)
     Integer countPosts(@Param("noticeBoard") long noticeBoard);


}
