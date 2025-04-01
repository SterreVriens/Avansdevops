package project.infrastructure.libs;

public class SlackLibrary {
    public void sendMessage(String message, String channel) {
        
        System.out.println("---------------Slack-----------------");
        System.out.println("Sending message to channel: " + channel);
        System.out.println("Sending message to Slack: " + message);
        System.out.println("-------------------------------------");
    }
}
