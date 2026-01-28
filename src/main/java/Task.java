public class Task{

    private String task;
    private boolean isDone;

    public Task(String task) {
        this.task = task;
        this.isDone = false;
    }

    public Task(String task, boolean isDone) {
        this.task = task;
        this.isDone = isDone;
    }

    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.task;
    }

    /**
     * Prints text saying that this task is done and mark the task as done
     */
    public void mark() {
        this.isDone = true;
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + this.toString());
    }

    /**
     * Prints text saying that this task is mark as undone and unmark the task
     */
    public void unmark() {
        this.isDone = false;
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + this.toString());
    }

    public String saveText() {
        return "Task | " + (this.isDone ? "1" : "0") + " | " + this.task;
    }

    protected boolean getIsDone() {
        return this.isDone;
    }

    protected String getTaskName() {
        return this.task;
    }
}