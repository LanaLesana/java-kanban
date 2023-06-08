package Service;
import java.io.*;

import Module.Epic;
import Module.Task;
import Module.SubTask;
import Module.TaskStatus;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private Path filePath;

    public FileBackedTasksManager(Path filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) {
        System.out.println("Спринт 6. Тестирование.");
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv"));

        fileBackedTasksManager.addTask(new Task(1, "Погулять", "Описание задачи 1", TaskStatus.NEW));
        fileBackedTasksManager.addTask(new Task(2,"Позавтракать", "Описание задачи 2", TaskStatus.NEW));

        fileBackedTasksManager.addEpic(new Epic( 4,"Похудеть", "Описание эпика 3"));

        fileBackedTasksManager.addSubTask(new SubTask( 5,"Купить форму", "Описание подзадачи 4", TaskStatus.NEW, 4));
        fileBackedTasksManager.addSubTask(new SubTask( 6,"Купить протеин", "Описание подзадачи 5", TaskStatus.NEW, 4));


        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getSubTaskById(5);
        fileBackedTasksManager.getEpicById(4);

        System.out.println(fileBackedTasksManager.getTasks());
        System.out.println(fileBackedTasksManager.getSubTasks());
        System.out.println(fileBackedTasksManager.getEpics());
        System.out.println(fileBackedTasksManager.getUserHistory());

        fileBackedTasksManager.removeSubTaskById(5);
        System.out.println(fileBackedTasksManager.getSubTasks());

        fileBackedTasksManager.save();

        System.out.println("Проверка восстановленной информации"); //Не работает. Не вытаскивает из файла строки. Не понимаю, почему.
        fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv"));
        System.out.println(fileBackedTasksManager.getTasks());
        System.out.println(fileBackedTasksManager.getSubTasks());
        System.out.println(fileBackedTasksManager.getEpics());
        System.out.println(fileBackedTasksManager.getUserHistory());


    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubTask(SubTask subtask) {
        super.addSubTask(subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subtask) {
        super.updateSubTask(subtask);
        save();
    }

    @Override
    public ArrayList<Task> getTaskList(HashMap<Integer, Task> tasks) {
        ArrayList<Task> taskList = super.getTaskList(tasks);
        save();
        return taskList;
    }

    @Override
    public ArrayList<Epic> getEpicList(HashMap<Integer, Epic> epics) {
        ArrayList<Epic> epicList = super.getEpicList(epics);
        save();
        return epicList;
    }

    @Override
    public ArrayList<SubTask> getSubTaskList(HashMap<Integer, SubTask> subtasks) {
        ArrayList<SubTask> subTaskList = super.getSubTaskList(subtasks);
        save();
        return subTaskList;
    }

    @Override
    public void clearTasks(HashMap<Integer, Task> tasks) {
        super.clearTasks(tasks);
        save();

    }

    @Override
    public void clearSubTasks(HashMap<Integer, SubTask> subtasks) {
        super.clearSubTasks(subtasks);
        save();
    }

    @Override
    public void clearEpics(HashMap<Integer, Epic> epics) {
        super.clearEpics(epics);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subtask = super.getSubTaskById(id);
        save();
        return subtask;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
        ArrayList<SubTask> list = super.getSubtasksFromEpic(epicId);
        save();
        return list;
    }


    private void save() {
        try {
        Files.deleteIfExists(Path.of("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        File dir = new File("/Users/olesia.b/IdeaProjects/java-kanban/src/Service/history.csv");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dir, true));
             BufferedReader bufferedReader = new BufferedReader(new FileReader(dir))) {

            if (bufferedReader.readLine() == null) {
                String header = ("id,type,name,status,description,epic" + "\n");
                bufferedWriter.write(header);
            }
            String lines = toString(this) + "\n" + historyToString(this.getUserHistory());
            bufferedWriter.write(lines);

        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка в работе с файлом.");
        }

    }

    private static String toString(TaskManager taskManager) {
        ArrayList<Task> allTasks = new ArrayList<>();

        allTasks.addAll(taskManager.getTasks().values());
        allTasks.addAll(taskManager.getSubTasks().values());
        allTasks.addAll(taskManager.getEpics().values());

        StringBuilder stringBuilder = new StringBuilder();

        for (Task task : allTasks) {
            stringBuilder.append(task.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    private static Task taskFromString(String value) {
        String[] split = value.split(",");
        Task task = new Task(Integer.parseInt(split[0]), split[2], split[4], TaskStatus.valueOf(split[3]));
        return task;
    }

    static String historyToString(HistoryManager manager) {
        List<Task> tasks = manager.getHistory();
        List<String> listOfStrings = new ArrayList<>();

        for (Task task : tasks) {
            listOfStrings.add(task.getId().toString());
        }
        String stringHistory = String.join(",", listOfStrings);
        return stringHistory;
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> historyList = new ArrayList<>();
        String[] historyArray = value.split(",");
        for (String str : historyArray) {
            historyList.add(Integer.parseInt(str));
        }
        return historyList;
    }

    private static FileBackedTasksManager loadFromFile(File file) {
        Path filePath = Path.of(file.toString());
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(filePath);

        List<String> listOfStrings = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file,StandardCharsets.UTF_8))) {
            String line =  bufferedReader.readLine();
            while (line != null) {
                listOfStrings.add(line);
                line = bufferedReader.readLine();
            }
            
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка чтения файла.");

        }



            for (int i = 1; i < listOfStrings.size() - 2; i++) {
                Task task = taskFromString(listOfStrings.get(i));

                if (task.getTitle().toUpperCase().equals(String.valueOf(TaskType.TASK))) {
                    fileBackedTasksManager.addTaskFromFile(task.getId(), task);

                } else if (task.getTitle().toUpperCase().equals(String.valueOf(TaskType.SUBTASK))) {
                    SubTask subtask = (SubTask) task;
                    fileBackedTasksManager.addSubTaskFromFile(task.getId(), subtask);

                } else if (task.getTitle().toUpperCase().equals(String.valueOf(TaskType.EPIC))) {
                    Epic epic = (Epic) task;
                    fileBackedTasksManager.addEpicFromFile(task.getId(), epic);
                }
            }

            if(!listOfStrings.isEmpty()) {
                String history = listOfStrings.get(listOfStrings.size() - 1);
                List<Integer> historyId = historyFromString(history);
                for (Integer id : historyId) {
                    fileBackedTasksManager.addHistory(id);

                }
            }
            return fileBackedTasksManager;
        }


    }

