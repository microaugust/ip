class Tasks{
    String[] taskList;
    int numOfTasks;

    Tasks() {
        this.taskList = new String[100];
        this.numOfTasks = 0;
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
            text += (i+1) + ". " + taskList[i];
        }
        return text;
    }

    public void add(String task) {
        this.taskList[numOfTasks] = task;
        numOfTasks++;
        System.out.println("    added: " + task);
    }
}