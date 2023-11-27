package com.nttdata.blog.cloud.sample.repositories;

import com.nttdata.blog.cloud.sample.entities.Author;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class AuthorRepositoryIntegrationTests {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @AfterEach
    public void tearDown(){
        authorRepository.deleteAll();
    }

    @BeforeEach
    public void setup(){
        author = new Author();
        author.setName("Danilo Caetano Matias dos Santos");
        author.setNickname("danilo_shaolin");

        authorRepository.saveAndFlush(author);
    }

    @Test
    public void givenAnAuthor_whenItIsSaved_thenItShouldBeRetrievedAndAssureThatItWasSaved() throws InterruptedException {

         Optional<Author> foundAuthor = authorRepository.findByNickname("danilo_shaolin");

         if(foundAuthor.isPresent()){
             Assertions.assertThat(author.getName()).isEqualTo(foundAuthor.get().getName());
             Assertions.assertThat(author.getNickname()).isEqualTo(foundAuthor.get().getNickname());
         }else{
             Assertions.fail("Fail into retrieve Author!");
         }

    }

    @Test
    public void givenAnAuthor_whenItIsUpdated_theItShouldUpdateThisNickName() throws InterruptedException {

        Author updateAuthor = authorRepository.findByNickname("danilo_shaolin").get();
        updateAuthor.setNickname("danilo_wing_chun");
        authorRepository.saveAndFlush(updateAuthor);

        Author foundAuthor = authorRepository.findByNickname("danilo_wing_chun").get();

        Assertions.assertThat(updateAuthor.getNickname()).isEqualTo(foundAuthor.getNickname());
    }

    @Test
    public void givenAnAuthor_whenItIsDeleted_theItMustAssureThatItWasDeleted(){

        authorRepository.delete(author);

        Assertions.assertThatThrownBy(() -> {
            authorRepository.findById(1L).get();
        }).isInstanceOf(NoSuchElementException.class);

    }

}
