package  domain.sprint.states;

import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintState;

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
    public void raport() {
        this.sprint.setState(new raportedSprintState(this.sprint));
        System.out.println("Sprint " + this.sprint.getName() + "is now ready to be raported.");
    }
    
}
