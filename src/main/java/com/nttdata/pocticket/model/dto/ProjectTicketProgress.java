package com.nttdata.pocticket.model.dto;

import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.Ticket;

public record ProjectTicketProgress(Project project, Ticket topTicket, double progressPercentage) {
}
