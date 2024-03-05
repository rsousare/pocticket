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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        if (ticketRepository == null){
            throw new IllegalArgumentException("TicketRepository cannot be null!");
        }
        try {
            List<Ticket> tickets = ticketRepository.findAll();
            if (tickets.isEmpty()){
                throw new IllegalArgumentException("Ticket cannot be empty!");
            }
            return tickets;
        }catch (DataAccessException ex){
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves a ticket by ID.
     * @param id The ID of the ticket to retrieve.
     * @return An Optional containing the ticket if found.
     */
    public Optional<Ticket> getTicketById(Long id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return ticketRepository.findById(id);
    }

    /**
     * Creates a new ticket.
     * @param ticket The ticket to be created.
     * @return The newly created ticket.
     * @throws EntityNotFoundException If the associated project or assigned user is not found.
     */

    public Ticket createTicket(Ticket ticket){
        if (ticket == null){
            throw new IllegalArgumentException("Ticket object cannot be null");
        }
        if (ticket.getTitle() == null || ticket.getTitle().isEmpty()){
            throw new IllegalArgumentException("Ticket Title cannot be null or empty");
        }
        if (ticket.getDescription() == null || ticket.getDescription().isEmpty()){
            throw new IllegalArgumentException("Ticket description cannot be null or empty");
        }
        Project project = projectRepository.findById(ticket.getProject().getId()).orElseThrow(
                ()->new EntityNotFoundException("Project not found whit Id: " + ticket.getProject().getId()));

        People assignedTo = peopleRepository.findById(ticket.getAssignedTo().getId()).orElseThrow(
                ()-> new EntityNotFoundException("People not found whit Id: " + ticket.getAssignedTo().getId()));

        ticket.setProject(project);
        ticket.setAssignedTo(assignedTo);

        return ticketRepository.save(ticket);
    }

    /**
     * Updates an existing ticket.
     * @param updateTicket The ticket with updated data.
     * @return The updated ticket.
     * @throws EntityNotFoundException If the ticket with the given ID is not found.
     */
    public Ticket updateTicket(Ticket updateTicket){
        if (updateTicket == null){
            throw new IllegalArgumentException("Update ticket cannot be null");
        }
        Optional<Ticket> existingTicket = ticketRepository.findById(updateTicket.getId());
        if (existingTicket.isEmpty()) {
            throw new EntityNotFoundException("Ticket with id: " + updateTicket.getId() + " not found");
        }
            Ticket ticketToUpdate = existingTicket.get();
            ticketToUpdate.setTitle(updateTicket.getTitle());
            ticketToUpdate.setDescription(updateTicket.getDescription());
            ticketToUpdate.setStatus(updateTicket.getStatus());
            ticketToUpdate.setPriority(updateTicket.getPriority());
            ticketToUpdate.setProgress(updateTicket.getProgress());
            ticketToUpdate.setEstimate(updateTicket.getEstimate());

            return ticketRepository.save(ticketToUpdate);
    }

    /**
     * Deletes a ticket by ID.
     * @param id The ID of the ticket to delete.
     */
    public void deleteTicket(Long id){
        if (id == null || id == 0){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        ticketRepository.deleteById(id);
    }

    /**
     * Updates the priority of a ticket.
     * @param id The ID of the ticket to update.
     * @param priority The new priority for the ticket.
     * @return true if the priority is updated successfully, false otherwise.
     */
    public boolean updatePriority(Long id, TicketPriority priority){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Invalid ticket id");
        }
        if (priority == null){
            throw new IllegalArgumentException("Ticket priority cannot be null");
        }
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
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Invalid ticket id");
        }
        if (status == null){
            throw new IllegalArgumentException("Status cannot be null");
        }
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
     * Updates the type of ticket.
     * @param id The ID of the ticket to update.
     * @param type The new type for the ticket.
     * @return true if the type is updated successfully, false otherwise.
     */
    public boolean updateType(Long id, TicketType type){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Invalid ticket id");
        }
        if (type == null){
            throw new IllegalArgumentException("Ticket type cannot be null");
        }
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

        try {

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
            throw new EntityNotFoundException("Ticket, user or project not found!");
        }
        }catch (Exception e){
            e.printStackTrace();
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
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Invalid ticket id");
        }
        if (progress < 0){
            throw new IllegalArgumentException("Progress must be a positive number");
        }
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
        if (priority == null){
            throw new IllegalArgumentException("Priority cannot be null");
        }
        List<Ticket> tickets = ticketRepository.findByPriority(priority);
        if (tickets == null){
            throw new RuntimeException("Failed to retrieve tickets by priority " + priority);
        }
        return tickets;
    }

    /**
     * Searches for tickets by status.
     * @param status The status to search for.
     * @return List of tickets with the specified status.
     */
    public List<Ticket> searchByStatus(TicketStatus status){
        if (status == null){
            throw new IllegalArgumentException("Status cannot be null");
        }
        List<Ticket> tickets = ticketRepository.findByStatus(status);
        if (tickets == null){
            throw new RuntimeException("Failed to retrieve tickets by status " + status);
        }
        return tickets;
    }

    /**
     * Retrieves completed tickets.
     * @return List of completed tickets.
     */
    public List<Ticket> searchCompletedTickets(){
        List<Ticket> completedTickets = ticketRepository.findByStatus(TicketStatus.DONE);

        if (completedTickets == null){
            throw new RuntimeException("Failed to retrieve completed tickets");
        }
        if (completedTickets.isEmpty()){
            throw new EntityNotFoundException("Tickets not found");
        }
        return completedTickets;
    }

    /**
     * Retrieves top project tickets by progress.
     * @return List of project tickets with progress information.
     */
    public List<ProjectTicketProgress> getTopProjectTicketsByProgress(){

        List<ProjectTicketProgress> topTicketsWithProgress = new ArrayList<>();

        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()){
            return topTicketsWithProgress;
        }

        for (Project project : projects){
            List<Ticket> projectTickets = ticketRepository.findByProject(project);

            if (projectTickets == null || projectTickets.isEmpty()){
                continue;
            }

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
        if (createdBy == null){
            throw new IllegalArgumentException("Parameter created cannot be null");
        }
        List<Ticket> tickets = ticketRepository.findByCreatedByOrderByCreatedAtDesc(createdBy);
        if (tickets.isEmpty()){
            throw new EntityNotFoundException("No tickets found for user: " + createdBy.getName());
        }
        return tickets;
    }

    /**
     * Retrieves top tickets by resolution date for a given user.
     * @param resolvedBy The user who resolved the tickets.
     * @return List of top tickets resolved by the user.
     */
    public List<Ticket> getTopTicketsByResolution(People resolvedBy){
        if (resolvedBy == null){
            throw new IllegalArgumentException("Parameter resolved cannot be null");
        }

        List<Ticket> tickets = ticketRepository.findByResolvedByOrderByResolvedAtDesc(resolvedBy);

        if (tickets.isEmpty()){
            throw new EntityNotFoundException("No tickets found for user " + resolvedBy.getName());
        }
        return tickets;
    }
}
