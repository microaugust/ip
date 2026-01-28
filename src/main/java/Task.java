public class Task{
    private String task;
    private boolean done;
    public Task(String task) {
        this.task = task;
        this.done = false;
    }

    public String toString() {
        return "[" + (this.done ? "X" : " ") + "] " + this.task;
    }

    /**
     * Prints text saying that this task is done and mark the task as done
     */
    public void mark() {
        this.done = true;
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + this.toString());
    }

    /**
     * Prints text saying that this task is mark as undone and unmark the task
     */
    public void unmark() {
        this.done = false;
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + this.toString());
    }

    public String saveText() {
        return "Task | " + (this.done ? "1" : "0");
    }

    protected boolean getDone() {
        return this.done;
    }

    protected String getTaskName() {
        return this.task;
    }
}