package project.domain.sprint.states;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintState;

public class CreatedSprintState implements ISprintState
{
    private Sprint sprint;

    public CreatedSprintState(Sprint sprint) {
        this.sprint = sprint;
    }
    
    @Override
    public void start() {
        // Logic to start the sprint
        this.sprint.setState(new StartedSprintState(this.sprint));
        System.out.println("Sprint "+ sprint.getName()+" started.");
    }

    @Override
    public void finish() {
        // Logic to finish the sprint
        this.sprint.setState(new FinishedSprintState(this.sprint));
        System.out.println("Sprint "+ sprint.getName()+" finished.");
        System.out.println("Sprint finished.");
    }

    @Override
    public void raport() {
        // Logic to raport the sprint
        System.out.println("⛔️ Sprint has not finished yet.");
    }

    @Override
    public void finalize() {
        // Logic to finalize the sprint
        System.out.println("⛔️ Sprint has not finished yet.");
    }

    @Override
    public void cancel() {
        // Logic to cancel the sprint
        this.sprint.setState(new CanceledSprintState(this.sprint));
        System.out.println("Sprint "+ sprint.getName()+" canceled.");
    }	
    
}
