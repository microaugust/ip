class Task{
    String[] taskList;
    int numOfTasks;
    boolean[] checkBox;

    Task() {
        this.taskList = new String[100];
        this.numOfTasks = 0;
        this.checkBox = new boolean[100];
    }

    public String toString() {
        if(numOfTasks == 0) {
            return "THERE IS NOTHING HERE";
        }
        String text = "";
        for(int i = 0; i < numOfTasks; i++) {
            if(i != 0) {
                text += "\n    ";
            }
            text += (i+1) + ".[" + (checkBox[i] ? "X" : " ") + "] " + taskList[i];
        }
        return text;
    }

    public void add(String task) {
        this.taskList[numOfTasks] = task;
        numOfTasks++;
        System.out.println("    added: " + task);
    }

    public void mark(int i) {
        this.checkBox[i - 1] = true;
        System.out.println("    Nice! I've marked this test as done:");
        System.out.println("      [X] " + this.taskList[i - 1]);
    }

    public void unmark(int i){
        this.checkBox[i - 1] = false;
        System.out.println("    OK, I've marked this task as not done yet:");
        System.err.println("      [ ]" + this.taskList[i - 1]);
    }
}