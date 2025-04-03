package project.domain.thread;

public class Comment extends ThreadComponent {
    private String text;
    private String author;
    private String date;

    public Comment(String text, String author, String date) {
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public void print() {
       System.out.println("Comment: " + text + " by " + author + " on " + date);
    }
    
}
