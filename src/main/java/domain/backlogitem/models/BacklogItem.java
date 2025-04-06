package  domain.backlogitem.models;

import java.util.ArrayList;

import  domain.backlogitem.interfaces.IBacklogItemObserver;
import  domain.backlogitem.interfaces.IBacklogItemState;
import  domain.backlogitem.models.states.ToDoState;
import domain.common.enums.UserRole;
import  domain.common.models.Backlog;
import  domain.common.models.User;
import domain.scm.models.Commit;
import  domain.sprint.Sprint;
import domain.thread.models.Thread;

public class BacklogItem {
    private Integer id;
    private String title;
    private String description;
    private IBacklogItemState currentState;
    private ArrayList<IBacklogItemObserver> observers = new ArrayList<>();
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private ArrayList<Commit> commits = new ArrayList<>();
    private User assignedTo;
    private Backlog backlog;
    private Sprint sprint; // Coupled when the BacklogItem is assigned to a Sprint

    public BacklogItem(Integer id, String title, String description, User assignedTo, Backlog backlog) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.backlog = backlog;
        this.currentState = new ToDoState();

        addObserver(new StateNotifier());
        backlog.addBacklogItem(this);
    }

    public void setStatus(User user, IBacklogItemState state) {
        if (this.assignedTo == null) {
            System.out.println("Warning: No developer is assigned to this backlog item.");
            return;
        }
        if (!this.assignedTo.equals(user)) {
            System.out.println("Warning: Only the assigned developer can change the status of this backlog item.");
            return;
        }

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
        notifyObservers();
        this.currentState = currentState;
    }

    public ArrayList<IBacklogItemObserver> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<IBacklogItemObserver> observers) {
        this.observers = observers;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void addCommit(Commit commit) {
        commits.add(commit);
    }
    public ArrayList<Commit> getCommits() {
        return commits;
    }

    public void setAssignedTo(User assignedTo, User assigner) {
        // Geef een waarschuwing als het backlog item al is toegewezen
        if (this.assignedTo != null) {
            System.out.println("Warning: This BacklogItem is already assigned to " + this.assignedTo.getUsername() + ".");
            return;
        }
        // Controleer of de toegewezene dezelfde is als degene die toewijst
        // Scrum Masters en Developers mogen zichzelf toewijzen
        if (assignedTo != assigner || 
            (assignedTo.getRole() != UserRole.SCRUMMASTER && assignedTo.getRole() != UserRole.DEVELOPER)) {
            System.out.println("Warning: You are not allowed to assign this backlog item");
            return;
        }
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
    public Backlog getBacklog() {
        return backlog;
    }
}
