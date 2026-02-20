package pain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a simple to-do task that has a description and a completion status.
 * This type of task contains a date/time for the start and end date/time.
 */
public class Events extends Task {

    private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm:ss");
    private DateTimeFormatter intputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Return a new Events object.
     * isDone will be false by default
     *
     * @param task Name of the Events task.
     * @param from Events start time in "dd/mm/yy hh:mm:ss" format.
     * @param to Events end time in "dd/mm/yy hh:mm:ss" format.
     * @return Events object.
     */
    public Events(String task, String from, String to) {
        super(task);
        this.from = LocalDateTime.parse(from, intputFormatter);
        this.to = LocalDateTime.parse(to, intputFormatter);
    }

    /**
     * Return a new Events object.
     *
     * @param task Name of the Events task.
     * @param isDone Mark the Events being done as true or false.
     * @param from Events start time in "dd/mm/yy hh:mm:ss" format.
     * @param to Events end time in "dd/mm/yy hh:mm:ss" format.
     * @return Events object.
     */
    public Events(String task, boolean isDone, String from, String to) {
        super(task, isDone);
        this.from = LocalDateTime.parse(from, intputFormatter);
        this.to = LocalDateTime.parse(to, intputFormatter);
    }

    /**
     * Return the String format of how this task is saved in the hard disk.
     *
     * @return String format of how this task is saved in the hard disk.
     */
    public String saveText() {
        return "Event | " + (this.getIsDone() ? "1" : "0") + " | " + this.getTaskName() + " | "
                + this.from.format(intputFormatter) + " | " + this.to.format(intputFormatter);
    }

    /**
     * Return the string output representation of how this task is represented to the user.
     *
     * @return String format of how this task is represented to the user.
     */
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(outputFormatter)
                + " to: " + this.to.format(outputFormatter) + ")";
    }
}
