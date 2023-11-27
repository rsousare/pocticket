package com.nttdata.blog.cloud.sample.controllers;

import com.github.javafaker.Faker;
import com.nttdata.blog.cloud.sample.dtos.AuthorDTO;
import com.nttdata.blog.cloud.sample.mappers.AuthorMapper;
import com.nttdata.blog.cloud.sample.repositories.AuthorRepository;
import com.nttdata.blog.cloud.sample.services.AuthorService;
import com.nttdata.blog.cloud.sample.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorRestController authorRestController;

    private Faker faker;

    private AuthorMapper authorMapper;

    public void createMockups(){

    }

    @BeforeEach
    public void setup(){
        faker = Faker.instance();
        authorMapper = new AuthorMapper();
        createMockups();
    }

    @Test
    public void givenAnUser_whenItIsCreated_thenItShouldShouldReturnStatusCreated() throws Exception {

       AuthorDTO authorDTO = new AuthorDTO();
       authorDTO.setId(1L);
       authorDTO.setName(faker.name().nameWithMiddle());
       authorDTO.setNickname(faker.name().username());

       when(authorService.save(authorMapper.authorDTOToAuthor(authorDTO))).thenReturn(authorMapper.authorDTOToAuthor(authorDTO));

        mockMvc.perform(post("/v1/blog-service/authors").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.asJsonString(authorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is( 1)))
                .andExpect(jsonPath("$.name", is(authorDTO.getName())))
                .andExpect(jsonPath("$.nickname", is(authorDTO.getNickname())));
    }

    @Test
    public void givenAnUser_whenItIsCreatedMissingNickname_thenItShouldShouldBeReturnedBadRequest() throws Exception {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName(faker.name().nameWithMiddle());

        when(authorService.save(authorMapper.authorDTOToAuthor(authorDTO))).thenReturn(authorMapper.authorDTOToAuthor(authorDTO));

        mockMvc.perform(post("/v1/blog-service/authors").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.asJsonString(authorDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAnNickname_whenItIsFindingTheAuthor_thenItShouldShouldReturnTheAuthor() throws Exception {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName(faker.name().nameWithMiddle());

        when(authorService.save(authorMapper.authorDTOToAuthor(authorDTO))).thenReturn(authorMapper.authorDTOToAuthor(authorDTO));

        mockMvc.perform(post("/v1/blog-service/authors").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.asJsonString(authorDTO)))
                .andExpect(status().isBadRequest());
    }

}
