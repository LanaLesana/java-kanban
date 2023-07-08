package Module;
import Service.TaskType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected Integer id;
    protected String title;
    protected String description;
    protected TaskStatus status = TaskStatus.NEW;
    protected Duration duration;
    protected LocalDateTime startTime;
    private LocalDateTime endTime;


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
    public Task(Integer id, String title, String description, TaskStatus status, LocalDateTime startTime, LocalDateTime endTime, Duration duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = getEndTime();
        this.duration = duration;
    }
    public Task(Integer id, String title, String description, TaskStatus status, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = getEndTime();
        this.duration = duration;
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
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public Duration getDuration() {
        return duration;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public String getStartTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedStartTime = getStartTime().format(formatter);
        return formattedStartTime;
    }

    public LocalDateTime getEndTime() {
        LocalDateTime endTime = startTime.plus(this.duration);
        return endTime;
    }
    public String getEndTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedEndTime = getEndTime().format(formatter);
        return formattedEndTime;
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
                task.getDescription()) && Objects.equals(getStatus(), task.getStatus()) && Objects.equals(getStartTime(),
                task.getStartTime()) && Objects.equals(getDuration(), task.getDuration());
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
        if(startTime != null) {
            hash = hash + startTime.hashCode();
        }
        if(duration != null) {
            hash = hash + duration.hashCode();
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
                getDescription() + ", " +
                getStartTimeString() + ", " + getEndTimeString() + ", " + getDuration().toString();
    }

}
