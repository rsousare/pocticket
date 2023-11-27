package com.nttdata.blog.cloud.sample.controllers;

import com.nttdata.blog.cloud.sample.dtos.AuthorDTO;
import com.nttdata.blog.cloud.sample.services.AuthorService;
import com.nttdata.blog.cloud.sample.entities.Author;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.blog.cloud.sample.mappers.AuthorMapper;

@RestController
@RequestMapping("/v1/blog-service/authors")
public class AuthorRestController {

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
        this.authorMapper = new AuthorMapper();
    }

    private AuthorService authorService;

    private AuthorMapper authorMapper;

    @PostMapping("")
    public ResponseEntity<AuthorDTO> save(@Valid @RequestBody AuthorDTO authorDTO){

        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        author = authorService.save(author);
        return new ResponseEntity<>(authorMapper.authorToAuthorDTO(author), HttpStatus.CREATED);
    }
}
