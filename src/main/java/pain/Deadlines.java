package pain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task{

    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm:ss");

    private LocalDateTime by;
    
    /**
     * Return a new Deadlines Object.
     * isDone will be false by default.
     * 
     * @param task Name of the Deadlines task.
     * @param by Deadlines time in "dd/mm/yy hh:mm:ss" format.
     * @return Deadlines object.
     */
    public Deadlines(String task, String by) {
        super(task);
        this.by = LocalDateTime.parse(by, inputFormatter);
    }

    /**
     * Return a new Deadlines Object.
     * 
     * @param task Name of the Deadlines task.
     * @param isDone Mark the Deadlines being done as true or false.
     * @param by Deadlines date in "dd/mm/yy hh:mm:ss" format.
     * @return Deadlines object.
     */
    public Deadlines(String task, boolean isDone, String by) {
        super(task, isDone);
        this.by = LocalDateTime.parse(by, inputFormatter);
    }

    /**
     * Return the String format of how this task is saved in the hard disk.
     * 
     * @return String format of how this task is saved in the hard disk.
     */
    public String saveText() {
        return "Deadline | " + (this.getIsDone() ? "1" : "0") + " | " + this.getTaskName() + " | " + this.by.format(inputFormatter);
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(outputFormatter) + ")";
    }
}
