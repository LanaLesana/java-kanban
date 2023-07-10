package Service;

import Server.HttpTaskManager;
import Server.KVTaskClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Managers {
    public static <T extends TaskManager> TaskManager getDefault() throws IOException, InterruptedException, URISyntaxException {
        KVTaskClient client = new KVTaskClient(new URI("http://localhost:8078"));
        HttpTaskManager httpTaskManager = new HttpTaskManager(client);
        return httpTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
