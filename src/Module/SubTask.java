package Module;
import java.util.Objects;

public class SubTask extends Task {
    private int epicId;


    public SubTask(int id, String title, String description, TaskStatus status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        SubTask subTask = (SubTask) o;
        return getId() == subTask.getId() && getEpicId() == subTask.getEpicId()
                && getTitle().equals(subTask.getTitle())
                && getDescription().equals(subTask.getDescription())
                && getStatus().equals(subTask.getStatus());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + epicId;
        return result;
    }
}
