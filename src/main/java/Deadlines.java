import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task{

    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm:ss");

    private LocalDateTime by;
    
    public Deadlines(String task, String by) {
        super(task);
        this.by = LocalDateTime.parse(by, inputFormatter);
    }

    public Deadlines(String task, boolean isDone, String by) {
        super(task, isDone);
        this.by = LocalDateTime.parse(by, inputFormatter);
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(outputFormatter) + ")";
    }

    public String saveText() {
        return "Deadline | " + (this.getIsDone() ? "1" : "0") + " | " + this.getTaskName() + " | " + this.by.format(inputFormatter);
    }
}
