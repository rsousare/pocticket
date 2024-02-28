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

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PeopleRepository peopleRepository;


    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(Long id){
        return ticketRepository.findById(id);
    }

    public Ticket createTicket(Ticket ticket){
        Project project = projectRepository.findById(ticket.getProject().getIdProject()).orElse(null);
        People assignedTo = peopleRepository.findById(ticket.getAssignedTo().getIdPeople()).orElse(null);
        if (project != null && assignedTo != null){
            ticket.setProject(project);
            ticket.setAssignedTo(assignedTo);
            return ticketRepository.save(ticket);
        }else {
            throw new EntityNotFoundException("Ticket " + ticket + " not found!");
        }
    }

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

    public void deleteTicket(Long id){
        ticketRepository.deleteById(id);
    }

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

    public List<Ticket> searchByPriority(TicketPriority priority){
        return ticketRepository.findByPriority(priority);
    }

    public List<Ticket> searchByStatus(TicketStatus status){
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> searchCompletedTickets(){
        return ticketRepository.findByStatus(TicketStatus.DONE);
    }

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

    public List<Ticket> getTopTicketsByCreation(People createdBy){
        return ticketRepository.findByCreatedByOrderByCreatedAtDesc(createdBy);
    }

    public List<Ticket> getTopTicketsByResolution(People resolvedBy){
        return ticketRepository.findByResolvedByOrderByResolvedAtDesc(resolvedBy);
    }
}
