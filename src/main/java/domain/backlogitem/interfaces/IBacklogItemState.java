package  domain.backlogitem.interfaces;

import domain.backlogitem.models.BacklogItem;

public interface IBacklogItemState {
    public String toString();
    public void setState(BacklogItem backlogItem);
}
