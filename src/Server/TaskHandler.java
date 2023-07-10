package Server;
import Service.InMemoryHistoryManager;
import Service.TaskManager;
import Module.Task;
import Module.SubTask;
import Module.Epic;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(),method);
        String response;
        int taskId = 0;
        switch(endpoint) {
            case GET_TASK:
                HashMap<Integer,Task> taskHashMap = taskManager.getTasks();
                response = gson.toJson(taskHashMap);
                writeResponse("Задачи получены", exchange, 200);
                break;
            case GET_SUBTASK:
                HashMap<Integer,SubTask> subtaskHashMap = taskManager.getSubTasks();
                response = gson.toJson(subtaskHashMap);
                writeResponse("Подзадачи получены", exchange, 200);
                break;
            case GET_EPIC:
                HashMap<Integer, Epic> epicHashMap = taskManager.getEpics();
                response = gson.toJson(epicHashMap);
                writeResponse("Эпики получены", exchange, 200);
                break;
            case GET_TASK_BY_ID:
                Task task = taskManager.getTaskById(taskId);
                response = gson.toJson(task);
                writeResponse("Задача получена", exchange, 200);
                break;
            case GET_SUBTASK_BY_ID:
                SubTask subtask = taskManager.getSubTaskById(taskId);
                response = gson.toJson(subtask);
                writeResponse("Задача получена", exchange, 200);
                break;
            case GET_EPIC_BY_ID:
                Epic epic = taskManager.getEpicById(taskId);
                response = gson.toJson(epic);
                writeResponse("Задача получена", exchange, 200);
                break;
            case GET_SUBTASKS_FROM_EPIC:
                ArrayList<SubTask> subTasksFromEpic = taskManager.getSubtasksFromEpic(taskId);
                response = gson.toJson(subTasksFromEpic);
                writeResponse("Подзадачи получены", exchange, 200);
                break;
            case GET_USER_HISTORY:
                InMemoryHistoryManager userHistory = taskManager.getUserHistory();
                response = gson.toJson(userHistory);
                writeResponse("История получена", exchange, 200);
                break;
            case POST_ADD_OR_UPDATE_TASK:
                Task task1 = gson.fromJson(new String(exchange.getRequestBody()
                        .readAllBytes(), StandardCharsets.UTF_8), Task.class);
                if(task1.getId() == null) {
                    taskManager.addTask(task1);
                }else {taskManager.updateTask(task1);}

                writeResponse("Задача добавлена/обновлена", exchange, 200);
                break;
            case POST_ADD_OR_UPDATE_SUBTASK:
                SubTask subtask1 = gson.fromJson(new String(exchange.getRequestBody()
                        .readAllBytes(), StandardCharsets.UTF_8), SubTask.class);
                if(subtask1.getId() == null) {
                    taskManager.addSubTask(subtask1);
                }else {taskManager.updateSubTask(subtask1);}
                writeResponse("Подзадача добавлена/обновлена", exchange, 200);
                break;
            case POST_ADD_OR_UPDATE_EPIC:
                Epic epic1 = gson.fromJson(new String(exchange.getRequestBody()
                        .readAllBytes(), StandardCharsets.UTF_8), Epic.class);
                if(epic1.getId() == null) {
                    taskManager.addEpic(epic1);
                }else {taskManager.updateEpic(epic1);}
                writeResponse("Эпик добавлен/обновлен", exchange, 200);
                break;
            case DELETE_TASKS:
                HashMap<Integer, Task> deletedTasks = gson.fromJson(new String(exchange.getRequestBody()
                        .readAllBytes(),StandardCharsets.UTF_8), HashMap.class);
                taskManager.clearTasks(deletedTasks);
                writeResponse("Все задачи удалены", exchange, 200);
                break;
            case DELETE_SUBTASKS:
                HashMap<Integer, SubTask> deletedSubTasks = gson.fromJson(new String(exchange.getRequestBody()
                        .readAllBytes(),StandardCharsets.UTF_8), HashMap.class);
                taskManager.clearSubTasks(deletedSubTasks);
                writeResponse("Все задачи удалены", exchange, 200);
                break;
            case DELETE_EPICS:
                HashMap<Integer, Epic> deletedEpics = gson.fromJson(new String(exchange.getRequestBody()
                        .readAllBytes(),StandardCharsets.UTF_8), HashMap.class);
                taskManager.clearEpics(deletedEpics);
                writeResponse("Все задачи удалены", exchange, 200);
                break;
            case DELETE_TASK_BY_ID:
                try {
                    taskManager.removeTaskById(taskId);
                    writeResponse("Задача удалена", exchange, 200);
                } catch (NullPointerException e) {
                    response = e.getMessage();
                    writeResponse("Не удалось удалить задачу", exchange, 500);
                    break;
                }
            case DELETE_SUBTASK_BY_ID:
                try {
                    taskManager.removeSubTaskById(taskId);
                    writeResponse("Задача удалена", exchange, 200);
                } catch (NullPointerException e) {
                    response = e.getMessage();
                    writeResponse("Не удалось удалить задачу", exchange, 500);
                    break;
                }
            case DELETE_EPIC_BY_ID:
                try {
                    taskManager.removeEpicById(taskId);
                    writeResponse("Задача удалена", exchange, 200);
                } catch (NullPointerException e) {
                    response = e.getMessage();
                    writeResponse("Не удалось удалить задачу", exchange, 500);
                    break;
                }
            case GET_PRIORITIZED_TASKS:

                    taskManager.getPrioritizedTasks();
                    writeResponse("Задачи получены", exchange, 200);
                    break;

            default:
                writeResponse("Произошла ошибка, проверьте данные запроса", exchange, 500);




        }



        if (!"GET".equals(method)) {
            writeResponse("Неправильный http-method!", exchange, 405);
            return;
        }
        try {
            taskManager.getTasks();
            writeResponse("Задачи получены", exchange, 200);
        } catch (Exception exception) {
            writeResponse("Произошла ошибка: " + exception.getMessage(), exchange, 500);
        }
    }

    private void writeResponse(String body, HttpExchange exchange, int code) throws IOException {
        byte[] responseBody = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, responseBody.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBody);
        } finally {
            exchange.close();
        }
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] path = requestPath.split("/");
        ArrayList<String> pathAsList = new ArrayList<>(Arrays.asList(path));
        if (requestMethod.equals("GET")) {
            if (pathAsList.contains("task") && pathAsList.contains("id=")) {
                return Endpoint.GET_TASK_BY_ID;
            } else if (pathAsList.contains("task") && !pathAsList.contains("id=")) {
                return Endpoint.GET_TASK;
            } else if (pathAsList.contains("subtask") && pathAsList.contains("id=")) {
                return Endpoint.GET_SUBTASK_BY_ID;
            } else if (pathAsList.contains("subtask") && !pathAsList.contains("id=")) {
                return Endpoint.GET_SUBTASK;
            } else if (pathAsList.contains("epic") && pathAsList.contains("id=")) {
                return Endpoint.GET_EPIC_BY_ID;
            } else if (pathAsList.contains("epic") && !pathAsList.contains("id=")) {
                return Endpoint.GET_EPIC;
            } else if (pathAsList.contains("history")) {
                return Endpoint.GET_USER_HISTORY;
            } else if (pathAsList.get(pathAsList.size() - 1).equals("tasks")) {
                return Endpoint.GET_PRIORITIZED_TASKS;
            } else if (pathAsList.contains("epic") && pathAsList.contains("subtask")) {
                return Endpoint.GET_SUBTASKS_FROM_EPIC;
            }
        } else if (requestMethod.equals(("POST"))) {
            if (pathAsList.get(2).equals("task")) {
                return Endpoint.POST_ADD_OR_UPDATE_TASK;
            } else if (pathAsList.get(2).equals("subtask")) {
                return Endpoint.POST_ADD_OR_UPDATE_SUBTASK;
            } else if (pathAsList.get(2).equals("epic")) {
                return Endpoint.POST_ADD_OR_UPDATE_EPIC;
            }
        } else if (requestMethod.equals("DELETE")) {
            if (pathAsList.contains("task") && pathAsList.contains("id=")) {
                return Endpoint.DELETE_TASK_BY_ID;
            } else if (pathAsList.contains("task") && !pathAsList.contains("id=")) {
                return Endpoint.DELETE_TASKS;
            } else if (pathAsList.contains("subtask") && pathAsList.contains("id=")) {
                return Endpoint.DELETE_SUBTASK_BY_ID;
            } else if (pathAsList.contains("subtask") && !pathAsList.contains("id=")) {
                return Endpoint.DELETE_SUBTASKS;
            } else if (pathAsList.contains("epic") && pathAsList.contains("id=")) {
                return Endpoint.DELETE_EPIC_BY_ID;
            } else if (pathAsList.contains("epic") && !pathAsList.contains("id=")) {
                return Endpoint.DELETE_EPICS;
            }
        }return Endpoint.NULL;
    }
}

