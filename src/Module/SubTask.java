package Module;
import Service.TaskType;

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;


    public SubTask(Integer id, String title, String description, TaskStatus status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }
    public SubTask(String title, String description, TaskStatus status, int epicId) {
        super(title, description, status);
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

    @Override
    public String toString() {
        return getId() + ", "
                + TaskType.SUBTASK + ", "
                + getTitle() + ", " +
                getStatus() + ", " +
                getDescription()+ ", " +
                getEpicId();
    }
}
