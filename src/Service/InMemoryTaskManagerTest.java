package Service;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void setUp() {
        TaskManager taskManager = new InMemoryTaskManager();
        initTasks();
    }

}
