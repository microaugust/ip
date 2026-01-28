public class Deadlines extends Task{
    private String by;
    
    public Deadlines(String task, String by) {
        super(task);
        this.by = by;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }

    public String saveText() {
        return "Deadline | " + (this.getDone() ? "1" : "0") + " | " + this.getTaskName() + " | " + this.by;
    }
}
