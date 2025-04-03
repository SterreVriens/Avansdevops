package  domain.sprintraport;

public class RaportBuilder {
    private String header = "default header";
    private String content = "default body";
    private String footer = "default footer";

    public RaportBuilder setHeader(String header) {
        this.header = header;
        return this;
    }

    public RaportBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public RaportBuilder setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public Raport build() {
        return new Raport(header, content, footer);
    }


}
