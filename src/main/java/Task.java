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

    public void mark() {
        this.done = true;
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + this.toString());
    }

    public void unmark() {
        this.done = false;
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("      " + this.toString());
    }
}