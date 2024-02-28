package com.nttdata.pocticket.repositories;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.Ticket;
import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.model.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByTitle (String title);
    List<Ticket> findByPriority(TicketPriority priority);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByProject(Project project);
    List<Ticket> findByCreatedByOrderByCreatedAtDesc(People createdBy);
    List<Ticket> findByResolvedByOrderByResolvedAtDesc(People resolvedBy);

    @Query(value = "SELECT " +
                   "idTicket, " +
                   "title, " +
                   "description, " +
                   "status, " +
                   "priority, " +
                   "progress, " +
                   "estimate, " +
                   "project_id, " +
                   "assigned_to_id, " +
                   "created_by_id, " +
                   "COALESCE(resolvedAt, 'N/A') AS resolvedAt, " +
                   "COALESCE(resolved_by_id, 'N/A') AS resolved_by_id " +
                   "FROM ticket", nativeQuery = true)
    List<Ticket> findAllWithResolvedAtAntResolvedBy();

}
