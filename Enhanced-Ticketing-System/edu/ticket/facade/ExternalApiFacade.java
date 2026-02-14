package edu.ticket.facade;

public class ExternalApiFacade {

    public void sendEmail(String to, String subject, String body) {
        System.out.println("ExternalAPI -> Email sent to " + to + " | Subject: " + subject);
    }

    public void sendSms(String to, String body) {
        System.out.println("ExternalAPI -> SMS sent to " + to + " | Body: " + body);
    }

    public void pushPortalNotification(String userId, String body) {
        System.out.println("ExternalAPI -> Portal notification to user " + userId + " | Body: " + body);
    }
}