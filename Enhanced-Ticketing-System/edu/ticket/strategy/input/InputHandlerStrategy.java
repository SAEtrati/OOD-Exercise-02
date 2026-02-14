package edu.ticket.strategy.input;

import java.util.Map;

public interface InputHandlerStrategy {

    Map<String, String> handle(String rawData);
}