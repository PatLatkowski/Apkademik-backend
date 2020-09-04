package pl.edu.pg.apkademikbackend.WebSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pg.apkademikbackend.WebSecurity.model.Post;

public interface PostRepository extends CrudRepository<Post,Integer> {
    public Post findById(long id);
}
