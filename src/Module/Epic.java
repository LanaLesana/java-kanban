package Module;
import Service.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;
import java.util.Comparator;


public class Epic extends Task {
    private ArrayList<SubTask> subtasks;
    private LocalDateTime endTime;


    public Epic(int id, String title, String description, ArrayList<SubTask> subtasks) {
        super(id, title, description, TaskStatus.NEW);
        this.subtasks = subtasks;
    }

    public Epic(Integer id, String title, String description) {
        super(title, description, TaskStatus.IN_PROGRESS);
    }
    public Epic(Task task) {
        super(task.getId(), task.getTitle(), task.getDescription(), task.getStatus());

    }
    @Override
    public LocalDateTime getStartTime() {
        Collections.sort(subtasks, (subTask1, subTask2) -> subTask1.getStartTime().compareTo(subTask2.getStartTime()));
        SubTask earliestSubTask = subtasks.get(0);
        return earliestSubTask.getStartTime();

    }
    @Override
    public LocalDateTime getEndTime() {
        Collections.sort(subtasks, (subTask2, subTask1) -> subTask2.getEndTime().compareTo(subTask1.getEndTime()));
        SubTask latestSubTask = subtasks.get(subtasks.size() - 1);
        return latestSubTask.getEndTime();

    }

    @Override
    public Duration getDuration() {
        Duration duration = Duration.between(startTime, endTime);
        return duration;
    }

    public ArrayList<SubTask> getSubtasks() {
        return subtasks;
    }
    public ArrayList<Integer> getSubtaskIds() {
        ArrayList<Integer> subtaskIds = new ArrayList<>();
        for (SubTask subtask : subtasks) {
            int subtaskId = subtask.getId();
            subtaskIds.add(subtaskId);
        }
        return subtaskIds;
    }

    @Override
    public String toString() {
        return getId() + ", "
                + TaskType.EPIC + ", "
                + getTitle() + ", " +
                getStatus()+ ", " +
                getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return getId() == epic.getId() &&
                Objects.equals(getTitle(), epic.getTitle()) &&
                Objects.equals(getDescription(), epic.getDescription()) &&
                Objects.equals(getStatus(), epic.getStatus()) &&
                Objects.equals(getSubtasks(), epic.getSubtasks());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (subtasks != null) {
            result = 31 * result + Objects.hashCode(subtasks);
        }
        return result;
    }
}
