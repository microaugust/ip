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
        this.taskStorage = new Storage(PATHNAME);
        if(!data.exists()) {
            data.mkdirs();
        }
        if(this.taskStorage.exists()) {
            this.taskList = new TaskList(this.taskStorage.retrieveTask());
        } else {
            this.taskList = new TaskList();
        }
    }

    public String startUp() {
        return ui.startUp();
    }

    public String getResponse(String input) throws IOException, InvalidCommandException, NoCommandException, EmptyCommandException, NotInListException {
        String[] parsedInput = parser.parseInput(input);
        String output = "";
        switch(parsedInput[0]) {
        //add error handling later
        case "bye":
            System.exit(0);
        case "list":
            output = ui.generateOutput(parsedInput, taskList);
            break;
        case "mark":
            assert parsedInput.length == 2: "Invalid mark command";
            int taskToMark = Integer.parseInt(parsedInput[1]) - 1;
            this.taskList.get(taskToMark).mark();
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            output = ui.generateOutput(parsedInput, taskList);
            break;
        case "unmark":
            assert parsedInput.length == 2: "Invalid unmark command"; 
            int taskToUnmark = Integer.parseInt(parsedInput[1]) - 1;
            this.taskList.get(taskToUnmark).unmark();
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            output = ui.generateOutput(parsedInput, taskList);
            break;
        case "todo":
            assert parsedInput.length == 2: "Invalid todo command";
            Task todoTask = new ToDos(parsedInput[1]);
            if (this.taskList.containsDuplicate(todoTask)) {
                output = duplicateTaskMessage(todoTask);
                this.taskList.add(todoTask);
                this.taskStorage.saveTaskOnHardDisk(this.taskList);
                break;
            }
            this.taskList.add(todoTask);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            output = ui.generateOutput(parsedInput, taskList);
            break;
        case "deadline":
            assert parsedInput.length == 3: "Invalid deadline command";
            Task deadlineTask = new Deadlines(parsedInput[1], parsedInput[2]);
            if (this.taskList.containsDuplicate(deadlineTask)) {
                output = duplicateTaskMessage(deadlineTask);
                this.taskList.add(deadlineTask);
                this.taskStorage.saveTaskOnHardDisk(this.taskList);
                break;
            }
            this.taskList.add(deadlineTask);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            output = ui.generateOutput(parsedInput, taskList);
            break;
        case "event":
            assert parsedInput.length == 4: "Invalid event command";
            Task eventTask = new Events(parsedInput[1], parsedInput[2], parsedInput[3]);
            if (this.taskList.containsDuplicate(eventTask)) {
                output = duplicateTaskMessage(eventTask);
                this.taskList.add(eventTask);
                this.taskStorage.saveTaskOnHardDisk(this.taskList);
                break;
            }
            this.taskList.add(eventTask);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            output = ui.generateOutput(parsedInput, taskList);
            break;
        case "delete":
            assert parsedInput.length == 2: "Invalid delete command";
            int taskToDelete = Integer.parseInt(parsedInput[1]) - 1;
            if (taskToDelete > this.taskList.size()) {
                throw new NotInListException();
            }
            output = ui.generateOutput(parsedInput, taskList);
            this.taskList.delete(taskToDelete);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            break;
        case "find":
            assert parsedInput.length == 2: "Invalid find command";
            output = ui.generateOutput(parsedInput, taskList);
            break;
        default:
            break;
        }
        return output;
    }

    private String duplicateTaskMessage(Task task) {
        TaskList duplicateList = this.taskList.findDuplicate(task);
        return "Note that you are adding:" + task.toString() + "\nThe following is/are already in the list:\n" + duplicateList.toStringSkipFirstLine();
    }
}
