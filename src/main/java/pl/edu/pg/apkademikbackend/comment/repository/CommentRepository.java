package pl.edu.pg.apkademikbackend.comment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pg.apkademikbackend.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findById(long id);

    @Query(value = "select c.* from comment as c where c.post_id= :post order by c.date desc", nativeQuery = true)
    List<Comment> findAllByDate(@Param("post") long post);
}

