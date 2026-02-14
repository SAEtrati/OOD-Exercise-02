package edu.ticket.state;

import edu.ticket.model.Ticket;

public class ResolvedState implements TicketState {

    @Override
    public String getName() {
        return "RESOLVED";
    }

    @Override
    public void enter(Ticket ticket) {
        System.out.println("Ticket #" + ticket.getId() + " resolved.");
    }

    @Override
    public void exit(Ticket ticket) {
        // no special action on exit
    }

    @Override
    public TicketState next(String action) {
        if ("close".equalsIgnoreCase(action)) {
            return new ClosedState();
        }
        throw new IllegalStateException("Invalid action '" + action + "' for state RESOLVED");
    }
}