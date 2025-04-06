package domain.thread.interfaces;
import domain.thread.models.Thread;
import domain.thread.models.ThreadComponent;

//Observer pattern

public interface IThreadObserver {
    public void update(ThreadComponent component, Thread thread);
}
