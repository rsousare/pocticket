package com.nttdata.blog.cloud.sample.repositories;

import com.nttdata.blog.cloud.sample.entities.Author;
import com.nttdata.blog.cloud.sample.entities.Post;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@Qualifier("postRepository")
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(Author author);
}
