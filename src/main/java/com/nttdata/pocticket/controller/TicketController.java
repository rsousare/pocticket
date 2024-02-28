package com.nttdata.pocticket.controller;


import com.nttdata.pocticket.model.dto.ProjectTicketProgress;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.Ticket;
import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.model.enums.TicketStatus;
import com.nttdata.pocticket.model.enums.TicketType;
import com.nttdata.pocticket.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id).orElse(null);
    }

    @GetMapping("/search/priority")
    public List<Ticket> searchByPriority(@RequestParam TicketPriority priority){
        return ticketService.searchByPriority(priority);
    }

    @GetMapping("/search/status")
    public List<Ticket> searchByStatus(@RequestParam TicketStatus status){
        return ticketService.searchByStatus(status);
    }

    @GetMapping("/search/completed")
    public List<Ticket> searchCompletedTickets(){
        return ticketService.searchCompletedTickets();
    }

    @GetMapping("/top-projects-tickets")
    public List<ProjectTicketProgress> getTopProjectTicketsByProgress(){
        return ticketService.getTopProjectTicketsByProgress();
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket){
        Project project = ticket.getProject();
        if (project == null){
            throw new IllegalArgumentException("O projeto associado ao ticket não pode ser nulo.");
        }
        logger.info("Received ticket request: {}", ticket);
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket){
        return ticketService.updateTicket(id, ticket);
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<String> updatePriority(@PathVariable Long id, @RequestBody TicketPriority priority){
        if (ticketService.updatePriority(id, priority)){
            return ResponseEntity.ok("Priority update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody TicketStatus status){
        if (ticketService.updateStatus(id, status)){
            return ResponseEntity.ok("Status update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    @PutMapping("/{id}/type")
    public ResponseEntity<String> updateType(@PathVariable Long id, @RequestBody TicketType type){
        if (ticketService.updateType(id, type)){
            return ResponseEntity.ok("Type update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<String> assignTicket(@PathVariable Long id, @RequestBody Map<String, Long> requestBody){
        Long idPeople = requestBody.get("idPeople");
        Long idProject = requestBody.get("idProject");
        if (idPeople != null && idProject != null && ticketService.assignTicket(id, idPeople, idProject)){
            return ResponseEntity.ok("Ticket assigned successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket or user or project not found!");
        }
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<String> updateProgress(@PathVariable Long id, @RequestParam int progress){
        if (ticketService.updateProgress(id, progress)){
            return ResponseEntity.ok("Ticket progress update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
    }
}
