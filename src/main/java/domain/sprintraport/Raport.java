package domain.sprintraport;

//Builder pattern

public class Raport {
    private String header;
    private String content;
    private String footer;

    // 🔹 Protected constructor: alleen te gebruiken door de builder
    // 🔹 Dit zorgt ervoor dat de builder de enige manier is om een Raport-object te maken
    protected Raport(String header, String content, String footer) {
        this.header = header;
        this.content = content;
        this.footer = footer;
    }

     // Getter voor header
     public String getHeader() {
        return header;
    }

    // Getter voor content
    public String getContent() {
        return content;
    }

    // Getter voor footer
    public String getFooter() {
        return footer;
    }

    public void showRaport() {
        System.out.println("===== " + header + " =====");
        System.out.println(content);
        System.out.println("===== " + footer + " =====");
    }

    public void exportAsPDF() {
        System.out.println("Exporting raport as PDF...");
        // Echte PDF-logica zou hier komen
    }

    public void exportAsPNG() {
        System.out.println("Exporting raport as PNG...");
        // Echte PNG-logica zou hier komen
    }

    // 🔹 Zorg ervoor dat de builder deze klasse kan aanmaken
    public static RaportBuilder builder() {
        return new RaportBuilder();
    }
}
