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
                    if (splittedInput[1].equals("0")) {
                        userTasks.add(new Task(splittedInput[2], false));
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new Task(splittedInput[2], true));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                case "ToDo":
                    if (splittedInput[1].equals("0")) {
                        userTasks.add(new ToDos(splittedInput[2], false));
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new ToDos(splittedInput[2], true));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                case "Deadline":
                    if (splittedInput[1].equals("0")) {
                        userTasks.add(new Deadlines(splittedInput[2], false, splittedInput[3]));
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new Deadlines(splittedInput[2], true, splittedInput[3]));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                case "Event":
                    if (splittedInput[1].equals("0")) {
                        userTasks.add(new Events(splittedInput[2], false, splittedInput[3], splittedInput[4]));
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new Events(splittedInput[2], true, splittedInput[3], splittedInput[4]));
                    } else {
                        throw new CorruptedInputException();
                    }
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
}
