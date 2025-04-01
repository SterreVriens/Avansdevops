package project.domain.sprint.states;

import project.domain.sprint.Sprint;
import project.domain.sprint.interfaces.ISprintState;

public class FinalizedSprintState implements ISprintState {
    private Sprint sprint;

    public FinalizedSprintState(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public void start() {
        System.out.println("⛔️ Sprint is already finalized.");
    }

    @Override
    public void finish() {
        System.out.println("⛔️ Sprint is already finalized.");
    }

    @Override
    public void report() {
        System.out.println("⛔️ Sprint is already finalized.");
    }

    @Override
    public void finalize() {
        System.out.println("⛔️ Sprint is already finalized.");
    }

    @Override
    public void cancel() {
        System.out.println("⛔️ Finished sprint cannot be canceled.");
    }
    
}
