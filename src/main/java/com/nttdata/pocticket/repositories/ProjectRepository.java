package com.nttdata.pocticket.repositories;

import com.nttdata.pocticket.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);

    @Query("SELECT p, COUNT(t.idTicket) AS totalTickets " +
           "FROM Project p JOIN p.tickets t " +
           "GROUP BY p " +
           "ORDER BY totalTickets DESC")
    List<Project> findProjectsWithMostContributors();
}

