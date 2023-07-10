package Service;
import Module.Epic;
import Module.Task;
import Module.SubTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    public HashMap<Integer, Task> getTasks();
    public HashMap<Integer, SubTask> getSubTasks();
    public HashMap<Integer, Epic> getEpics();
    public void addTask (Task task);
    public void addEpic (Epic epic);
    public void addSubTask (SubTask subtask);
    public void updateTask (Task task);
    public void updateEpic (Epic epic);
    public void updateSubTask (SubTask subtask);
    public ArrayList<Task> getTaskList(HashMap<Integer, Task> tasks);
    public ArrayList<Epic> getEpicList(HashMap<Integer, Epic> epics);
    public ArrayList<SubTask> getSubTaskList(HashMap<Integer, SubTask> subtasks);
    public void clearTasks(HashMap<Integer, Task> tasks);
    public void clearSubTasks(HashMap<Integer, SubTask> subtasks);
    public void clearEpics(HashMap<Integer, Epic> epics);
    public Task getTaskById(int id);
    public Epic getEpicById(int id);
    public SubTask getSubTaskById(int id);
    public void removeTaskById(int id);
    public void removeSubTaskById(int id);
    public void removeEpicById(int id);
    public ArrayList<SubTask> getSubtasksFromEpic (int epicId);
    public InMemoryHistoryManager getUserHistory();
    public Set<Task> getPrioritizedTasks();



    void save() throws IOException, InterruptedException;

    static <T extends TaskManager> T loadFromFile(File file) {
        return null;
    }
}
