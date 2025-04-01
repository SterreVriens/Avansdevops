package project.domain.sprint.states;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintState;

public class FinishedSprintState implements ISprintState {
    private Sprint sprint;

    public FinishedSprintState(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public void start() {
        System.out.println("⛔️ Sprint is already finished.");
    }

    @Override
    public void cancel() {
        sprint.setState(new CanceledSprintState(this.sprint));
        System.out.println("Sprint " + this.sprint.getName() + " canceled.");
    }

    @Override
    public void finalize() {
        
        this.sprint.getSprintStrategy().finalizeSprint(this.sprint);
        System.out.println("Sprint " + this.sprint.getName() + " finalized.");
    }

    @Override
    public void finish() {
        System.out.println("⛔️ Sprint is already finished.");
    }

    @Override
    public void report() {
        this.sprint.setState(new ReportedSprintState(this.sprint));
        System.out.println("Sprint " + this.sprint.getName() + " reported.");
    }
    
}
