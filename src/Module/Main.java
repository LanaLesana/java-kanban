package Module;
import Service.TaskManager;
import Service.InMemoryTaskManager;
import Service.InMemoryHistoryManager;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {



        TaskManager taskManager = new InMemoryTaskManager();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();


//        Epic epicWithNoSubtask = new Epic(1, "Вылечить кота", "Описание",new ArrayList<>());
//        Epic epicWithThreeSubtask = new Epic(2, "Покрасить стену", "Описание", new ArrayList<>());
//
//        SubTask catInVet = new SubTask(3, "Отнести кота в ветеринарку", "Описание", TaskStatus.NEW,1);
//        SubTask buyPaint = new SubTask(4, "Купить краску", "Описание", TaskStatus.NEW, 2);
//        SubTask paintWall = new SubTask(5, "Закрасить стену", "Описание", TaskStatus.NEW, 2);
//
//
//        taskManager.addEpic(epicWithNoSubtask);
//        taskManager.addEpic(epicWithThreeSubtask);
//        taskManager.addSubTask(catInVet);
//        taskManager.addSubTask(buyPaint);
//        taskManager.addSubTask(paintWall);
//
//        epicWithThreeSubtask.getSubtasks().add(catInVet);
//        epicWithThreeSubtask.getSubtasks().add(buyPaint);
//        epicWithThreeSubtask.getSubtasks().add(paintWall);
//
//
//
//        System.out.println("Проверка истории просмотров 1" + taskManager.getUserHistory().getHistory());
//
//        taskManager.getEpicById(1);
//        taskManager.getEpicById(2);
//        taskManager.getSubTaskById(3);
//        taskManager.getEpicById(1);
//
//        System.out.println("Проверка истории просмотров 2" + taskManager.getUserHistory().getHistory());
//
//        taskManager.removeSubTaskById(3);
//        taskManager.removeEpicById(2);
//
//        System.out.println("Проверка истории просмотров 3" + taskManager.getUserHistory().getHistory());



//        System.out.println("Первая проверка");
//        System.out.println(epicWithNoSubtask);
//        System.out.println(epicWithThreeSubtask);
//
//        System.out.println("Вторая проверка");
//
//        catInVet.setStatus(TaskStatus.DONE);
//        buyPaint.setStatus(TaskStatus.DONE);
//
//        taskManager.updateEpic(epicWithNoSubtask);
//        taskManager.updateEpic(epicWithThreeSubtask);
//
//        System.out.println(epicWithNoSubtask);
//        System.out.println(epicWithThreeSubtask);
//
//        System.out.println("Третья проверка");
//
//        taskManager.removeEpicById(epicWithNoSubtask.getId());
//        taskManager.removeSubTaskById(paintWall.getId());
//
//
//
//        System.out.println(taskManager.getEpicById(epicWithNoSubtask.getId()));
//        System.out.println(taskManager.getEpicById(epicWithThreeSubtask.getId()));
//
//        System.out.println("Проверка истории просмотров");
//        System.out.println(taskManager.getUserHistory());









    }
}
