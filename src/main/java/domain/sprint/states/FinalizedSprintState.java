package  domain.sprint.states;

import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintState;

// State pattern

public class FinalizedSprintState implements ISprintState {
    private Sprint sprint;

    public FinalizedSprintState(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public void start() {
        System.out.println("⛔️ Sprint cannot start. Sprint is already finalized.");
    }

    @Override
    public void finish() {
        System.out.println("⛔️Sprint cannot finish. Sprint is already finalized.");
    }

    @Override
    public void raport() {
        System.out.println("⛔️Sprint cannot raport. Sprint is already finalized.");
    }

    @Override
    public void finalized() {
        System.out.println("⛔️Sprint cannot finalize Sprint is already finalized.");
    }

    @Override
    public void cancel() {
        System.out.println("⛔️ Finished sprint cannot be canceled.");
    }
    
}
