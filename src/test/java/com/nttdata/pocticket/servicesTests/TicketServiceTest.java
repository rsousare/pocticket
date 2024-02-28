package com.nttdata.pocticket.servicesTests;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.Ticket;
import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.model.enums.TicketStatus;
import com.nttdata.pocticket.repositories.PeopleRepository;
import com.nttdata.pocticket.repositories.ProjectRepository;
import com.nttdata.pocticket.repositories.TicketRepository;
import com.nttdata.pocticket.services.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void createTicketTest(){
        Ticket ticket = new Ticket();
        Project project = new Project();
        People assignedTo = new People();

        ticket.setProject(project);
        ticket.setAssignedTo(assignedTo);

        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(peopleRepository.findById(any())).thenReturn(Optional.of(assignedTo));
        when(ticketRepository.save(any())).thenReturn(ticket);

        Ticket createdTicket = ticketService.createTicket(ticket);

        assertNotNull(createdTicket);
        assertEquals(project, createdTicket.getProject());
        assertEquals(assignedTo, createdTicket.getAssignedTo());
    }

    @Test
    public void updateTicketTest(){
        Long id = 1L;
        Ticket existingTicket = mock(Ticket.class);
        Ticket updatedTicket = new Ticket(existingTicket.getIdTicket(), existingTicket.getTitle(), "Update Title",
                existingTicket.getStatus(), existingTicket.getType(), TicketPriority.HIGH, 20, existingTicket.getEstimate(),
                existingTicket.getCreatedAt(), existingTicket.getProject(), existingTicket.getAssignedTo(), existingTicket.getCreatedBy(),
                existingTicket.getResolvedAt(),existingTicket.getResolvedBy());


        when(ticketRepository.findById(id)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(existingTicket)).thenReturn(existingTicket);

        Ticket result = ticketService.updateTicket(id, updatedTicket);

        assertNotNull(result);
        assertEquals(updatedTicket.getTitle(), result.getTitle());
    }

    @Test
    public void deleteTicketTest(){
        Long id = 1L;
        ticketService.deleteTicket(id);
        verify(ticketRepository, times(1)).deleteById(id);
    }

    @Test
    public void updatePriorityTest(){
        Long id = 1L;
        Ticket ticket = new Ticket();
        TicketPriority priority = TicketPriority.HIGH;

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any())).thenReturn(ticket);

        boolean result = ticketService.updatePriority(id, priority);

        assertTrue(result);
        assertEquals(priority, ticket.getPriority());
        verify(ticketRepository, times(1)).findById(id);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void getTopTicketsByCreationTest(){
        People createdBy = new People(1L, "Nome", "email", new ArrayList<>());

        List<Ticket> tickets = new ArrayList<>();

        when(ticketRepository.findByCreatedByOrderByCreatedAtDesc(createdBy)).thenReturn(tickets);

        List<Ticket> result = ticketService.getTopTicketsByCreation(createdBy);
        assertEquals(tickets, result);
    }

    @Test
    public void getTopTicketsByResolutionTest(){
        People resolvedBy = new People(1L, "Nome", "email", new ArrayList<>());

        List<Ticket> tickets = new ArrayList<>();

        when(ticketRepository.findByResolvedByOrderByResolvedAtDesc(resolvedBy)).thenReturn(tickets);

        List<Ticket> result = ticketService.getTopTicketsByResolution(resolvedBy);

        assertEquals(tickets, result);
    }

    @Test
    public void updateStatusToDoneTest(){
        Long id = 1L;
        TicketStatus status = TicketStatus.DONE;

        Ticket ticket = new Ticket();
        Optional<Ticket> optionalTicket = Optional.of(ticket);
        when(ticketRepository.findById(id)).thenReturn(optionalTicket);

        boolean result = ticketService.updateStatus(id, status);

        assertTrue(result);
        assertEquals(status, ticket.getStatus());
        assertNotNull(ticket.getResolvedAt());
        verify(ticketRepository, times(1)).save(ticket);
    }
}
