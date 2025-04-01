package project.domain.common.models;

import java.util.ArrayList;

public class Project {
    private int id;
    private String projectName;
    private String description;
    private User productOwner;
    private ArrayList<User> teamMembers = new ArrayList<>();
    private Backlog backlog;
    // TODO: add sprints
    // private Sprint[] sprints;

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
    public void setId(int id) {
        this.id = id;
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
    public void setProductOwner(User productOwner) {
        this.productOwner = productOwner;
    }
    // Team Members
    public ArrayList<User> getTeamMembers() {
        return teamMembers;
    }
    public void setTeamMembers(ArrayList<User> teamMembers) {
        this.teamMembers = teamMembers;
    }
    public void addTeamMembers(User u) {
        this.teamMembers.add(u);
    }
    // Backlog
    public Backlog getBacklog() {
        return backlog;
    }
    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

}
