package  domain.backlogitem.models.states;

import  domain.backlogitem.interfaces.IBacklogItemState;
import domain.backlogitem.models.BacklogItem;

public class DoneState implements IBacklogItemState{
    @Override
    public String toString() {
        return "Done";
    }

    @Override
    public void setState(BacklogItem backlogItem) {
        if(backlogItem.areAllActivitiesDone()){
        backlogItem.setCurrentState(this);
        backlogItem.notifyObservers();
        }
        else{
            System.out.println("Cannot set state to Done. Not all activities are done.");
        }
    }
}
