package edu.ticket.state;

import edu.ticket.model.Ticket;

public class ClosedState implements TicketState {

    @Override
    public String getName() {
        return "CLOSED";
    }

    @Override
    public void enter(Ticket ticket) {
        System.out.println("Ticket #" + ticket.getId() + " closed.");
    }

    @Override
    public void exit(Ticket ticket) {
        // no special action on exit
    }

    @Override
    public TicketState next(String action) {
        throw new IllegalStateException("Ticket is already closed. No further actions allowed.");
    }
}