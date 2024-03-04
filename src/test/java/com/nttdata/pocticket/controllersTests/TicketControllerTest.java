package com.nttdata.pocticket.controllersTests;


import com.nttdata.pocticket.controller.TicketController;
import com.nttdata.pocticket.model.dto.TicketDTO;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.Ticket;
import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.model.enums.TicketStatus;
import com.nttdata.pocticket.model.enums.TicketType;
import com.nttdata.pocticket.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
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

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTicketsTest(){
        when(modelMapper.map(any(), eq(TicketDTO.class))).thenAnswer(invocationOnMock -> {
            Ticket source = invocationOnMock.getArgument(0);
            return new TicketDTO(source.getId(), source.getTitle(), source.getDescription(),
                    source.getStatus(), source.getType(), source.getPriority(), source.getProgress(),
                    source.getEstimate());
        });

        Ticket ticket1 = new Ticket(1L, "Title1", "Original Description", TicketStatus.NEW, TicketType.OTHER,
                TicketPriority.LOW, 0, 10, LocalDateTime.now(), null, null, null, null, null);
        Ticket ticket2 = new Ticket(2L, "Title2", "Original Description", TicketStatus.NEW, TicketType.OTHER,
                TicketPriority.LOW, 0, 10, LocalDateTime.now(), null, null, null, null, null);

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketService.getAllTickets()).thenReturn(tickets);

        List<TicketDTO> result = ticketController.getAllTickets();
        verify(ticketService).getAllTickets();

        assertEquals(2,result.size());
        assertEquals("Title1", result.get(0).getTitle());

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
        Ticket newTicket = new Ticket();
        newTicket.setTitle("New ticket");
        newTicket.setDescription("New description");

        Project project = new Project();
        project.setId(1L);
        newTicket.setProject(project);

        when(ticketService.createTicket(any(Ticket.class))).thenReturn(newTicket);

        Ticket createdTicket = ticketController.createTicket(newTicket);

        assertEquals(newTicket, createdTicket);
        verify(ticketService, times(1)).createTicket(newTicket);
    }

    @Test
    public void updateTicketTest(){
        Long id = 1L;
        Ticket existingTicket = mock(Ticket.class);

        Ticket updateTicket = new Ticket(existingTicket.getId(), existingTicket.getTitle(), "Update Title",
                existingTicket.getStatus(), existingTicket.getType(), TicketPriority.HIGH, 20, existingTicket.getEstimate(),
                existingTicket.getCreatedAt(), existingTicket.getProject(), existingTicket.getAssignedTo(), existingTicket.getCreatedBy(),
                existingTicket.getResolvedAt(),existingTicket.getResolvedBy());
        when(ticketService.updateTicket(updateTicket)).thenReturn(updateTicket);

        Ticket result = ticketController.updateTicket(updateTicket);

        assertNotNull(result);
        assertEquals(updateTicket, result);

        verify(ticketService).updateTicket(updateTicket);
    }
}
