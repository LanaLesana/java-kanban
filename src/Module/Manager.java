package Module;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int nextId;
    private HashMap<Integer, Task > tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();

    public void addTask (Task task) {
        task.setId(nextId);
        nextId++;
        tasks.put(task.getId(), task);
    }

    public void addEpic (Epic epic) {
        epic.setId(nextId);
        nextId++;
        epics.put(epic.getId(), epic);
    }

    public void addSubTask (SubTask subtask) {
        subtask.setId(nextId);
        nextId++;
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateTask (Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic (Epic epic) {
        boolean allSubtasksDone = true;
        boolean epicInProgress = false;
        for (SubTask subtask : epic.getSubtasks()) {
            if (subtask.getStatus() != "DONE") {
                allSubtasksDone = false;
                if (subtask.getStatus() == "NEW" || subtask.getStatus() == "IN_PROGRESS") {
                    epicInProgress = true;
                }
            }
        }
        if (allSubtasksDone) {
            epic.setStatus("DONE");
        } else if (epicInProgress) {
            epic.setStatus("IN_PROGRESS");
        } else {
            epic.setStatus("NEW");
        }
        epics.put(epic.getId(), epic);
    }


    public void updateSubTask (SubTask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public ArrayList<Task> getTaskList(HashMap<Integer, Task> tasks) {
        ArrayList<Task> taskList = new ArrayList<>(tasks.values());
        return taskList;
    }

    public ArrayList<Epic> getEpicList(HashMap<Integer, Epic> epics) {
        ArrayList<Epic> epicList = new ArrayList<>(epics.values());
        return epicList;
    }

    public ArrayList<SubTask> getSubTaskList(HashMap<Integer, SubTask> subtasks) {
        ArrayList<SubTask> subTaskList = new ArrayList<>(subtasks.values());
        return subTaskList;
    }

    public void clearTasks(HashMap<Integer, Task > tasks) {
        tasks.clear();
    }

    public void clearSubTasks(HashMap<Integer, SubTask> subtasks) {
        subtasks.clear();
    }

    public void clearEpics(HashMap<Integer, Epic> epics) {
        epics.clear();
    }

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        return task;
    }
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        return epic;
    }
    public SubTask getSubTaskById(int id) {
        SubTask subtask = subtasks.get(id);
        return subtask;
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }
    public void removeSubTaskById(int id) {
        subtasks.remove(id);
    }
    public void removeEpicById(int id) {
        epics.remove(id);
    }

    public ArrayList<SubTask> getSubtasksFromEpic (int epicId) {
        Epic epic = epics.get(epicId);
        return epic.getSubtasks();
    }






}
