package  domain.backlogitem.models;

import java.util.ArrayList;

import  domain.backlogitem.interfaces.IBacklogItemObserver;
import  domain.backlogitem.interfaces.IBacklogItemState;
import  domain.backlogitem.models.states.ToDoState;
import  domain.common.models.Backlog;
import  domain.common.models.User;
import  domain.notification.models.NotificationService;
import  domain.sprint.Sprint;
import  domain.thread.Thread;

public class BacklogItem {
    private Integer id;
    private String title;
    private String description;
    private IBacklogItemState currentState = new ToDoState();
    private ArrayList<IBacklogItemObserver> observers = new ArrayList<>();
    private ArrayList<Activty> activities = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private User assignedTo;
    private Backlog backlog;
    private Sprint sprint; // Coupled when the BacklogItem is assigned to a Sprint

    public BacklogItem(Integer id, String title, String description, User assignedTo, Backlog backlog) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.backlog = backlog;

        addObserver(new StateNotifier());
        backlog.addBacklogItem(this);
    }

    public void setState(IBacklogItemState state) {
        this.currentState = state;
        notifyObservers();
    }

    public void addObserver(IBacklogItemObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(IBacklogItemObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (IBacklogItemObserver observer : observers) {
            observer.Update(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IBacklogItemState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(IBacklogItemState currentState) {
        this.currentState = currentState;
    }

    public ArrayList<IBacklogItemObserver> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<IBacklogItemObserver> observers) {
        this.observers = observers;
    }

    public ArrayList<Activty> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activty> activities) {
        this.activities = activities;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public User getScrumMaster() {
        return sprint.getScrumMaster();
    }

    public User[] getTesters() {
        return backlog.getTesters();
    }

    public void printThreads() {
        for (int i = 0; i < threads.size(); i++) {
            System.out.println("\nIndex of thread: " + i);
            threads.get(i).print();
            System.out.println();
        }
    }

    public void addThread(Thread thread) {
        threads.add(thread);
    }

    public void removeThread(Thread thread) {
        threads.remove(thread);
    }

    public Thread getThreadByTitle(String title) {
        for (Thread thread : threads) {
            if (thread.getTitle().equals(title)) {
                return thread;
            }
        }
        return null;
    }
}
