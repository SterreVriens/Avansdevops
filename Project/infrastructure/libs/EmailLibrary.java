package project.infrastructure.libs;

public class EmailLibrary {
    public void sendEmail(String subject, String body, String recipient) {
        System.out.println("---------------Email-----------------");
        System.out.println("Sending email to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("-------------------------------------");
    }
}
