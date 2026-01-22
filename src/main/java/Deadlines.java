public class Deadlines extends Task{
    private String by;
    
    public Deadlines(String task, String by) {
        super(task);
        this.by = by;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
