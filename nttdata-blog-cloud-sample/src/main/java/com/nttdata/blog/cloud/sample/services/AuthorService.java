package com.nttdata.blog.cloud.sample.services;

import com.nttdata.blog.cloud.sample.entities.Author;
import com.nttdata.blog.cloud.sample.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("authorService")
public class AuthorService {



    private AuthorRepository authorRepository;

    public Author save(Author author){
        return authorRepository.saveAndFlush(author);
    }

    public Optional<Author> findByNickname(String nickname){
        return authorRepository.findByNickname(nickname);
    }

    public void delete(Author author){
        authorRepository.delete(author);
    }

}
