package Service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Module.Task;
import Module.SubTask;
import Module.Epic;
import Module.TaskStatus;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

abstract class HistoryManagerTest <T extends HistoryManager> {
    @Test
    void addTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Название", "Описание", TaskStatus.NEW);

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Задача не добавлена.");


    };
    @Test
    void getHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Название", "Описание", TaskStatus.NEW);

        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");

    };
    @Test
    void removeById() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task(1,"Название", "Описание", TaskStatus.NEW);
        historyManager.add(task);
        historyManager.remove(1);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не пустая.");

    };

}
