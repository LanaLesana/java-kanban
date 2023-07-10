package Server;
import Module.Epic;
import Module.Task;
import Module.SubTask;


import Service.FileBackedTasksManager;
import Service.InMemoryHistoryManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson = new Gson();
    private final KVTaskClient client;
    private final URI url = URI.create("http://localhost:8078");
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();



    public HttpTaskManager(KVTaskClient kvTaskClient) {
        super();
        this.client = kvTaskClient;

    }
    ;
    @Override
    public void save() {

        try {
            String jsonTasks = gson.toJson(new ArrayList<>(getTaskList(getTasks())));
            client.put("tasks", jsonTasks);
            String jsonSubtasks = gson.toJson(new ArrayList<>(getTaskList(getSubTasks())));
            client.put("subtasks", jsonSubtasks);
            String jsonEpics = gson.toJson(new ArrayList<>(getTaskList(getEpics())));
            client.put("epics", jsonEpics);
            String jsonHistory = gson.toJson(inMemoryHistoryManager.getHistory().stream().map(Task::getId).collect(Collectors.toList()));
            client.put("history", jsonHistory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
