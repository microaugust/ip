package pain;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the task of storing and retrieving file on disk.
 */
public class Storage {

    private File file;

    /**
     * Return a new Storage object.
     *
     * @param pathName Path name of the file where the all tasks are stored.
     * @return new Storage object.
     */
    public Storage(String pathName) {
        this.file = new File(pathName);
    }

    /**
     * Return an ArrayList of Task storing the task that was previously saved.
     *
     * @return ArrayList of Task storing the task that was previously saved.
     * @throws IOException If the saved file is not found.
     */
    public ArrayList<Task> retrieveTask() throws IOException {
        try {
            Scanner sc = new Scanner(this.file);
            ArrayList<Task> userTasks = new ArrayList<Task>();
            while (sc.hasNextLine()) {
                String parse = sc.nextLine();
                if (parse.isEmpty()) {
                    sc.close();
                    break;
                }
                String[] splittedInput = parse.split("\\s*\\|\\s*");
                switch(splittedInput[0]) {
                case "Task":
                    userTasks = addTask(userTasks, splittedInput);
                    break;
                case "ToDo":
                    userTasks = addToDo(userTasks, splittedInput);
                    break;
                case "Deadline":
                    userTasks = addDeadline(userTasks, splittedInput);
                    break;
                case "Event":
                    userTasks = addEvent(userTasks, splittedInput);
                    break;
                default:
                    break;
                }
            }
            sc.close();
            return userTasks;
        } catch (CorruptedInputException | IndexOutOfBoundsException | DateTimeParseException e) {
            System.out.println("Corrupted File. New file will be made");
            FileWriter dataFile = new FileWriter(this.file);
            dataFile.close();
            return new ArrayList<Task>();
        }
    }

    /**
     * Saved current list of task on the hard disk.
     *
     * @param userTasks
     * @throws IOException If the saved file is not found.
     */
    public void saveTaskOnHardDisk(TaskList userTasks) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        for (Task t: userTasks.getUserTasks()) {
            fw.write(t.saveText() + System.lineSeparator());
        }
        fw.close();
    }

    public boolean exists() {
        return this.file.exists();
    }

    private static ArrayList<Task> addTask(ArrayList<Task> lst, String[] tokens) throws CorruptedInputException {
        if (!tokens[1].equals("0") && !tokens[1].equals("1")) {
            throw new CorruptedInputException();
        }
        if (tokens[1].equals("0")) {
            lst.add(new Task(tokens[2], false));
        } else if (tokens[1].equals("1")) {
            lst.add(new Task(tokens[2], true));
        }
        return lst;
    }

    private static ArrayList<Task> addToDo(ArrayList<Task> lst, String[] tokens) throws CorruptedInputException {
        if (!tokens[1].equals("0") && !tokens[1].equals("1")) {
            throw new CorruptedInputException();
        }
        if (tokens[1].equals("0")) {
            lst.add(new ToDos(tokens[2], false));
        } else if (tokens[1].equals("1")) {
            lst.add(new ToDos(tokens[2], true));
        }
        return lst;
    }

    private static ArrayList<Task> addDeadline(ArrayList<Task> lst, String[] tokens) throws CorruptedInputException {
        if (!tokens[1].equals("0") && !tokens[1].equals("1")) {
            throw new CorruptedInputException();
        }
        if (tokens[1].equals("0")) {
            lst.add(new Deadlines(tokens[2], false, tokens[3]));
        } else if (tokens[1].equals("1")) {
            lst.add(new Deadlines(tokens[2], true, tokens[3]));
        }
        return lst;
    }

    private static ArrayList<Task> addEvent(ArrayList<Task> lst, String[] tokens) throws CorruptedInputException {
        if (!tokens[1].equals("0") && !tokens[1].equals("1")) {
            throw new CorruptedInputException();
        } 
        if (tokens[1].equals("0")) {
            lst.add(new Events(tokens[2], false, tokens[3], tokens[4]));
        } else if (tokens[1].equals("1")) {
            lst.add(new Events(tokens[2], true, tokens[3], tokens[4]));
        }
        return lst;
    }
}
