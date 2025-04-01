package project.domain.sprint.states;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintState;

public class StartedSprintState implements ISprintState {
    private Sprint sprint;

    public StartedSprintState(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public void start() {
        System.err.println("⛔️ Sprint is already started.");
    }

    @Override
    public void cancel() {
        sprint.setState(new CanceledSprintState(sprint));
        System.out.println("Sprint " + sprint.getName() + " canceled.");
    }

    @Override
    public void finalize() {
        System.err.println("⛔️ Sprint is not finished yet.");
    }

    @Override
    public void finish() {
        sprint.setState(new FinishedSprintState(sprint));
        System.out.println("Sprint " + sprint.getName() + " finished.");
    }

    @Override
    public void report() {
        System.err.println("⛔️ Sprint is not finished yet.");
    }
    
}
