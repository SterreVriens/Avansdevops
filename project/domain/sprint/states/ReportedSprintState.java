package project.domain.sprint.states;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintState;

public class ReportedSprintState implements ISprintState {
    private Sprint sprint;

    public ReportedSprintState(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public void start() {
        System.out.println("⛔️ Sprint is already started.");
    }

    @Override
    public void cancel() {
        this.sprint.setState(new CanceledSprintState(this.sprint));
        System.out.println("Sprint " + this.sprint.getName() + " canceled.");
    }

    @Override
    public void finalize() {
        this.sprint.setState(new FinalizedSprintState(this.sprint));
        this.sprint.getSprintStrategy().finalizeSprint(this.sprint);
        System.out.println("Sprint " + this.sprint.getName() + " finalized.");
    }

    @Override
    public void finish() {
        System.out.println("⛔️ Sprint is already finished.");
    }

    @Override
    public void report() {
        System.out.println("⛔️ Sprint is already reported.");
    }
    
}
