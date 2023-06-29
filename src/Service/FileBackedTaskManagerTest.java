package Service;

import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Path;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @BeforeEach
    public void setUp() {
        File file = new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv");
        Path filePath = Path.of(file.toString());
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(filePath);
        initTasks();
    }
}