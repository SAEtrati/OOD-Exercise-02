package edu.ticket.state;

import edu.ticket.model.Ticket;

public interface TicketState {

    String getName();

    void enter(Ticket ticket);

    void exit(Ticket ticket);

    TicketState next(String action);
}