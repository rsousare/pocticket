package com.nttdata.pocticket.repositories;

import com.nttdata.pocticket.model.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByName(String name);

    @Query("SELECT a FROM Area a JOIN a.projects p JOIN p.tickets t " +
           "GROUP BY a " +
           "ORDER BY COUNT(t.id) DESC")
    List<Area> findAreasWithMostContributors();
}
