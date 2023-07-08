package Server;


import Service.Managers;
import Service.TaskManager;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final int port;

    public HttpTaskServer(int port) {
        this.port = port;
    }

    public void startServer(TaskManager taskManager) throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(port), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        httpServer.start();
        taskManager = Managers.getDefault();

    }

}

