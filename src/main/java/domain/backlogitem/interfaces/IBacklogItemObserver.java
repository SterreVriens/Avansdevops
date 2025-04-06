package  domain.backlogitem.interfaces;

import  domain.backlogitem.models.BacklogItem;

//Observer pattern

public interface IBacklogItemObserver {
    public void Update(BacklogItem item);
}
