public class ToDos extends Task{
    public ToDos(String task) {
        super(task);
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}
