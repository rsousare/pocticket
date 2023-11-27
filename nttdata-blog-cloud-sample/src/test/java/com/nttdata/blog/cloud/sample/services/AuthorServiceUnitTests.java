package com.nttdata.blog.cloud.sample.services;

import com.nttdata.blog.cloud.sample.entities.Author;
import com.nttdata.blog.cloud.sample.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class AuthorServiceUnitTests {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void whenGivenAnAuthor_WhenItIsValid_theItShoudBeSaved(){

        Author author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

        given(authorRepository.saveAndFlush(author)).willReturn(author);

        Author savedAuthor = authorService.save(author);


        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getNickname()).isEqualTo(author.getNickname());
    }

    @Test
    public void whenFindAnExistingAuthor_WhenItChangeThisNickName_thenItShouldBeUpdated(){

        Author author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

        when(authorRepository.saveAndFlush(any())).thenReturn(author);

        authorService.save(author);

        when(authorRepository.findByNickname(any())).thenReturn(Optional.of(author));

        Optional<Author> findAuthor = authorService.findByNickname("danilo_shaolin");

        if(findAuthor.isPresent()){

            assertThat(findAuthor.get()).isNotNull();
            assertThat(findAuthor.get().getNickname()).isEqualTo("danilo_shaolin");
            assertThat(findAuthor.get().getName()).isEqualTo("Danilo Caetano Matias dos Santos");
            author.setNickname("danilo_wing_chun");
            authorService.save(author);

            Optional<Author> updatedAuthor = authorService.findByNickname("danilo_wing_chun");

            assertThat(updatedAuthor.get().getNickname()).isEqualTo("danilo_wing_chun");
        }else{
            fail("Fail to update Author");
        }
    }

    @Test
    public void givenAnCreatedAuthor_WhenItTriesToFindThis_thenThisAuthorShouldBeReturned(){

        Author author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

        when(authorRepository.saveAndFlush(any())).thenReturn(author);

        authorService.save(author);

        when(authorRepository.findByNickname(any())).thenReturn(Optional.of(author));

        Optional<Author> findAuthor = authorService.findByNickname("danilo_shaolin");

        if(findAuthor.isPresent()){
            assertThat(findAuthor.get()).isNotNull();
            assertThat(findAuthor.get().getNickname()).isEqualTo("danilo_shaolin");
            assertThat(findAuthor.get().getName()).isEqualTo("Danilo Caetano Matias dos Santos");
        }else{
            fail("Fail to update Author");
        }

    }

    @Test
    public void givenAnCreatedAuthor_WhenItTriesToRemoveThis_thenItShouldBeRemoved(){

        Author author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

        when(authorRepository.saveAndFlush(any())).thenReturn(author);

        authorService.save(author);

        when(authorRepository.findByNickname(any())).thenReturn(Optional.of(author));

        Optional<Author> findAuthor = authorService.findByNickname("danilo_shaolin");

        if(findAuthor.isPresent()){
            authorService.delete(author);
            when(authorRepository.findByNickname(any())).thenReturn(null);
            Optional<Author> deletedAuthor = authorService.findByNickname("danilo_shaolin");
            assertThat(deletedAuthor).isNull();
        }else{
            fail("Fail to update Author");
        }

    }
}
