package  domain.sprint.states;

import  domain.common.models.User;
import  domain.sprint.Sprint;
import  domain.sprint.interfaces.ISprintState;

public class CanceledSprintState implements ISprintState {
    private Sprint sprint;

    public CanceledSprintState(Sprint sprint) {
        this.sprint = sprint;

        String subject = "Sprint Canceled";
        String message = "Sprint " + sprint.getName() + " has been canceled.";
        this.sprint.sendNotification(sprint.getScrumMaster(), subject, message);
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Cannot start a canceled sprint.");
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException("Sprint is already canceled.");
    }

    @Override
    public void finalized() {
        throw new UnsupportedOperationException("Cannot finalize a canceled sprint.");
    }

    @Override
    public void finish() {
        throw new UnsupportedOperationException("Cannot finish a canceled sprint.");
    }

    @Override
    public void raport() {
        throw new UnsupportedOperationException("Cannot raport a canceled sprint.");
    }
    
}
