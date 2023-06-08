package Module;
import Service.TaskType;

import java.util.ArrayList;
import java.util.Objects;


public class Epic extends Task {
    private ArrayList<SubTask> subtasks;

    public Epic(int id, String title, String description, ArrayList<SubTask> subtasks) {
        super(id, title, description, TaskStatus.NEW);
        this.subtasks = subtasks;
    }

    public Epic(Integer id, String title, String description) {
        super(title, description, TaskStatus.IN_PROGRESS);
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

    //@Override
    //public String toString() {
      //  return "Epic{" +
          //      "id=" + getId() +
        //        ", title='" + getTitle() + '\'' +
              //  ", description='" + getDescription() + '\'' +
            //    ", status='" + getStatus() + '\'' +
                //", subtasks=" + subtasks +
                //'}';
    //}

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
