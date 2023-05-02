package Module;
import Service.TaskManager;
import Service.InMemoryTaskManager;
import Service.InMemoryHistoryManager;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {



        TaskManager taskManager = new InMemoryTaskManager();
        //InMemoryHistoryManager historyManager = new InMemoryHistoryManager();


        Epic epicWithOneSubtask = new Epic(1, "Вылечить кота", "Описание",new ArrayList<>());
        Epic epicWithTwoSubtask = new Epic(2, "Покрасить стену", "Описание", new ArrayList<>());

        SubTask catInVet = new SubTask(3, "Отнести кота в ветеринарку", "Описание", TaskStatus.NEW,1);
        SubTask buyPaint = new SubTask(4, "Купить краску", "Описание", TaskStatus.NEW, 2);
        SubTask paintWall = new SubTask(5, "Закрасить стену", "Описание", TaskStatus.NEW, 2);


        taskManager.addEpic(epicWithOneSubtask);
        taskManager.addEpic(epicWithTwoSubtask);
        taskManager.addSubTask(catInVet);
        taskManager.addSubTask(buyPaint);
        taskManager.addSubTask(paintWall);

        epicWithOneSubtask.getSubtasks().add(catInVet);
        epicWithTwoSubtask.getSubtasks().add(buyPaint);
        epicWithTwoSubtask.getSubtasks().add(paintWall);

        System.out.println("Первая проверка");
        System.out.println(epicWithOneSubtask);
        System.out.println(epicWithTwoSubtask);

        System.out.println("Вторая проверка");

        catInVet.setStatus(TaskStatus.DONE);
        buyPaint.setStatus(TaskStatus.DONE);

        taskManager.updateEpic(epicWithOneSubtask);
        taskManager.updateEpic(epicWithTwoSubtask);

        System.out.println(epicWithOneSubtask);
        System.out.println(epicWithTwoSubtask);

        System.out.println("Третья проверка");

        taskManager.removeEpicById(epicWithOneSubtask.getId());
        taskManager.removeSubTaskById(paintWall.getId());



        System.out.println(taskManager.getEpicById(epicWithOneSubtask.getId()));
        System.out.println(taskManager.getEpicById(epicWithTwoSubtask.getId()));

        System.out.println("Проверка истории просмотров");
        System.out.println(taskManager.getUserHistory());









    }
}
