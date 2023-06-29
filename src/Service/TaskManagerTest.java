package Service;
import Module.Task;
import Module.SubTask;
import Module.Epic;
import Module.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;



public abstract class TaskManagerTest<T extends TaskManager> {
 protected T taskManager;
 protected Task task;
 protected Epic epic;
 protected SubTask subtask;

 protected void initTasks() {
  TaskManager taskManager = new InMemoryTaskManager() {
  };
  Task task1 = new Task(1, "Title1", "Description1", TaskStatus.NEW);
  Task task2 = new Task(2, "Title2", "Description2", TaskStatus.NEW);
  Epic epic1 = new Epic(3, "Title1", "Description1");
  Epic epic2 = new Epic(4, "Title2", "Description2");
  SubTask subTask1 = new SubTask("Название", "Описание", TaskStatus.NEW, 3);
  SubTask subTask2 = new SubTask(6, "Title2", "Description2", TaskStatus.NEW, 3);

  taskManager.addTask(task1);
  taskManager.addTask(task2);
  taskManager.addEpic(epic1);
  taskManager.addEpic(epic2);
  taskManager.addSubTask(subTask1);
  taskManager.addSubTask(subTask2);

 }
  @Test
  void getTasks () {
   HashMap<Integer, Task> taskMap = new HashMap<>();
   taskMap.put(1, task);

   assertEquals(taskMap, taskManager.getTasks(), "Задачи не совпадают.");

  }
  ;
  @Test
  void getSubTasks () {
   HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
   subTaskMap.put(1, subtask);

   assertEquals(subTaskMap, taskManager.getSubTasks(), "Подзадачи не совпадают.");

  }
  ;
  @Test
  void getEpics () {
   HashMap<Integer, Epic> epicMap = new HashMap<>();
   epicMap.put(1, epic);

   assertEquals(epicMap, taskManager.getEpics(), "Эпики не совпадают.");


  }
  ;
  @Test
  void addTask () {
   final int taskId = task.getId();
   final Task savedTask = taskManager.getTaskById(taskId);

   assertNotNull(savedTask, "Задача не найдена.");
   assertEquals(task, savedTask, "Задачи не совпадают.");

   final HashMap<Integer, Task> tasks = taskManager.getTasks();

   assertNotNull(tasks, "Задачи на возвращаются.");
   assertEquals(1, tasks.size(), "Неверное количество задач.");
   assertEquals(task, tasks.get(0), "Задачи не совпадают.");
  }
  ;
  @Test
  void addEpic () {
   final Epic savedEpic = taskManager.getEpicById(10);
   assertNotNull(savedEpic, "Задача не найдена.");
   assertNull(epic.getSubtasks(), "Найдены несуществующие подзадачи");
  }
  ;
  @Test
  void addSubTask () {
   final int subtaskId = subtask.getId();
   final SubTask savedSubTask = taskManager.getSubTaskById(subtaskId);

   assertNotNull(savedSubTask, "Задача не найдена.");
   assertEquals(subtask, savedSubTask, "Задачи не совпадают.");

   final HashMap<Integer, SubTask> subtasks = taskManager.getSubTasks();

   assertNotNull(subtasks, "Задачи на возвращаются.");
   assertEquals(1, subtasks.size(), "Неверное количество задач.");
   assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");


  }
  ;
  @Test
  void updateTask () {
   Task savedTask = taskManager.getTaskById(10);
   task.setDescription("Edited description");
   taskManager.updateTask(task);
   assertNotEquals(taskManager.getTaskById(10), savedTask, "Задача не обновлена.");

  }
  ;
  @Test
  void updateEpic () {
   Epic savedEpic = taskManager.getEpicById(10);
   epic.setDescription("Edited description");
   taskManager.updateEpic(epic);
   assertNotEquals(taskManager.getEpicById(10), savedEpic, "Эпик не обновлен");

  }
  ;
  @Test
  void updateSubTask () {
   SubTask savedSubTask = taskManager.getSubTaskById(10);
   subtask.setDescription("Edited description");
   taskManager.updateSubTask(subtask);
   assertNotEquals(taskManager.getSubTaskById(10), savedSubTask, "Эпик не обновлен");

  }
  ;
  @Test
  void getTaskList () {

   HashMap<Integer, Task> taskMap = taskManager.getTasks();
   ArrayList<Task> taskList = new ArrayList<>(taskMap.values());

   ArrayList<Task> savedTaskList = taskManager.getTaskList(taskMap);
   assertEquals(taskList, savedTaskList, "Список задач не совпадает.");


  }
  ;
  @Test
  void getEpicList () {
   HashMap<Integer, Epic> epicMap = taskManager.getEpics();
   ArrayList<Epic> taskList = new ArrayList<>(epicMap.values());

   ArrayList<Epic> savedEpicList = taskManager.getEpicList(epicMap);
   assertEquals(taskList, savedEpicList, "Список эпиков не совпадает.");

  }
  ;
  @Test
  void getSubTaskList () {
   HashMap<Integer, SubTask> subTaskMap = taskManager.getSubTasks();
   ArrayList<SubTask> subTaskList = new ArrayList<>(subTaskMap.values());

   ArrayList<SubTask> savedEpicList = taskManager.getSubTaskList(subTaskMap);
   assertEquals(subTaskList, savedEpicList, "Список подзадач не совпадает.");


  }
  ;
  @Test
  void clearTasks () {
   taskManager.clearTasks(taskManager.getTasks());
   assertNull(taskManager.getTasks(), "Список задач не очищен.");


  }
  ;
  @Test
  void clearSubTasks () {
   taskManager.clearSubTasks(taskManager.getSubTasks());
   assertNull(taskManager.getSubTasks(), "Список подзадач не очищен.");


  }
  ;
  @Test
  void clearEpics () {
   taskManager.clearEpics(taskManager.getEpics());
   assertNull(taskManager.getSubTasks(), "Список эпиков не очищен.");

  }
  ;
  @Test
  void getTaskById () {
   Task savedTask = taskManager.getTaskById(1);
   assertEquals(task, savedTask, "Задачи не совпадают");

  }
  ;
  @Test
  void getEpicById () {
   Epic savedEpic = taskManager.getEpicById(1);
   assertEquals(epic, savedEpic, "Эпики не совпадают");

  }
  ;
  @Test
  void getSubTaskById () {
   SubTask savedSubTask = taskManager.getSubTaskById(1);
   assertEquals(subtask, savedSubTask, "Подзадачи не совпадают");


  }
  ;
  @Test
  void removeTaskById () {
   taskManager.removeTaskById(1);
   assertNull(taskManager.getTasks(), "Задача не удалилась.");

  }
  ;
  @Test
  void removeSubTaskById () {
   taskManager.removeSubTaskById(1);
   assertNull(taskManager.getSubTasks(), "Подзадача не удалилась.");

  }
  ;
  @Test
  void removeEpicById () {
   taskManager.removeEpicById(1);
   assertNull(taskManager.getEpics(), "Эпик не удалился.");

  }
  ;
  @Test
  void getSubtasksFromEpic () {
   ArrayList<SubTask> subTasksList = epic.getSubtasks();
   ArrayList<SubTask> savedSubTasksList = taskManager.getSubtasksFromEpic(1);

   assertEquals(subTasksList, savedSubTasksList, "Список подзадач не совпадает.");
  }
  ;

  @Test
  void fileBackedManager () {
   taskManager.getTaskById(1);
   taskManager.getTaskById(2);
   taskManager.getSubTaskById(5);
   taskManager.getEpicById(4);

   taskManager.removeSubTaskById(5);

   HashMap<Integer, Task> tasksMap = taskManager.getTasks();
   HashMap<Integer, SubTask> subTasksMap = taskManager.getSubTasks();
   HashMap<Integer, Epic> epicsMap = taskManager.getEpics();
   InMemoryHistoryManager userHistory = taskManager.getUserHistory();

   taskManager.save();

   taskManager = TaskManager.loadFromFile(new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv"));
   HashMap<Integer, Task> savedTasksMap = taskManager.getTasks();
   HashMap<Integer, SubTask> savedSubTasksMap = taskManager.getSubTasks();
   HashMap<Integer, Epic> savedEpicsMap = taskManager.getEpics();
   InMemoryHistoryManager savedUserHistory = taskManager.getUserHistory();

   assertEquals(tasksMap, savedTasksMap, "Восстановленный из файла список задач не совпадает.");
   assertEquals(subTasksMap, savedSubTasksMap, "Восстановленный из файла список подзадач не совпадает.");
   assertEquals(epicsMap, savedEpicsMap, "Восстановленный из файла список эпиков не совпадает.");


  }


 }
