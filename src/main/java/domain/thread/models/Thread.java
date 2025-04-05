package domain.thread.models;

import java.util.ArrayList;

import domain.backlogitem.models.BacklogItem;
import domain.thread.interfaces.IThreadObserver;

public class Thread extends ThreadComponent {
    private ArrayList<ThreadComponent> children = new ArrayList<>();
    private ArrayList<IThreadObserver> observers = new ArrayList<>();
    private String title;
    private BacklogItem backlogItem;

    public Thread(String title, BacklogItem backlogItem) {
        this.title = title;
        this.backlogItem = backlogItem;
        addObserver(new CommentNotifier());
    }

    public String getTitle() {
        return title;
    }

    public void addChild(ThreadComponent component) {
        notifyObservers(component);
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

    public void addObserver(IThreadObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(IThreadObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(ThreadComponent component) {
        for (IThreadObserver observer : observers) {
            observer.update(component, this);
        }
    }

    public BacklogItem getBacklogItem() {
        return backlogItem;
    }
}
