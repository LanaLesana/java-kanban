package Service;
import Module.Task;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> userHistory = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (userHistory.size() > 10) {
            userHistory.removeFirst();
        }
        userHistory.add(task);


    }
    @Override
    public List<Task> getHistory() {
        return userHistory;
    }
}
