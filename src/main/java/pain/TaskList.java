package pain;
import java.util.ArrayList;
/**
 * Represents a list of {@link Task} objects and provides basic operations for it.
 */
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

    /**
     * Return ArrayList of Task with the specify keyword.
     *
     * @param keyword Keyword to look for.
     * @return ArratList of Task with the specify keyword
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> listOfTaskWithKeywords = new ArrayList<Task>();
        for (Task t: this.userTasks) {
            if (t.getTaskName().contains(keyword)) {
                listOfTaskWithKeywords.add(t);
            }
        }
        return listOfTaskWithKeywords;
    }

    /**
     * Returns a formatted string representation of the current task list to be return.
     *
     * @return A String representation of the tasks in this list.
     */
    public String toString() {
        int n = this.userTasks.size();
        if (n == 0) {
            return "NOTHING HERE";
        }
        String toReturn = "";
        toReturn += "Here are the tasks in your list:";
        for (int i = 0; i < n; i++) {
            toReturn += "\n" + (i + 1) + ". " + this.userTasks.get(i).toString();
        }
        return toReturn;
    }
}

