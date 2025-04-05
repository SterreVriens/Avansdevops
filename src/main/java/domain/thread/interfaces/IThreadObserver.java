package domain.thread.interfaces;
import domain.thread.models.Thread;
import domain.thread.models.ThreadComponent;

public interface IThreadObserver {
    public void update(ThreadComponent component, Thread thread);
}
