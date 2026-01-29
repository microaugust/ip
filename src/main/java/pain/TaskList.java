package pain;
import java.util.ArrayList;
public class TaskList {

    private ArrayList<Task> userTasks;
    
    public TaskList() {
        this.userTasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> userTasks) {
        this.userTasks = userTasks;
    }

    public void add(Task task) {
        this.userTasks.add(task);
    }

    public void delete(int index) {
        userTasks.remove(index);
    }

    public int size() {
        return userTasks.size();
    }

    public Task get(int index) {
        return this.userTasks.get(index);
    }

    public ArrayList<Task> getUserTasks() {
        return this.userTasks;
    }

    public String toString() {
        int n = this.userTasks.size();
        String toReturn = "";
        if(n == 0) {
            return "    NOTHING HERE";
        }
        toReturn += "    Here are the tasks in your list:";
        for(int i = 0; i < n; i++) {
            toReturn += "\n    " + (i + 1) + ". " + this.userTasks.get(i).toString();
        }
        return toReturn;
    }
}

