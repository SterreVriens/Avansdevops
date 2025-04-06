package domain.thread.models;

import java.util.ArrayList;

import domain.backlogitem.models.BacklogItem;
import domain.backlogitem.models.states.FinalizedState;
import domain.common.models.User;
import domain.thread.interfaces.IThreadObserver;

//Observer pattern

public class Thread extends ThreadComponent {
    private ArrayList<ThreadComponent> children = new ArrayList<>();
    private ArrayList<IThreadObserver> observers = new ArrayList<>();
    private String title;
    private String description;
    private BacklogItem backlogItem;
    private User owner;

    public Thread(String title, String description, BacklogItem backlogItem, User owner) {
        this.title = title;
        this.description = description;
        this.backlogItem = backlogItem;
        this.owner = owner;

        addObserver(new CommentNotifier());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public User getOwner() {
        return owner;
    }

    public void setTitle(String title, User initiator) {
        if(initiator.equals(owner)) {
            this.title = title;
        } else {
            System.out.println("Only the owner can change the title.");
        }
    }

    public void setDescription(String description, User initiator) {
        if(initiator.equals(owner)) {
            this.description = description;
        } else {
            System.out.println("Only the owner can change the description.");
        }
    }
    
    public void addChild(ThreadComponent component) {
        if(backlogItem.getCurrentState() instanceof FinalizedState) {
            System.out.println("Cannot add comments to a finalized backlog item.");
            return;
        }
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
