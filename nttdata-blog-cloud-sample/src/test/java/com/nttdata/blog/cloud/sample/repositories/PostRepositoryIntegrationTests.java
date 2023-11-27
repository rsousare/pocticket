package com.nttdata.blog.cloud.sample.repositories;

import com.nttdata.blog.cloud.sample.entities.Author;
import com.nttdata.blog.cloud.sample.entities.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class PostRepositoryIntegrationTests {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void tearDown(){
        authorRepository.deleteAll();
    }

    @Test
    public void givenACreatedPost_WhenItIsBeingSaved_ThenItShouldNotBeSavedWithoutAnAuthor(){

        Post post = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries!");

        Assertions.assertThatThrownBy(() -> {
            postRepository.saveAndFlush(post);
        }).isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test

    public void givenACreatedPost_WhenItIsBeingSaved_ThenItShouLdBeSavedWithAnAuthor(){
        Author author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

        authorRepository.saveAndFlush(author);

        Post post = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries!");
        postRepository.saveAndFlush(post);

        Post foundPost = postRepository.findAll().get(0);
        Assertions.assertThat(foundPost).isNotNull();

        List<Post> posts = postRepository.findAllByAuthor(author);
        Assertions.assertThat(posts.size()).isEqualTo(1);

    }

}
