package project.domain.sprint;

import java.util.Date;
import java.util.List;

import project.domain.backlogitem.models.BacklogItem;
import project.domain.common.models.User;
import project.domain.sprint.interfaces.ISprintState;
import project.domain.sprint.interfaces.ISprintStrategy;
import project.domain.sprint.states.CreatedSprintState;
import project.domain.sprint.states.raportedSprintState;
import project.domain.sprintraport.Raport;
import project.domain.sprintraport.RaportBuilder;

public class Sprint {
    private String name;
    private Date startDate;
    private Date endDate;
    private ISprintState state;
    private User scrumMaster;
    private ISprintStrategy sprintStrategy;
    private String reviewSummery;
    private RaportBuilder raportBuilder;
    private Raport raport;
    private List<BacklogItem> backlogItems;
 
    public Sprint(String name, Date startDate, Date endDate, User scrumMaster, ISprintStrategy sprintStrategy) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scrumMaster = scrumMaster;
        this.sprintStrategy = sprintStrategy;
        this.state = new CreatedSprintState(this);
    }

    //Getters and Setters

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
    public String getReviewSummery() {
        return reviewSummery;
    }
    public void setReviewSummery(String reviewSummery) {
        this.reviewSummery = reviewSummery;
    }

    // Change states 

    public void start() {
        this.state.start();
    }
    public void finish() {
        this.state.finish();
    }
    public void raport() {
        this.state.raport();
    }
    public void finalize() {
        this.state.finalize();
    }
    public void cancel() {
        this.state.cancel();
    }

    //raporting

    public void generateRaport() {
        // Bouw het rapport
        if(!(this.state instanceof raportedSprintState)){
            System.err.println("⛔️ Sprint is not finished yet.");
            return;
        }
        this.raport = new RaportBuilder()
            .setHeader("Sprint Report: " + this.name)
            .setContent("Sprint start date: " + this.startDate + "\nSprint end date: " + this.endDate)
            .setFooter("Scrum Master: " + (this.scrumMaster != null ? this.scrumMaster.getUsername() : "Unknown"))
            .build();
    
        // Bekijk het rapport
        this.raport.showRaport();
    }

    public void modifyRaport(String newHeader, String newContent, String newFooter) {
        if (this.raport == null) {
            System.err.println("No report has been generated yet. Please generate a report first.");
            return;
        }
    
        // Pas het rapport aan
        this.raport = new RaportBuilder()
            .setHeader(newHeader != null ? newHeader : this.raport.getHeader())
            .setContent(newContent != null ? newContent : this.raport.getContent())
            .setFooter(newFooter != null ? newFooter : this.raport.getFooter())
            .build();
    
        // Bekijk het aangepaste rapport
        this.raport.showRaport();
    }
    public void exportRaportAsPDF() {
        if (this.raport == null) {
            System.err.println("No report has been generated yet. Please generate a report first.");
            return;
        }
    
        // Exporteer het rapport als PDF
        this.raport.exportAsPDF();
    }
    public void exportRaportAsPNG() {
        if (this.raport == null) {
            System.err.println("No report has been generated yet. Please generate a report first.");
            return;
        }
    
        // Exporteer het rapport als PNG
        this.raport.exportAsPNG();
    }
}
