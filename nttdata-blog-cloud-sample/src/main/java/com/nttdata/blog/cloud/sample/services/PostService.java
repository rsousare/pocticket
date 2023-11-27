package com.nttdata.blog.cloud.sample.services;

import com.nttdata.blog.cloud.sample.entities.Author;
import com.nttdata.blog.cloud.sample.entities.Post;
import com.nttdata.blog.cloud.sample.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("postService")
public class PostService {

    private PostRepository postRepository;

    public Post save(Post post){
        return postRepository.saveAndFlush(post);
    }

    public List<Post> save(List<Post> post){
        return postRepository.saveAllAndFlush(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public List<Post> findAllByAuthor(Author author){
        return postRepository.findAllByAuthor(author);
    }
}
