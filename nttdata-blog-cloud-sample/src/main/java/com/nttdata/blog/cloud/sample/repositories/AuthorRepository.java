package com.nttdata.blog.cloud.sample.repositories;

import com.nttdata.blog.cloud.sample.entities.Author;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@Qualifier("authorRepository")
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNickname(@Param("nickname") String nickname);
}
