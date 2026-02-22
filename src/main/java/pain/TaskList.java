package pain;
import java.util.ArrayList;
/**
 * Represents a list of {@link Task} objects and provides basic operations for it.
 */
public class TaskList {

    private static final String EMPTY_TASKLIST_OUTPUT = "NOTHING HERE";
    private static final String TASKLIST_OUTPUT_START = "Here are the tasks in your list:";

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
     * Checks whether the task list already contains a task with the same description
     * as the given task.
     *
     * @param addedTask the task to check for duplicates against
     * @return true if a task with the same description already exists in the list,
     *         false otherwise
     */
    public boolean containsDuplicate(Task addedTask) {
        for (Task t: this.userTasks) {
            if (addedTask.isSameTask(t)) {
                return true;
            }
        }
        return false;
    }

    public TaskList findDuplicate(Task addedTask) {
        ArrayList<Task> duplicates = new ArrayList<Task>();
        for (Task t: this.userTasks) {
            if (addedTask.isSameTask(t)) {
                duplicates.add(t);
            }
        }
        return new TaskList(duplicates);
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
            return EMPTY_TASKLIST_OUTPUT;
        }
        String toReturn = TASKLIST_OUTPUT_START;
        for (int i = 0; i < n; i++) {
            toReturn += "\n" + (i + 1) + ". " + this.userTasks.get(i).toString();
        }
        return toReturn;
    }

    /**
     * Returns a formatted string representation of just the task in the task list.
     *
     * @return A String representation of the tasks in this list.
     */
    public String toStringSkipFirstLine() {
        int n = this.userTasks.size();
        if (n == 0) {
            return "NOTHING HERE";
        }
        String toReturn = "";
        for (int i = 0; i < n; i++) {
            if (i != 0) {
                toReturn += "\n";
            }
            toReturn += (i + 1) + ". " + this.userTasks.get(i).toString();
        }
        return toReturn;
    }
}
