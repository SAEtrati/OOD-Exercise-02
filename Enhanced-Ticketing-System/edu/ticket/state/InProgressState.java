package edu.ticket.state;

import edu.ticket.model.Ticket;

public class InProgressState implements TicketState {

    @Override
    public String getName() {
        return "IN_PROGRESS";
    }

    @Override
    public void enter(Ticket ticket) {
        System.out.println("Ticket #" + ticket.getId() + " is now in progress.");
    }

    @Override
    public void exit(Ticket ticket) {
        // no special action on exit
    }

    @Override
    public TicketState next(String action) {
        if ("resolve".equalsIgnoreCase(action)) {
            return new ResolvedState();
        }
        throw new IllegalStateException("Invalid action '" + action + "' for state IN_PROGRESS");
    }
}