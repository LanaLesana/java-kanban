package Module;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {



        Manager manager = new Manager();
        //ArrayList<SubTask> subtasks = new ArrayList<>();

        Epic epicWithOneSubtask = new Epic(1, "Вылечить кота", "Описание",new ArrayList<>());
        Epic epicWithTwoSubtask = new Epic(2, "Покрасить стену", "Описание", new ArrayList<>());

        SubTask catInVet = new SubTask(3, "Отнести кота в ветеринарку", "Описание", "NEW",1);
        SubTask buyPaint = new SubTask(4, "Купить краску", "Описание", "NEW", 2);
        SubTask paintWall = new SubTask(5, "Закрасить стену", "Описание", "NEW", 2);


        manager.addEpic(epicWithOneSubtask);
        manager.addEpic(epicWithTwoSubtask);
        manager.addSubTask(catInVet);
        manager.addSubTask(buyPaint);
        manager.addSubTask(paintWall);

        epicWithOneSubtask.getSubtasks().add(catInVet);
        epicWithTwoSubtask.getSubtasks().add(buyPaint);
        epicWithTwoSubtask.getSubtasks().add(paintWall);

        System.out.println("Первая проверка");
        System.out.println(epicWithOneSubtask);
        System.out.println(epicWithTwoSubtask);

        System.out.println("Вторая проверка");

        catInVet.setStatus("DONE");
        buyPaint.setStatus("DONE");

        manager.updateEpic(epicWithOneSubtask);
        manager.updateEpic(epicWithTwoSubtask);

        System.out.println(epicWithOneSubtask);
        System.out.println(epicWithTwoSubtask);

        System.out.println("Третья проверка");

        manager.removeEpicById(epicWithOneSubtask.getId());
        manager.removeSubTaskById(paintWall.getId());



        System.out.println(manager.getEpicById(epicWithOneSubtask.getId()));
        System.out.println(manager.getEpicById(epicWithTwoSubtask.getId()));









    }
}
