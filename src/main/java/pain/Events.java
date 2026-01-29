package pain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {

    DateTimeFormatter intputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm:ss");

    private LocalDateTime from;
    private LocalDateTime to;

    public Events(String task, String from, String to) {
        super(task);
        this.from = LocalDateTime.parse(from, intputFormatter);
        this.to = LocalDateTime.parse(to, intputFormatter);
    }

    public Events(String task, boolean isDone, String from, String to) {
        super(task, isDone);
        this.from = LocalDateTime.parse(from, intputFormatter);
        this.to = LocalDateTime.parse(to, intputFormatter);
    }
    
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(outputFormatter) + " to: " + this.to.format(outputFormatter) + ")";
    }

    public String saveText() {
        return "Event | " + (this.getIsDone() ? "1" : "0") + " | " + this.getTaskName() + " | " + this.from.format(intputFormatter) + " | " + this.to.format(intputFormatter);
    }
}
