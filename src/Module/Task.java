package Module;
import Service.TaskType;

import java.util.Objects;

public class Task {
    protected Integer id;
    protected String title;
    protected String description;
    protected TaskStatus status = TaskStatus.NEW;

    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
    public Task(Integer id, String title, String description, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getId() == task.getId() && Objects.equals(getTitle(), task.getTitle()) && Objects.equals(getDescription(),
                task.getDescription()) && Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (title != null) {
            hash = hash + title.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        if (status != null) {
            hash = hash + status.hashCode();
        }
        hash = hash * 31 + id;
        return hash;
    }

    @Override
    public String toString() {
        return getId() + ", "
                + TaskType.TASK + ", "
                + getTitle() + ", " +
                getStatus() + ", " +
                getDescription();
    }

}
