package  domain.sprint.states;

import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintState;

// State pattern

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
    public void finalized() {
        System.err.println("⛔️ Sprint is not finished yet.");
    }

    @Override
    public void finish() {
        sprint.setState(new FinishedSprintState(sprint));
        System.out.println("Sprint " + sprint.getName() + " finished.");
    }

    @Override
    public void raport() {
        System.err.println("⛔️ Sprint is not finished yet.");
    }
    
}
