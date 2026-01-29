import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private File file;

    public Storage(String pathName) {
        this.file = new File(pathName);
    }

    public ArrayList<Task> retrieveTask() throws IOException {
        try {
            Scanner sc = new Scanner(this.file);
            ArrayList<Task> userTasks = new ArrayList<Task>();
            while(sc.hasNextLine()) {
                String parse = sc.nextLine();
                if(parse.isEmpty()) {
                    sc.close();
                    break;
                }
                String[] splittedInput= parse.split("\\s*\\|\\s*");
                switch(splittedInput[0]) {
                case "Task":
                    if(splittedInput[1].equals("0")) {
                        userTasks.add(new Task(splittedInput[2], false));    
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new Task(splittedInput[2], true));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                case "ToDo":
                    if(splittedInput[1].equals("0")) {
                        userTasks.add(new ToDos(splittedInput[2], false));    
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new ToDos(splittedInput[2], true));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                case "Deadline":
                    if(splittedInput[1].equals("0")) {
                        userTasks.add(new Deadlines(splittedInput[2], false, splittedInput[3]));    
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new Deadlines(splittedInput[2], true, splittedInput[3]));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                case "Event":
                    if(splittedInput[1].equals("0")) {
                        userTasks.add(new Events(splittedInput[2], false, splittedInput[3], splittedInput[4]));    
                    } else if (splittedInput[1].equals("1")) {
                        userTasks.add(new Events(splittedInput[2], true, splittedInput[3], splittedInput[4]));
                    } else {
                        throw new CorruptedInputException();
                    }
                    break;
                }
                sc.close();
                return userTasks;
            }
        } catch(CorruptedInputException | IndexOutOfBoundsException | DateTimeParseException e) {
            System.out.println("Corrupted File. New file will be made");
            FileWriter dataFile = new FileWriter(this.file);
            dataFile.close();
            return new ArrayList<Task>();
        }
        return new ArrayList<Task>();
    }

    public void saveTaskOnHardDisk(TaskList userTasks) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        for(Task t: userTasks.getUserTasks()) {
            fw.write(t.saveText() + System.lineSeparator());
        }
        fw.close();
    }

    public boolean exists() {
        return this.file.exists();
    }
}
