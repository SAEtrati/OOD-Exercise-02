package edu.ticket.state;

import edu.ticket.model.Ticket;

public class NewState implements TicketState {

    @Override
    public String getName() {
        return "NEW";
    }

    @Override
    public void enter(Ticket ticket) {
        System.out.println("Ticket #" + ticket.getId() + " created.");
    }

    @Override
    public void exit(Ticket ticket) {
        // no special action on exit
    }

    @Override
    public TicketState next(String action) {
        if ("assign".equalsIgnoreCase(action)) {
            return new AssignedState();
        }
        throw new IllegalStateException("Invalid action '" + action + "' for state NEW");
    }
}