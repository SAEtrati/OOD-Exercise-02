package edu.ticket.strategy.input;

import java.util.HashMap;
import java.util.Map;

public class EmailInputHandler implements InputHandlerStrategy {

    @Override
    public Map<String, String> handle(String rawData) {
        System.out.println("Processing input from EMAIL channel...");
        Map<String, String> data = new HashMap<>();
        data.put("title", "Email Request");
        data.put("description", rawData);
        data.put("type", "REQUEST");
        data.put("priority", "MEDIUM");
        return data;
    }
}