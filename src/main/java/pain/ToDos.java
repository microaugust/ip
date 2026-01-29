package pain;
public class ToDos extends Task{
    public ToDos(String task) {
        super(task);
    }

    public ToDos(String task, boolean isDone) {
        super(task, isDone);
    }

    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Return the String format of how this task is saved in the hard disk.
     * 
     * @return String format of how this task is saved in the hard disk.
     */
    public String saveText() {
        return "ToDo | " + (this.getIsDone() ? "1" : "0") + " | " + this.getTaskName();
    }
}
