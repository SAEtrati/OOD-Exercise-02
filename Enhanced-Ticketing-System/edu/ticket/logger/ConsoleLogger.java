package edu.ticket.logger;

import java.time.LocalDateTime;

public class ConsoleLogger implements Logger {

    @Override
    public void log(String level, String message) {
        System.out.println("[" + level + "] " + LocalDateTime.now() + " - " + message);
    }
}