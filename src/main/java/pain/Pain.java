package pain;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main program for the Pain application.
 * Initializes storage, parser, UI, and the task list (loading tasks from disk if available).
 */
public class Pain {

    private static final String PATHNAME = "data/pain.txt";
    private static final String NAME = "Pain";
    private static final Ui ui = new Ui("Pain");

    private static Scanner sc = new Scanner(System.in); // i think don't need this anymore
    private final Parser parser = new Parser();
    private TaskList taskList;
    private Storage taskStorage;

    public void retrieveStorage() throws IOException {
        File data = new File("data");
        File dataText = new File(PATHNAME);
        taskStorage = new Storage(PATHNAME);
        if(!data.exists()) {
            data.mkdirs();
        }
        if(taskStorage.exists()) {
            this.taskList = new TaskList(taskStorage.retrieveTask());
        } else {
            this.taskList = new TaskList();
        }
    }

    public String getResponse(String input) throws IOException, InvalidCommandException, NoCommandException, EmptyCommandException, NotInListException {
        String[] parsedInput = parser.parseInput(input);
        switch(parsedInput[0]) {
        //add error handling later
        case "bye":
            System.exit(0);
        case "list":
            break;
        case "mark":
            int taskToMark = Integer.parseInt(parsedInput[1]) - 1;
            taskList.get(taskToMark).mark();
            taskStorage.saveTaskOnHardDisk(taskList);
            break;
        case "unmark": 
            int taskToUnmark = Integer.parseInt(parsedInput[1]) - 1;
            taskList.get(taskToUnmark).unmark();
            taskStorage.saveTaskOnHardDisk(taskList);
            break;
        case "todo":
            Task todoTask = new ToDos(parsedInput[1]);
            taskList.add(todoTask);
            taskStorage.saveTaskOnHardDisk(taskList); 
            break;
        case "deadline":
            Task deadlineTask = new Deadlines(parsedInput[1], parsedInput[2]);
            taskList.add(deadlineTask);
            taskStorage.saveTaskOnHardDisk(taskList);
            break;
        case "event":
            Task eventTask = new Events(parsedInput[1], parsedInput[2], parsedInput[3]);
            taskList.add(eventTask);
            taskStorage.saveTaskOnHardDisk(taskList);
            break;
        case "delete":
           int taskToDelete = Integer.parseInt(parsedInput[1]) - 1;
            if (taskToDelete >= taskList.size()) {
                throw new NotInListException();
            }
            taskList.delete(taskToDelete);
            taskStorage.saveTaskOnHardDisk(taskList);
            break;
        case "find":
            break;
        default:
            break;
        }
        return ui.generateOutput(parsedInput, taskList);
    }

    /*
    public static void main(String[] args) throws IOException {
        File data = new File("data");
        File dataText = new File(PATHNAME);
        Storage taskStorage = new Storage(PATHNAME);
        Parser parser = new Parser();
        Ui ui = new Ui(NAME);

        if (!data.exists()) {
            data.mkdirs();
        }
        TaskList taskList;
        if (taskStorage.exists()) {
            taskList = new TaskList(taskStorage.retrieveTask());
        } else {
            taskList = new TaskList();
        }

        ui.startUp();

        while (true) { //PRINT OUTPUT RETURN A STRING NOW
            try {
                String[] parsedInput = ui.getAndParseInput(sc, parser);
                switch(parsedInput[0]) {
                case "bye":
                    //ui.printOutput(parsedInput, taskList);
                    sc.close();
                    System.exit(0);
                    break;
                case "list":
                    //ui.printOutput(parsedInput, taskList);
                    break;
                case "mark":
                    int taskToMark = Integer.parseInt(parsedInput[1]) - 1;
                    if (taskToMark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToMark).mark();
                    //ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "unmark":
                    int taskToUnmark = Integer.parseInt(parsedInput[1]) - 1;
                    if (taskToUnmark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToUnmark).unmark();
                    //ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "todo":
                    Task todoTask = new ToDos(parsedInput[1]);
                    taskList.add(todoTask);
                    //ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "deadline":
                    Task deadlineTask = new Deadlines(parsedInput[1], parsedInput[2]);
                    taskList.add(deadlineTask);
                    //ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "event":
                    Task eventTask = new Events(parsedInput[1], parsedInput[2], parsedInput[3]);
                    taskList.add(eventTask);
                    //ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "delete":
                    int taskToDelete = Integer.parseInt(parsedInput[1]) - 1;
                    if (taskToDelete >= taskList.size()) {
                        throw new NotInListException();
                    }
                    //ui.printOutput(parsedInput, taskList);
                    taskList.delete(taskToDelete);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "find":
                    //ui.printOutput(parsedInput, taskList);
                    break;
                default:
                    throw new NoCommandException();
                }
            } catch (NoCommandException e) {
                System.out.println("    brotha, what are you typing? i don't understand u");
            } catch (EmptyCommandException e) {
                System.out.println("    ooooooh, this cannot be empty lah");
            } catch (InvalidCommandException e) {
                System.out.println("    aiya recheck whether this correct format or not aah");
            } catch (NotInListException e) {
                System.out.println("    that index not in the list lol");
            } catch (NumberFormatException e) {
                System.out.println("    need to be a number");
            } catch (DateTimeParseException e) {
                System.out.println("    invalid date time format (need to be dd/mm/yyyy hh:mm:ss)");
            }
        }
    }
    */
}
