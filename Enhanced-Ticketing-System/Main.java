import edu.ticket.facade.TicketingFacade;
import edu.ticket.factory.TicketFactory;
import edu.ticket.logger.ConsoleLogger;
import edu.ticket.logger.Logger;
import edu.ticket.model.Channel;
import edu.ticket.model.Ticket;
import edu.ticket.strategy.assign.AssignStrategy;
import edu.ticket.strategy.assign.DefaultAssignStrategy;
import edu.ticket.strategy.input.InputHandlerStrategy;
import edu.ticket.strategy.input.WebInputHandler;
import edu.ticket.strategy.input.EmailInputHandler;
import edu.ticket.strategy.response.EmailResponseStrategy;
import edu.ticket.strategy.response.ResponseStrategy;
import edu.ticket.strategy.response.SmsResponseStrategy;

public class Main {

    public static void main(String[] args) {

        Logger logger = new ConsoleLogger();
        TicketFactory factory = new TicketFactory();
        AssignStrategy assignStrategy = new DefaultAssignStrategy();
    }
}
