package Service;

public class Managers {
    public static <T extends TaskManager> TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
