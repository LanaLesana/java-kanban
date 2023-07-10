package Service;
import Module.Epic;
import Module.Task;
import Module.SubTask;
import Module.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int nextId;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();



    private HashMap<Integer, SubTask> subtasks = new HashMap<>();

    private InMemoryHistoryManager userHistory = new InMemoryHistoryManager();
    protected final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));



    public HashMap<Integer, SubTask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }
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




    @Override
    public InMemoryHistoryManager getUserHistory() {
        return userHistory;
    }

    @Override
    public void save()  {

    }

    @Override
    public void addTask(Task task) {
        if (task.getId() != null) {
            if (!checkTaskConflicts(task, getExistingTasksAndSubtasks())) {
                tasks.put(task.getId(), task);
                prioritizedTasks.add(task);
            } else {
                System.out.println("Задача конфликтует с существующей задачей.");
            }
        } else {
            task.setId(nextId);
            nextId++;
            if (!checkTaskConflicts(task, getExistingTasksAndSubtasks())) {
                tasks.put(task.getId(), task);
                prioritizedTasks.add(task);
            } else {
                System.out.println("Задача конфликтует с существующей задачей.");
            }
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
        if (subtask.getId() != null) {
            if (!checkTaskConflicts(subtask, getExistingTasksAndSubtasks())) {
                subtasks.put(subtask.getId(), subtask);
                prioritizedTasks.add(subtask);
            } else {
                System.out.println("Задача конфликтует с существующей задачей.");
            }
        } else {
            subtask.setId(nextId);
            nextId++;
            if (!checkTaskConflicts(subtask, getExistingTasksAndSubtasks())) {
                subtasks.put(subtask.getId(), subtask);
                prioritizedTasks.add(subtask);
            } else {
                System.out.println("Задача конфликтует с существующей задачей.");
            }
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
        updateEpicTiming(epic);
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
    public ArrayList<Task> getExistingTasksAndSubtasks() {
        ArrayList<Task> list = getTaskList(tasks);
        list.addAll(getSubTaskList(subtasks));
        return list;
    }

    @Override
    public void clearTasks(HashMap<Integer, Task> tasks) {

        for (Integer id : tasks.keySet()) {
            userHistory.remove(id);
            prioritizedTasks.remove(getTaskByIdToRemove(id));
        }
        tasks.clear();

    }

    @Override
    public void clearSubTasks(HashMap<Integer, SubTask> subtasks) {
        for (Integer id : subtasks.keySet()) {
            userHistory.remove(id);
            prioritizedTasks.remove(getSubTaskByIdToRemove(id));
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
    public Task getTaskByIdToRemove(int id) {
        Task task = tasks.get(id);
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
    public Task getSubTaskByIdToRemove(int id) {
        SubTask subtask = subtasks.get(id);
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

    //public ArrayList<Task> getPrioritizedTasks() {
      //  Set<Task> prioritizedTasksSet = new TreeSet<>(Comparator.comparing(Task :: getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));
        //prioritizedTasksSet.addAll(getTaskList(tasks));
        //prioritizedTasksSet.addAll(getSubTaskList(subtasks));
        //return new ArrayList<>(prioritizedTasksSet);



    private boolean checkTaskConflicts(Task newTask, List<Task> existingTasks) {
        for (Task task : existingTasks) {
            LocalDateTime newTaskStartTime = newTask.getStartTime();
            LocalDateTime newTaskEndTime = newTask.getEndTime();
            LocalDateTime existingTaskStartTime = task.getStartTime();
            LocalDateTime existingTaskEndTime = task.getEndTime();

            if (newTaskStartTime.isBefore(existingTaskEndTime) && newTaskEndTime.isAfter(existingTaskStartTime)) {
                return true;
            }
        }
        return false;
    }


    public void updateEpicTiming(Epic epic) {
        epic.setStartTime(epic.getStartTime());
        epic.setEndTime(epic.getEndTime());
        epic.setDuration(epic.getDuration());

    }





}
