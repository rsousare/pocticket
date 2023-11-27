package com.nttdata.blog.cloud.sample.dtos;

import com.nttdata.blog.cloud.sample.entities.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    private Set<Post> posts;
}
