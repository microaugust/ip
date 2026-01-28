public class Events extends Task {

    private String from;
    private String to;

    public Events(String task, String from, String to) {
        super(task);
        this.from = from;
        this.to = to;
    }
    
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }

    public String saveText() {
        return "Event | " + (this.getDone() ? "1" : "0") + " | " + this.getTaskName() + " | " + this.from + " | " + this.to;
    }
}
