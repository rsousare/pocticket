package com.nttdata.blog.cloud.sample.services;

import com.nttdata.blog.cloud.sample.entities.Author;
import com.nttdata.blog.cloud.sample.entities.Post;
import com.nttdata.blog.cloud.sample.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class PostRepositoryUnitTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Author author;

    @BeforeEach
    public void setup(){
        author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

    }

    @Test
    public void givenACreatedPost_WhenItIsSaved_ThenTheServiceShouldReturnIt(){
        Post post = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");

        given(postRepository.saveAndFlush(any())).willReturn(post);

        var savedPost = postService.save(post);

        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getAuthor()).isEqualTo(author);

    }

    @Test
    public void givenACreatedSetOfPosts_WhenItIsSaved_ThenTheServiceShouldReturnThem(){

        List<Post> posts = new ArrayList<>();

        var post = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");
        posts.add(post);

        var post2 = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");
        posts.add(post2);

        var post3 = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");
        posts.add(post3);

        given(postRepository.saveAllAndFlush(any())).willReturn(posts);

        var savedPosts = postService.save(posts);

        assertThat(savedPosts).isNotNull();
        assertThat(savedPosts.size()).isEqualTo(3);

    }

    @Test
    public void givenACreatedSetOfPosts_WhenItIsSaved_ThenItShouldRemoveOneOfThem(){

        List<Post> posts = new ArrayList<>();

        var post = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");
        posts.add(post);

        var post2 = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");
        posts.add(post2);

        var post3 = new Post();
        post.setDateCreation(LocalDateTime.now());
        post.setAuthor(author);
        post.setText("The passage experienced a surge in popularity during the 1960s when Letraset used it on their dry-transfer sheets, and again during the 90s as desktop publishers bundled the text with their software. Today it's seen all around the web; on templates, websites, and stock designs. Use our generator to get your own, or read on for the authoritative history of lorem ipsum.");
        posts.add(post3);

        given(postRepository.saveAllAndFlush(any())).willReturn(posts);

        var savedPosts = postService.save(posts);

        assertThat(savedPosts).isNotNull();
        assertThat(savedPosts.size()).isEqualTo(3);


        posts.remove(post2);
        doNothing().when(postRepository).delete(any());
        postService.delete(post2);

        when(postRepository.findAllByAuthor(any())).thenReturn(posts);
        var postsDanilo = postService.findAllByAuthor(author);

        assertThat(postsDanilo.size()).isEqualTo(2);
    }


}
