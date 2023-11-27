package com.nttdata.blog.cloud.sample.mappers;

import com.nttdata.blog.cloud.sample.dtos.AuthorDTO;
import com.nttdata.blog.cloud.sample.entities.Author;

public class AuthorMapper {

        public Author authorDTOToAuthor(AuthorDTO authorDTO) {
                Author author = new Author();
                author.setId(authorDTO.getId());
                author.setName(authorDTO.getName());
                author.setNickname(authorDTO.getNickname());
                author.setPosts(authorDTO.getPosts());
                return author;
        }

        public AuthorDTO authorToAuthorDTO(Author author) {
                AuthorDTO authorDTO = new AuthorDTO();
                authorDTO.setId(author.getId());
                authorDTO.setName(author.getName());
                authorDTO.setNickname(author.getNickname());
                authorDTO.setPosts(author.getPosts());
                return authorDTO;
        }

}
