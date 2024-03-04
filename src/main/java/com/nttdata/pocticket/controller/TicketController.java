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

    /**
     * Retrieves all tickets.
     * @return List of all tickets.
     */
    @GetMapping
    public List<Ticket> getAllTickets(){
        return ticketService.getAllTickets();
    }

    /**
     * Retrieves a ticket by its ID.
     * @param id The ID of the ticket to retrieve.
     * @return The ticket if found, null otherwise.
     */
    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id).orElse(null);
    }

    /**
     * Searches tickets by priority.
     * @param priority The priority to search for.
     * @return List of tickets with the specified priority.
     */
    @GetMapping("/search/priority")
    public List<Ticket> searchByPriority(@RequestParam TicketPriority priority){
        return ticketService.searchByPriority(priority);
    }

    /**
     * Searches tickets by status.
     * @param status The status to search for.
     * @return List of tickets with the specified status.
     */
    @GetMapping("/search/status")
    public List<Ticket> searchByStatus(@RequestParam TicketStatus status){
        return ticketService.searchByStatus(status);
    }

    /**
     * Searches for completed tickets.
     * @return List of completed tickets.
     */
    @GetMapping("/search/completed")
    public List<Ticket> searchCompletedTickets(){
        return ticketService.searchCompletedTickets();
    }

    /**
     * Retrieves top project tickets by progress.
     * @return List of project ticket progress.
     */
    @GetMapping("/top-projects-tickets")
    public List<ProjectTicketProgress> getTopProjectTicketsByProgress(){
        return ticketService.getTopProjectTicketsByProgress();
    }

    /**
     * Creates a new ticket.
     * @param ticket The ticket to create.
     * @return The created ticket.
     */
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket){
        Project project = ticket.getProject();
        if (project == null){
            throw new IllegalArgumentException("The project associated with the ticket cannot be null.");
        }
        logger.info("Received ticket request: {}", ticket);
        return ticketService.createTicket(ticket);
    }


    /**
     * Updates an existing ticket.
     * @param ticket The updated ticket data.
     * @return The updated ticket.
     */
    @PutMapping("/{id}")
    public Ticket updateTicket(@RequestBody Ticket ticket){
        return ticketService.updateTicket(ticket);
    }

    /**
     * Updates the priority of a ticket.
     * @param id The ID of the ticket to update.
     * @param priority The new priority.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping("/{id}/priority")
    public ResponseEntity<String> updatePriority(@PathVariable Long id, @RequestBody TicketPriority priority){
        if (ticketService.updatePriority(id, priority)){
            return ResponseEntity.ok("Priority update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    /**
     * Updates the status of a ticket.
     * @param id The ID of the ticket to update.
     * @param status The new status.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody TicketStatus status){
        if (ticketService.updateStatus(id, status)){
            return ResponseEntity.ok("Status update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    /**
     * Updates the type of ticket.
     * @param id The ID of the ticket to update.
     * @param type The new type.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping("/{id}/type")
    public ResponseEntity<String> updateType(@PathVariable Long id, @RequestBody TicketType type){
        if (ticketService.updateType(id, type)){
            return ResponseEntity.ok("Type update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    /**
     * Assigns a ticket to a user and project.
     * @param id The ID of the ticket to assign.
     * @param requestBody The request body containing user and project IDs.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping("/{id}/assign")
    public ResponseEntity<String> assignTicket(@PathVariable Long id, @RequestBody Map<String, Long> requestBody){
        Long idPeople = requestBody.get("id");
        Long idProject = requestBody.get("id");
        if (idPeople != null) {
            if (idProject != null && ticketService.assignTicket(id, idPeople, idProject)) {
                return ResponseEntity.ok("Ticket assigned successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket or user or project not found!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket or user or project not found!");
        }
    }

    /**
     * Updates the progress of a ticket.
     * @param id The ID of the ticket to update.
     * @param progress The new progress value.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PutMapping("/{id}/progress")
    public ResponseEntity<String> updateProgress(@PathVariable Long id, @RequestParam int progress){
        if (ticketService.updateProgress(id, progress)){
            return ResponseEntity.ok("Ticket progress update successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found!");
        }
    }

    /**
     * Deletes a ticket by its ID.
     * @param id The ID of the ticket to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
    }
}
