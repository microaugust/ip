package pain;

/**
 * Represents a basic task with a description and a completion status.
 */
public class Task {

    private String task;
    private boolean isDone;

    /**
     * Return a new Task object.
     * isDone is false by default.
     *
     * @param task Task name.
     */
    public Task(String task) {
        this.task = task;
        this.isDone = false;
    }

    /**
     * Return a new Task object.
     *
     * @param task Task name.
     * @param Mark the Task being done as true or false.
     */
    public Task(String task, boolean isDone) {
        this.task = task;
        this.isDone = isDone;
    }

    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.task;
    }

    /**
     * Mark the task as being done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Mark the task as being not done.
     */
    public void unmark() {
        this.isDone = false;
    }


    /**
     * Return the String format of how this task is saved in the hard disk.
     *
     * @return String format of how this task is saved in the hard disk.
     */
    public String saveText() {
        return "Task | " + (this.isDone ? "1" : "0") + " | " + this.task;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String getTaskName() {
        return this.task;
    }
}
