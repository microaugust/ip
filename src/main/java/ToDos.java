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

    public String saveText() {
        return "ToDo | " + (this.getIsDone() ? "1" : "0") + " | " + this.getTaskName();
    }
}
