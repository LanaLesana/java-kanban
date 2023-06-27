package Service;
import Module.Task;
import Module.SubTask;
import Module.Epic;
import Module.TaskStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;


abstract class TaskManagerTest<T extends TaskManager> {
    @Test
   void getTasks() {
     TaskManager taskManager = new InMemoryTaskManager();
     Task task = new Task("Название", "Описание", TaskStatus.NEW);
     taskManager.addTask(task);

     HashMap<Integer, Task> taskMap = new HashMap<>();
     taskMap.put(1, task);

     assertEquals(taskMap, taskManager.getTasks(), "Задачи не совпадают.");

    };
    @Test
    void getSubTasks(){
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask = new SubTask("Название", "Описание", TaskStatus.NEW, 01);
     taskManager.addSubTask(subTask);

     HashMap<Integer, SubTask> subTaskMap = new HashMap<>();
     subTaskMap.put(1, subTask);

     assertEquals(subTaskMap, taskManager.getSubTasks(), "Подзадачи не совпадают.");

    };
    @Test
    void getEpics() {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic1 = new Epic(1, "Title1", "Description1");
     taskManager.addEpic(epic1);

     HashMap<Integer, Epic> epicMap = new HashMap<>();
     epicMap.put(1, epic1);

     assertEquals(epicMap, taskManager.getEpics(), "Эпики не совпадают.");



    };
    @Test
    void addTask () {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Название", "Описание", TaskStatus.NEW);
        taskManager.addTask(task);
        final int taskId = task.getId();
        final Task savedTask = taskManager.getTaskById(taskId);

     assertNotNull(savedTask, "Задача не найдена.");
     assertEquals(task, savedTask, "Задачи не совпадают.");

     final HashMap<Integer,Task> tasks = taskManager.getTasks();

     assertNotNull(tasks, "Задачи на возвращаются.");
     assertEquals(1, tasks.size(), "Неверное количество задач.");
     assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    };
    @Test
    void addEpic () {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic = new Epic(10, "Название", "Описание");
     taskManager.addEpic(epic);
     final Epic savedEpic = taskManager.getEpicById(10);
     assertNotNull(savedEpic, "Задача не найдена.");
     assertNull(epic.getSubtasks(), "Найдены несуществующие подзадачи");
    };
    @Test
    void addSubTask () {
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask = new SubTask("Название", "Описание", TaskStatus.NEW, 01);
     taskManager.addSubTask(subTask);
     final int subtaskId = subTask.getId();
     final SubTask savedSubTask = taskManager.getSubTaskById(subtaskId);

     assertNotNull(savedSubTask, "Задача не найдена.");
     assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

     final HashMap<Integer, SubTask> subtasks = taskManager.getSubTasks();

     assertNotNull(subtasks, "Задачи на возвращаются.");
     assertEquals(1, subtasks.size(), "Неверное количество задач.");
     assertEquals(subTask, subtasks.get(0), "Задачи не совпадают.");


    };
    @Test
    void updateTask () {
     TaskManager taskManager = new InMemoryTaskManager();
     Task task = new Task(10,"Title", "Description", TaskStatus.NEW);
     taskManager.addTask(task);
     Task savedTask = taskManager.getTaskById(10);
     task.setDescription("Edited description");
     taskManager.updateTask(task);
     assertNotEquals(taskManager.getTaskById(10), savedTask, "Задача не обновлена.");

    };
    @Test
    void updateEpic () {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic = new Epic(10, "Title", "Description");
     taskManager.addEpic(epic);
     Epic savedEpic = taskManager.getEpicById(10);
     epic.setDescription("Edited description");
     taskManager.updateEpic(epic);
     assertNotEquals(taskManager.getEpicById(10), savedEpic, "Эпик не обновлен");

    };
    @Test
    void updateSubTask () {
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask = new SubTask(10, "Title", "Description", TaskStatus.NEW, 11);
     taskManager.addSubTask(subTask);
     SubTask savedSubTask = taskManager.getSubTaskById(10);
     subTask.setDescription("Edited description");
     taskManager.updateSubTask(subTask);
     assertNotEquals(taskManager.getSubTaskById(10), savedSubTask, "Эпик не обновлен");

    };
    @Test
    void getTaskList() {

     TaskManager taskManager = new InMemoryTaskManager();
     Task task1 = new Task(1,"Title1", "Description1", TaskStatus.NEW);
     Task task2 = new Task(2,"Title2", "Description2", TaskStatus.NEW);
     taskManager.addTask(task1);
     taskManager.addTask(task2);

     HashMap<Integer, Task> taskMap = taskManager.getTasks();
     ArrayList<Task> taskList = new ArrayList<>(taskMap.values());

     ArrayList<Task> savedTaskList = taskManager.getTaskList(taskMap);
     assertEquals(taskList, savedTaskList, "Список задач не совпадает.");



    };
    @Test
    void getEpicList() {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic1 = new Epic(1, "Title1", "Description1");
     Epic epic2 = new Epic(2, "Title2", "Description2");

     taskManager.addEpic(epic1);
     taskManager.addEpic(epic2);

     HashMap<Integer, Epic> epicMap = taskManager.getEpics();
     ArrayList<Epic> taskList = new ArrayList<>(epicMap.values());

     ArrayList<Epic> savedEpicList = taskManager.getEpicList(epicMap);
     assertEquals(taskList, savedEpicList, "Список эпиков не совпадает.");

    };
    @Test
    void getSubTaskList() {
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask1 = new SubTask(1, "Title1", "Description1", TaskStatus.NEW, 11);
     SubTask subTask2 = new SubTask(2, "Title2", "Description2", TaskStatus.NEW, 11);

     taskManager.addSubTask(subTask1);
     taskManager.addSubTask(subTask2);

     HashMap<Integer, SubTask> subTaskMap = taskManager.getSubTasks();
     ArrayList<SubTask> subTaskList = new ArrayList<>(subTaskMap.values());

     ArrayList<SubTask> savedEpicList = taskManager.getSubTaskList(subTaskMap);
     assertEquals(subTaskList, savedEpicList, "Список подзадач не совпадает.");


    };
    @Test
    void clearTasks() {
     TaskManager taskManager = new InMemoryTaskManager();
     Task task1 = new Task(1,"Title1", "Description1", TaskStatus.NEW);
     Task task2 = new Task(2,"Title2", "Description2", TaskStatus.NEW);
     taskManager.addTask(task1);
     taskManager.addTask(task2);

     taskManager.clearTasks(taskManager.getTasks());
     assertNull(taskManager.getTasks(), "Список задач не очищен.");


    };
    @Test
    void clearSubTasks() {
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask1 = new SubTask(1, "Title1", "Description1", TaskStatus.NEW, 11);
     SubTask subTask2 = new SubTask(2, "Title2", "Description2", TaskStatus.NEW, 11);

     taskManager.addSubTask(subTask1);
     taskManager.addSubTask(subTask2);

     taskManager.clearSubTasks(taskManager.getSubTasks());
     assertNull(taskManager.getSubTasks(), "Список подзадач не очищен.");


    };
    @Test
    void clearEpics() {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic1 = new Epic(1, "Title1", "Description1");
     Epic epic2 = new Epic(2, "Title2", "Description2");

     taskManager.addEpic(epic1);
     taskManager.addEpic(epic2);

     taskManager.clearEpics(taskManager.getEpics());
     assertNull(taskManager.getSubTasks(), "Список эпиков не очищен.");

    };
    @Test
    void getTaskById() {
     TaskManager taskManager = new InMemoryTaskManager();
     Task task1 = new Task(1,"Title1", "Description1", TaskStatus.NEW);
     taskManager.addTask(task1);
     Task savedTask = taskManager.getTaskById(1);
     assertEquals(task1, savedTask, "Задачи не совпадают");

    };
    @Test
    void getEpicById() {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic1 = new Epic(1, "Title1", "Description1");
     taskManager.addEpic(epic1);
     Epic savedEpic = taskManager.getEpicById(1);
     assertEquals(epic1, savedEpic, "Эпики не совпадают");

    };
    @Test
    void getSubTaskById() {
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask1 = new SubTask(1, "Title1", "Description1", TaskStatus.NEW, 11);
     taskManager.addSubTask(subTask1);
     SubTask savedSubTask = taskManager.getSubTaskById(1);
     assertEquals(subTask1, savedSubTask, "Подзадачи не совпадают");


    };
    @Test
   void removeTaskById() {
     TaskManager taskManager = new InMemoryTaskManager();
     Task task1 = new Task(1,"Title1", "Description1", TaskStatus.NEW);
     taskManager.addTask(task1);
     taskManager.removeTaskById(1);
     assertNull(taskManager.getTasks(), "Задача не удалилась.");

    };
    @Test
   void removeSubTaskById() {
     TaskManager taskManager = new InMemoryTaskManager();
     SubTask subTask1 = new SubTask(1, "Title1", "Description1", TaskStatus.NEW, 11);
     taskManager.addSubTask(subTask1);
     taskManager.removeSubTaskById(1);
     assertNull(taskManager.getSubTasks(), "Подзадача не удалилась.");

   };
    @Test
    void removeEpicById() {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic1 = new Epic(1, "Title1", "Description1");
     taskManager.addEpic(epic1);
     taskManager.removeEpicById(1);
     assertNull(taskManager.getEpics(), "Эпик не удалился.");

    };
    @Test
    void getSubtasksFromEpic () {
     TaskManager taskManager = new InMemoryTaskManager();
     Epic epic1 = new Epic(1, "Title1", "Description1");
     SubTask subTask1 = new SubTask(2, "Title1", "Description1", TaskStatus.NEW, 1);
     SubTask subTask2 = new SubTask(3, "Title2", "Description2", TaskStatus.NEW, 1);

     taskManager.addEpic(epic1);
     taskManager.addSubTask(subTask1);
     taskManager.addSubTask(subTask2);

     ArrayList<SubTask> subTasksList = epic1.getSubtasks();
     ArrayList<SubTask> savedSubTasksList = taskManager.getSubtasksFromEpic(1);

     assertEquals(subTasksList, savedSubTasksList, "Список подзадач не совпадает.");
    };

 @Test
 void fileBackedManager() {
  File file = new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv");
  Path filePath = Path.of(file.toString());
  FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(filePath);

  fileBackedTasksManager.addTask(new Task(1, "Погулять", "Описание задачи 1", TaskStatus.NEW));
  fileBackedTasksManager.addTask(new Task(2, "Позавтракать", "Описание задачи 2", TaskStatus.NEW));

  fileBackedTasksManager.addEpic(new Epic(4, "Похудеть", "Описание эпика 3"));

  fileBackedTasksManager.addSubTask(new SubTask(5, "Купить форму", "Описание подзадачи 4", TaskStatus.NEW, 4));
  fileBackedTasksManager.addSubTask(new SubTask(6, "Купить протеин", "Описание подзадачи 5", TaskStatus.NEW, 4));


  fileBackedTasksManager.getTaskById(1);
  fileBackedTasksManager.getTaskById(2);
  fileBackedTasksManager.getSubTaskById(5);
  fileBackedTasksManager.getEpicById(4);

  fileBackedTasksManager.removeSubTaskById(5);

  HashMap<Integer,Task> tasksMap = fileBackedTasksManager.getTasks();
  HashMap<Integer,SubTask> subTasksMap = fileBackedTasksManager.getSubTasks();
  HashMap<Integer,Epic> epicsMap = fileBackedTasksManager.getEpics();
  InMemoryHistoryManager userHistory = fileBackedTasksManager.getUserHistory();

  fileBackedTasksManager.save();

  fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv"));
  HashMap<Integer,Task> savedTasksMap = fileBackedTasksManager.getTasks();
  HashMap<Integer,SubTask> savedSubTasksMap = fileBackedTasksManager.getSubTasks();
  HashMap<Integer,Epic> savedEpicsMap = fileBackedTasksManager.getEpics();
  InMemoryHistoryManager savedUserHistory = fileBackedTasksManager.getUserHistory();

  assertEquals(tasksMap, savedTasksMap, "Восстановленный из файла список задач не совпадает.");
  assertEquals(subTasksMap, savedSubTasksMap, "Восстановленный из файла список подзадач не совпадает.");
  assertEquals(epicsMap, savedEpicsMap, "Восстановленный из файла список эпиков не совпадает.");


 }



}
