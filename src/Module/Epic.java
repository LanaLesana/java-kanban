package Module;
import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<SubTask> subtasks;

    public Epic(int id, String title, String description, ArrayList<SubTask> subtasks) {
        super(id, title, description, "NEW");
        this.subtasks = subtasks;
    }

    public ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }
}
