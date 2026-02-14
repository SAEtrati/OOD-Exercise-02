package edu.ticket.state;

import edu.ticket.model.Ticket;

public class AssignedState implements TicketState {

    @Override
    public String getName() {
        return "ASSIGNED";
    }

    @Override
    public void enter(Ticket ticket) {
        System.out.println("Ticket #" + ticket.getId() + " assigned.");
    }

    @Override
    public void exit(Ticket ticket) {
        // no special action on exit
    }

    @Override
    public TicketState next(String action) {
        if ("start".equalsIgnoreCase(action)) {
            return new InProgressState();
        }
        throw new IllegalStateException("Invalid action '" + action + "' for state ASSIGNED");
    }
}