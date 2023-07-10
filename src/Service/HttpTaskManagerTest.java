package Service;

import Server.HttpTaskManager;
import Server.KVServer;
import Server.KVTaskClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;


public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer kvServer;

    {
        try {
            kvServer = new KVServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() throws IOException, InterruptedException, URISyntaxException {

        kvServer.start();
        KVTaskClient client = new KVTaskClient(new URI("http://localhost:8078"));
        HttpTaskManager httpTaskManager = new HttpTaskManager(client);
    }
    @AfterEach
    public void tearDown() {
        kvServer.stop();
    }
}
