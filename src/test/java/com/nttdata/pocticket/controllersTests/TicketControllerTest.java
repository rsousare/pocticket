package com.nttdata.pocticket.controllersTests;


import com.nttdata.pocticket.controller.TicketController;
import com.nttdata.pocticket.model.entity.Ticket;

import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTicketsTest(){

        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAllTickets()).thenReturn(tickets);

        List<Ticket> result = ticketController.getAllTickets();

        assertEquals(tickets, result);
        verify(ticketService).getAllTickets();
    }

    @Test
    public void getTicketByIdTest(){
        Long id = 1L;
        Ticket mockTicket = new Ticket();
        mockTicket.setId(id);
        mockTicket.setTitle("Ticket Test");
        when(ticketService.getTicketById(id)).thenReturn(Optional.of(mockTicket));

        Ticket ticket = ticketController.getTicketById(id);

        assertEquals(mockTicket, ticket);
        verify(ticketService).getTicketById(id);
    }

    @Test
    public void createTicketTest(){
        Ticket mocktTicket = new Ticket();
        when(ticketService.createTicket(mocktTicket)).thenReturn(mocktTicket);

        Ticket createdTicket = ticketController.createTicket(mocktTicket);

        assertNotNull(createdTicket);
        assertEquals(mocktTicket, createdTicket);
        verify(ticketService).createTicket(mocktTicket);
    }

    @Test
    public void updateTicketTest(){
        Long id = 1L;
        Ticket existingTicket = mock(Ticket.class);

        Ticket updateTicket = new Ticket(existingTicket.getId(), existingTicket.getTitle(), "Update Title",
                existingTicket.getStatus(), existingTicket.getType(), TicketPriority.HIGH, 20, existingTicket.getEstimate(),
                existingTicket.getCreatedAt(), existingTicket.getProject(), existingTicket.getAssignedTo(), existingTicket.getCreatedBy(),
                existingTicket.getResolvedAt(),existingTicket.getResolvedBy());
        when(ticketService.updateTicket(id, updateTicket)).thenReturn(updateTicket);

        Ticket result = ticketController.updateTicket(id, updateTicket);

        assertNotNull(result);
        assertEquals(updateTicket, result);

        verify(ticketService).updateTicket(id, updateTicket);
    }
}
