package  domain.sprint.states;

import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintState;

// State pattern

public class raportedSprintState implements ISprintState {
    private Sprint sprint;

    public raportedSprintState(Sprint sprint) {
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
    public void finalized() {
        this.sprint.getSprintStrategy().finalizeSprint(this.sprint);
    }

    @Override
    public void finish() {
        System.out.println("⛔️ Sprint is already finished.");
    }

    @Override
    public void raport() {
        System.out.println("⛔️ Sprint is already raported.");
    }
    
}
