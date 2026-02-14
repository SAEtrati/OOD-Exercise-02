package edu.ticket.strategy.input;

import java.util.HashMap;
import java.util.Map;

public class WebInputHandler implements InputHandlerStrategy {

    @Override
    public Map<String, String> handle(String rawData) {
        System.out.println("Processing input from WEB channel...");
        Map<String, String> data = new HashMap<>();
        data.put("title", "Web Request");
        data.put("description", rawData);
        data.put("type", "INCIDENT");
        data.put("priority", "HIGH");
        return data;
    }
}