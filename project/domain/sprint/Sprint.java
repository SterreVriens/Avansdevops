package project.domain.sprint;

import java.util.Date;
import java.util.List;

import project.domain.sprint.interfaces.ISprintState;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.CreatedSprintState;
import project.domain.backlogitem.models.BacklogItem;
import project.domain.common.models.User;

public class Sprint {
    private String name;
    private Date startDate;
    private Date endDate;
    private ISprintState state;
    private User scrumMaster;
    private ISprintStrategy sprintStrategy;
    private List<BacklogItem> backlogItems;
 
    public Sprint(String name, Date startDate, Date endDate, User scrumMaster, ISprintStrategy sprintStrategy) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scrumMaster = scrumMaster;
        this.sprintStrategy = sprintStrategy;
        this.state = new CreatedSprintState(this);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(!(this.state instanceof CreatedSprintState)) {
            System.err.println("Cannot change name of a active.");
            return;
        }
        this.name = name;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        if(!(this.state instanceof CreatedSprintState)) {
            System.err.println("Cannot change the start date of a active.");
            return;
        }
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        if(!(this.state instanceof CreatedSprintState)) {
            System.err.println("Cannot change the end date of a active.");
            return;
        }
        this.endDate = endDate;
    }
    public ISprintState getState() {
        return state;
    }
    public void setState(ISprintState state) {
        this.state = state;
    }
    public User getScrumMaster() {
        return this.scrumMaster;
    }
    public ISprintStrategy getSprintStrategy() {
        return this.sprintStrategy;
    }
}
