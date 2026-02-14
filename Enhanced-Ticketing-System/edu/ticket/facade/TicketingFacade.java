package edu.ticket.facade;

import edu.ticket.factory.TicketFactory;
import edu.ticket.infra.DatabaseConnection;
import edu.ticket.logger.Logger;
import edu.ticket.model.Channel;
import edu.ticket.model.Ticket;
import edu.ticket.strategy.assign.AssignStrategy;
import edu.ticket.strategy.input.InputHandlerStrategy;
import edu.ticket.strategy.response.ResponseStrategy;

import java.util.Map;

public class TicketingFacade {

    private final InputHandlerStrategy inputHandler;
    private final TicketFactory ticketFactory;
    private final AssignStrategy assignStrategy;
    private final ResponseStrategy responseStrategy;
    private final Logger logger;

    public TicketingFacade(InputHandlerStrategy inputHandler,
                           TicketFactory ticketFactory,
                           AssignStrategy assignStrategy,
                           ResponseStrategy responseStrategy,
                           Logger logger) {
        this.inputHandler = inputHandler;
        this.ticketFactory = ticketFactory;
        this.assignStrategy = assignStrategy;
        this.responseStrategy = responseStrategy;
        this.logger = logger;
    }

    public Ticket submit(String rawData, Channel channel) {
        // 1. Parse input
        Map<String, String> data = inputHandler.handle(rawData);
        logger.log("INFO", "Input processed from channel: " + channel);

        // 2. Create ticket via factory
        Ticket ticket = ticketFactory.create(data, channel);
        logger.log("INFO", "Ticket created: " + ticket.getSummary());

        // 3. Persist
        DatabaseConnection.getInstance().save(ticket.getSummary());

        return ticket;
    }

    public void assignAndNotify(Ticket ticket) {
        // 4. Assign
        String department = assignStrategy.assign(ticket);
        logger.log("INFO", "Ticket #" + ticket.getId() + " assigned to " + department);
        ticket.applyAction("assign");

        // 5. Notify
        responseStrategy.send(ticket, "Your request has been assigned to " + department + ".");
        logger.log("INFO", "Notification sent for ticket #" + ticket.getId());
    }

    public void transition(Ticket ticket, String action) {
        ticket.applyAction(action);
        logger.log("INFO", "Ticket #" + ticket.getId() + " transitioned via action: " + action);
        DatabaseConnection.getInstance().save(ticket.getSummary());
    }

    public Ticket process(String rawData, Channel channel) {
        Ticket ticket = submit(rawData, channel);
        assignAndNotify(ticket);
        transition(ticket, "start");
        transition(ticket, "resolve");
        transition(ticket, "close");
        return ticket;
    }
}