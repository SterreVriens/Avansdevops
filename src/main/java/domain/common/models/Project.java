package  domain.common.models;

import java.util.ArrayList;

import  domain.common.enums.UserRole;
import  domain.scm.interfaces.ISCMAdapter;
import  domain.scm.models.Repository;
import domain.sprint.Sprint;

public class Project {
    private int id;
    private String projectName;
    private String description;
    private User productOwner;
    private ArrayList<User> teamMembers = new ArrayList<>();
    private Backlog backlog;
    private ArrayList<Repository> repositories = new ArrayList<>();
    private ArrayList<Sprint> sprints = new ArrayList<>();

    // Constructor
    public Project(int id, String projectName, String description, User productOwner, Backlog backlog) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        this.productOwner = productOwner;
        this.backlog = backlog;
    }

    // ID
    public int getId() {
        return id;
    }

    // Project Name
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Product Owner
    public User getProductOwner() {
        return productOwner;
    }

    // Team Members
    public ArrayList<User> getTeamMembers() {
        return teamMembers;
    }

    public void addTeamMembers(User u) {
        this.teamMembers.add(u);
    }

    public void setSprints(Sprint sprints) {
        this.sprints.add(sprints);
    }

    public ArrayList<Sprint> getSprints() {
        return sprints;
    }

    // Backlog
    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public User[] getTesters() {
        ArrayList<User> testers = new ArrayList<>();
        for (User user : teamMembers) {
            if (user.getRole() == UserRole.TESTER) {
                testers.add(user);
            }
        }
        return testers.toArray(new User[0]);
    }
    public void addRepository(String name, ISCMAdapter scmAdapter) {
        this.repositories.add(new Repository(name, scmAdapter));
    }
    public ArrayList<Repository> getRepositories() {
        return repositories;
    }
    public Repository getRepositoryByName(String name) {
        for (Repository repo : repositories) {
            if (repo.getName().equals(name)) {
                return repo;
            }
        }
        return null;
    }
}
