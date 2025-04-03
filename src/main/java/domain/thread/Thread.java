package  domain.thread;

import java.util.ArrayList;

public class Thread extends ThreadComponent{
    private ArrayList<ThreadComponent> children = new ArrayList<>();
    private String title;

    public Thread(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    //add and remove children
    public void addChild(ThreadComponent component) {
        children.add(component);
    }
    public void removeChild(ThreadComponent component) {
        children.remove(component);
    }
    public ArrayList<ThreadComponent> getChildren() {
        return children;
    }
    public void print() {
        System.out.println("Title: " + title);
        for (ThreadComponent child : children) {
            System.out.println("-------------------------------------");
            child.print();
        }
    }

}
