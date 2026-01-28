public class ToDos extends Task{
    public ToDos(String task) {
        super(task);
    }

    public String toString() {
        return "[T]" + super.toString();
    }

    public String saveText() {
        return "ToDo | " + (this.getDone() ? "1" : "0") + " | " + this.getTaskName();
    }
}
