package Service;
import Module.Epic;
import Module.Task;
import Module.SubTask;
import Module.TaskStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int nextId;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasks() {
        return subtasks;
    }

    private InMemoryHistoryManager userHistory = new InMemoryHistoryManager();


    @Override
    public InMemoryHistoryManager getUserHistory() {
        return userHistory;
    }

    @Override
    public void addTask(Task task) {
        if (task.getId() != null) {
            tasks.put(task.getId(), task);

        } else {
            task.setId(nextId);
            nextId++;
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic.getId() != null ) {
            epics.put(epic.getId(), epic);
        }else {
            epic.setId(nextId);
            nextId++;
            epics.put(epic.getId(), epic);
        }

    }

    @Override
    public void addSubTask(SubTask subtask) {
        if (subtask.getId() != null ) {
            subtasks.put(subtask.getId(), subtask);
        }else {
            subtask.setId(nextId);
            nextId++;
            subtasks.put(subtask.getId(), subtask);
        }

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            updateEpic(epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) {
            return;
        }
        boolean allSubtasksDone = true;
        boolean epicInProgress = false;
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            epics.put(epic.getId(), epic);
            return;
        }
        for (SubTask subtask : epic.getSubtasks()) {
            if (subtask.getStatus() != TaskStatus.DONE) {
                allSubtasksDone = false;
                if (subtask.getStatus() == TaskStatus.NEW || subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                    epicInProgress = true;
                }
            }
        }
        if (allSubtasksDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (epicInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubTask(SubTask subtask) {

        subtasks.put(subtask.getId(), subtask);
        updateEpic(epics.get(subtask.getEpicId()));
    }

    @Override
    public ArrayList<Task> getTaskList(HashMap<Integer, Task> tasks) {
        ArrayList<Task> taskList = new ArrayList<>(tasks.values());
        return taskList;
    }

    @Override
    public ArrayList<Epic> getEpicList(HashMap<Integer, Epic> epics) {
        ArrayList<Epic> epicList = new ArrayList<>(epics.values());
        return epicList;
    }

    @Override
    public ArrayList<SubTask> getSubTaskList(HashMap<Integer, SubTask> subtasks) {
        ArrayList<SubTask> subTaskList = new ArrayList<>(subtasks.values());
        return subTaskList;
    }

    @Override
    public void clearTasks(HashMap<Integer, Task> tasks) {

        for (Integer id : tasks.keySet()) {
            userHistory.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void clearSubTasks(HashMap<Integer, SubTask> subtasks) {
        for (Integer id : subtasks.keySet()) {
            userHistory.remove(id);
        }
        subtasks.clear();
        for (Epic epic : epics.values())
            updateEpic(epic);
    }

    @Override
    public void clearEpics(HashMap<Integer, Epic> epics) {

        for (Integer id : epics.keySet()) {
            userHistory.remove(id);
        }
        epics.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        userHistory.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        userHistory.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subtask = subtasks.get(id);
        userHistory.add(subtask);
        return subtask;
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        userHistory.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        SubTask subtask = getSubTaskById(id);
        subtasks.remove(id);
        userHistory.remove(id);
        updateEpic(epics.get(subtask.getEpicId()));
    }

    @Override
    public void removeEpicById(int id) {
        epics.remove(id);
        userHistory.remove(id);
    }

    @Override
    public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
        Epic epic = epics.get(epicId);
        return epic.getSubtasks();
    }

    public void addTaskFromFile(Integer id, Task task) {
        tasks.put(id, task);
    }

    public void addSubTaskFromFile(Integer id, SubTask subtask) {
        subtasks.put(id, subtask);
    }

    public void addEpicFromFile(Integer id, Epic epic) {
        epics.put(id, epic);
    }

    public void addHistory(int id) {
        if (epics.containsKey(id)) {
            userHistory.add(epics.get(id));
        } else if (subtasks.containsKey(id)) {
            userHistory.add(subtasks.get(id));
        } else if (tasks.containsKey(id)) {
            userHistory.add(tasks.get(id));
        }

    }
}
