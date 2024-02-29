package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.dto.ProjectTicketProgress;
import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.Ticket;

import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.model.enums.TicketStatus;
import com.nttdata.pocticket.model.enums.TicketType;
import com.nttdata.pocticket.repositories.PeopleRepository;
import com.nttdata.pocticket.repositories.ProjectRepository;
import com.nttdata.pocticket.repositories.TicketRepository;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle operations related to tickets.
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PeopleRepository peopleRepository;


    /**
     * Retrieves all tickets.
     * @return List of all tickets.
     */
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    /**
     * Retrieves a ticket by ID.
     * @param id The ID of the ticket to retrieve.
     * @return An Optional containing the ticket if found.
     */
    public Optional<Ticket> getTicketById(Long id){
        return ticketRepository.findById(id);
    }

    /**
     * Creates a new ticket.
     * @param ticket The ticket to be created.
     * @return The newly created ticket.
     * @throws EntityNotFoundException If the associated project or assigned user is not found.
     */
    public Ticket createTicket(Ticket ticket){
        Project project = projectRepository.findById(ticket.getProject().getId()).orElse(null);
        People assignedTo = peopleRepository.findById(ticket.getAssignedTo().getId()).orElse(null);
        if (project != null && assignedTo != null){
            ticket.setProject(project);
            ticket.setAssignedTo(assignedTo);
            return ticketRepository.save(ticket);
        }else {
            throw new EntityNotFoundException("Ticket " + ticket + " not found!");
        }
    }

    /**
     * Updates an existing ticket.
     * @param id The ID of the ticket to update.
     * @param updateTicket The ticket with updated data.
     * @return The updated ticket.
     * @throws EntityNotFoundException If the ticket with the given ID is not found.
     */
    public Ticket updateTicket(Long id, Ticket updateTicket){
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        if (existingTicket.isPresent()){
            Ticket ticketToUpdate = existingTicket.get();
            ticketToUpdate.setTitle(updateTicket.getTitle());
            ticketToUpdate.setDescription(updateTicket.getDescription());
            ticketToUpdate.setStatus(updateTicket.getStatus());
            ticketToUpdate.setPriority(updateTicket.getPriority());
            ticketToUpdate.setProgress(updateTicket.getProgress());
            ticketToUpdate.setEstimate(updateTicket.getEstimate());

            return ticketRepository.save(ticketToUpdate);
        }else {
            throw new EntityNotFoundException("Ticket with id " + id + " not found!");
        }
    }

    /**
     * Deletes a ticket by ID.
     * @param id The ID of the ticket to delete.
     */
    public void deleteTicket(Long id){
        ticketRepository.deleteById(id);
    }

    /**
     * Updates the priority of a ticket.
     * @param id The ID of the ticket to update.
     * @param priority The new priority for the ticket.
     * @return true if the priority is updated successfully, false otherwise.
     */
    public boolean updatePriority(Long id, TicketPriority priority){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.get();
            ticket.setPriority(priority);
            ticketRepository.save(ticket);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Updates the status of a ticket.
     * @param id The ID of the ticket to update.
     * @param status The new status for the ticket.
     * @return true if the status is updated successfully, false otherwise.
     */
    public boolean updateStatus(Long id, TicketStatus status){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.get();
            if(status == TicketStatus.DONE){
                ticket.setStatus(status);
                ticket.setResolvedAt(LocalDateTime.now());
                ticketRepository.save(ticket);

//                People resolvedBy = getCurrentUser();
//                ticket.setResolvedBy(resolvedBy);

            }else {
                ticket.setStatus(status);
                ticketRepository.save(ticket);
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * Updates the type of a ticket.
     * @param id The ID of the ticket to update.
     * @param type The new type for the ticket.
     * @return true if the type is updated successfully, false otherwise.
     */
    public boolean updateType(Long id, TicketType type){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.get();
            ticket.setType(type);
            ticketRepository.save(ticket);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Assigns a ticket to a user and project.
     * @param id The ID of the ticket to assign.
     * @param userId The ID of the user to assign the ticket to.
     * @param projectId The ID of the project the ticket belongs to.
     * @return true if the ticket is assigned successfully, false otherwise.
     */
    public boolean assignTicket(Long id, Long userId, Long projectId){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        Optional<People> optionalPeople = peopleRepository.findById(userId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalTicket.isPresent() && optionalPeople.isPresent() && optionalProject.isPresent()){
            Ticket ticket = optionalTicket.get();
            People user = optionalPeople.get();
            Project project = optionalProject.get();

            ticket.setAssignedTo(user);
            ticket.setProject(project);

            ticketRepository.save(ticket);

            return true;
        }else {
            return false;
        }
    }

    /**
     * Updates the progress of a ticket.
     * @param id The ID of the ticket to update.
     * @param progress The new progress value for the ticket.
     * @return true if the progress is updated successfully, false otherwise.
     */
    public boolean updateProgress(Long id, int progress){
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.get();
            ticket.setProgress(progress);
            ticketRepository.save(ticket);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Searches for tickets by priority.
     * @param priority The priority to search for.
     * @return List of tickets with the specified priority.
     */
    public List<Ticket> searchByPriority(TicketPriority priority){
        return ticketRepository.findByPriority(priority);
    }

    /**
     * Searches for tickets by status.
     * @param status The status to search for.
     * @return List of tickets with the specified status.
     */
    public List<Ticket> searchByStatus(TicketStatus status){
        return ticketRepository.findByStatus(status);
    }

    /**
     * Retrieves completed tickets.
     * @return List of completed tickets.
     */
    public List<Ticket> searchCompletedTickets(){
        return ticketRepository.findByStatus(TicketStatus.DONE);
    }

    /**
     * Retrieves top project tickets by progress.
     * @return List of project tickets with progress information.
     */
    public List<ProjectTicketProgress> getTopProjectTicketsByProgress(){
        List<Project> projects = projectRepository.findAll();
        List<ProjectTicketProgress> topTicketsWithProgress = new ArrayList<>();

        for (Project project : projects){
            List<Ticket> projectTickets = ticketRepository.findByProject(project);
            double totalEstimate = projectTickets.stream().mapToDouble(Ticket::getEstimate).sum();
            double totalProgress = projectTickets.stream().mapToDouble(Ticket::getProgress).sum();

            if (totalEstimate > 0){
                double progressPercentage = (totalProgress / totalEstimate) * 100;
                projectTickets.sort(Comparator.comparingDouble(Ticket::getProgress).reversed());
                Ticket topTicket = projectTickets.get(0);
                topTicketsWithProgress.add(new ProjectTicketProgress(project, topTicket, progressPercentage));
            }
        }
        return topTicketsWithProgress;
    }

    /**
     * Retrieves top tickets by creation date for a given user.
     * @param createdBy The user who created the tickets.
     * @return List of top tickets created by the user.
     */
    public List<Ticket> getTopTicketsByCreation(People createdBy){
        return ticketRepository.findByCreatedByOrderByCreatedAtDesc(createdBy);
    }

    /**
     * Retrieves top tickets by resolution date for a given user.
     * @param resolvedBy The user who resolved the tickets.
     * @return List of top tickets resolved by the user.
     */
    public List<Ticket> getTopTicketsByResolution(People resolvedBy){
        return ticketRepository.findByResolvedByOrderByResolvedAtDesc(resolvedBy);
    }
}
