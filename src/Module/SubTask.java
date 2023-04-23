package Module;

public class SubTask extends Task{
    private int epicId;


    public SubTask(int id, String title, String description, String status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
