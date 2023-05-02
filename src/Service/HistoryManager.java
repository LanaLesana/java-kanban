package Service;
import java.util.List;
import Module.Task;


public interface HistoryManager {
    public void add(Task task);
    public List<Task> getHistory();
}
