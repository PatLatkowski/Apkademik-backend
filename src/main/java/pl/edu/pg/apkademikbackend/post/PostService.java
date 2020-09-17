package pl.edu.pg.apkademikbackend.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.apkademikbackend.post.exception.PostNotFoundException;
import pl.edu.pg.apkademikbackend.post.model.Post;
import pl.edu.pg.apkademikbackend.post.repository.PostRepository;

@Component
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post getPostById(long id){
        Post post = postRepository.findById(id);
        if(post == null)
            throw new PostNotFoundException(id);
        return post;
    }

    public Post updatePostById(long id, Post newPost){
        Post post = postRepository.findById(id);
        if(post == null)
            throw new PostNotFoundException(id);
        if(newPost.getTitle()!=null)
            post.setTitle(newPost.getTitle());
        if(newPost.getText()!=null)
            post.setText(newPost.getText());
        if(newPost.getDate()!=null)
            post.setDate(newPost.getDate());
        return postRepository.save(post);
    }

    public void deletePostById(long id){
        Post post = postRepository.findById(id);
        if(post == null)
            throw new  PostNotFoundException(id);
        postRepository.delete(post);
    }

}