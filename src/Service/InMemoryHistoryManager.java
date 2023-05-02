package Service;
import Module.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> userHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (userHistory.size() > 9) {
            userHistory.remove(0);
            userHistory.add(task);
        }else {
            userHistory.add(task);
        }

    }
    @Override
    public List<Task> getHistory() {
        return userHistory;
    }
}
