package Module;

import Service.InMemoryTaskManager;
import Service.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    void addEpicWithNoSubtasks() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic(10, "Название", "Описание");
        taskManager.addEpic(epic);
        final Epic savedEpic = taskManager.getEpicById(10);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertNull(epic.getSubtasks(), "Найдены несуществующие подзадачи");
    }

    @Test
    void addEpicWithAllSubtasksNEW() {
        Epic epic = new Epic(10, "Название", "Описание");
        SubTask subTask1 = new SubTask( 11,"Название11", "Описание подзадачи 11", TaskStatus.NEW, 10);
        SubTask subTask2 = new SubTask( 12,"Название12", "Описание подзадачи 12", TaskStatus.NEW, 10);
        ArrayList<SubTask> subTasks = epic.getSubtasks();

        assertEquals(TaskStatus.NEW, subTasks.get(0).getStatus(), "Не соответствие статусов NEW");
        assertEquals(TaskStatus.NEW, subTasks.get(1).getStatus(), "Не соответствие статусов NEW");
    }
    @Test
    void addEpicWithAllSubtasksDONE() {
        Epic epic = new Epic(10, "Название", "Описание");
        SubTask subTask1 = new SubTask( 11,"Название11", "Описание подзадачи 11", TaskStatus.DONE, 10);
        SubTask subTask2 = new SubTask( 12,"Название12", "Описание подзадачи 12", TaskStatus.DONE, 10);
        ArrayList<SubTask> subTasks = epic.getSubtasks();

        assertEquals(TaskStatus.DONE, subTasks.get(0).getStatus(), "Не соответствие статусов DONE");
        assertEquals(TaskStatus.DONE, subTasks.get(1).getStatus(), "Не соответствие статусов DONE");
    }

    @Test
    void addEpicWithAllSubtasksINPROGRESS() {
        Epic epic = new Epic(10, "Название", "Описание");
        SubTask subTask1 = new SubTask( 11,"Название11", "Описание подзадачи 11", TaskStatus.IN_PROGRESS, 10);
        SubTask subTask2 = new SubTask( 12,"Название12", "Описание подзадачи 12", TaskStatus.IN_PROGRESS, 10);
        ArrayList<SubTask> subTasks = epic.getSubtasks();

        assertEquals(TaskStatus.IN_PROGRESS, subTasks.get(0).getStatus(), "Не соответствие статусов IN_PROGRESS");
        assertEquals(TaskStatus.IN_PROGRESS, subTasks.get(1).getStatus(), "Не соответствие статусов IN_PROGRESS");
    }
    @Test
    void addEpicWithAllSubtasksNEWandDone() {
        Epic epic = new Epic(10, "Название", "Описание");
        SubTask subTask1 = new SubTask( 11,"Название11", "Описание подзадачи 11", TaskStatus.NEW, 10);
        SubTask subTask2 = new SubTask( 12,"Название12", "Описание подзадачи 12", TaskStatus.DONE, 10);
        ArrayList<SubTask> subTasks = epic.getSubtasks();

        assertEquals(TaskStatus.NEW, subTasks.get(0).getStatus(), "Не соответствие статусов NEW");
        assertEquals(TaskStatus.DONE, subTasks.get(1).getStatus(), "Не соответствие статусов DONE");
    }




}