package pain;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Main program for the Pain application.
 * Initializes storage, parser, UI, and the task list (loading tasks from disk if available).
 */
public class Pain {

    private static final String PATHNAME = "data/pain.txt";
    private static final String NAME = "Pain";
    private static final Ui ui = new Ui(NAME);
    private static final String INVALID_COMMAND_OUTPUT = "The command structure is invalid";
    private static final String NO_COMMAND_OUTPUT = "The command doesn't exist";
    private static final String EMPTY_COMMAND_OUTPUT = "This command need some arguments";
    private static final String NOT_IN_LIST_OUTPUT = "That task doesn't exist";
    private static final String NOT_INTEGER_ARGUMENT_OUTPUT = "Argument must be an integer";
    private static final String DATE_TIME_PARSE_ERROR_OUTPUT = "Date and time input format is dd/mm/yyyy hh:mm:ss";
    private static final String OTHER_ERROR = "SORRY AN ERROR JUST OCCUR";

    private final Parser parser = new Parser();
    private TaskList taskList;
    private Storage taskStorage;

    /**
     * Initializes application storage on hard disk and task list using data on disk if available.
     * Load tasks from disk into a taskList, if failed then, taskList will be empty.
     *
     * @throws IOException if an I/O error occurs while accessing the storage file
     *                     or loading tasks from disk.
     */
    public void retrieveStorage() throws IOException {
        File data = new File("data");
        this.taskStorage = new Storage(PATHNAME);
        if (!data.exists()) {
            data.mkdirs();
        }
        if (this.taskStorage.exists()) {
            this.taskList = new TaskList(this.taskStorage.retrieveTask());
        } else {
            this.taskList = new TaskList();
        }
    }

    public String startUp() {
        return ui.startUp();
    }

    // AI-assisted: ChatGPT suggests to help refactor getResponse() into helper methods.
    public String getResponse(String input) {
        try {
            String[] parsedInput = parser.parseInput(input);
            switch(parsedInput[0]) {
            case "bye":
                System.exit(0);
                return "EXIT";
            case "list":
                return handleListCommand(parsedInput);
            case "mark":
                return handleMarkCommand(parsedInput);
            case "unmark":
                return handleUnmarkCommand(parsedInput);
            case "todo":
                return handleToDoCommand(parsedInput);
            case "deadline":
                return handleDeadlineCommand(parsedInput);
            case "event":
                return handleEventCommand(parsedInput);
            case "delete":
                return handleDeleteCommand(parsedInput);
            case "find":
                return handleFindCommand(parsedInput);
            default:
                throw new NoCommandException();
            }
        } catch (InvalidCommandException e) {
            return INVALID_COMMAND_OUTPUT;
        } catch (NoCommandException e) {
            return NO_COMMAND_OUTPUT;
        } catch (EmptyCommandException e) {
            return EMPTY_COMMAND_OUTPUT;
        } catch (NotInListException e) {
            return NOT_IN_LIST_OUTPUT;
        } catch (NumberFormatException e) {
            return NOT_INTEGER_ARGUMENT_OUTPUT;
        } catch (DateTimeParseException e) {
            return DATE_TIME_PARSE_ERROR_OUTPUT;
        } catch (Exception e) {
            return OTHER_ERROR;
        }
    }

    private String duplicateTaskMessage(Task task) {
        TaskList duplicateList = this.taskList.findDuplicate(task);
        return "Note that you are adding:" + task.toString() + "\nThe following is/are already in the list:\n"
                + duplicateList.toStringSkipFirstLine();
    }

    private String handleListCommand(String[] tokens) throws NoCommandException {
        assert tokens.length == 1 : "Invalid list comand";
        return ui.generateOutput(tokens, this.taskList);
    }

    private String handleMarkCommand(String[] tokens) throws IOException, NoCommandException,
            NotInListException, NumberFormatException {
        assert tokens.length == 2 : "Invalid mark command";
        int taskToMark = Integer.parseInt(tokens[1]) - 1;
        if (taskToMark > this.taskList.size()) {
            throw new NotInListException();
        }
        this.taskList.get(taskToMark).mark();
        this.taskStorage.saveTaskOnHardDisk(this.taskList);
        return ui.generateOutput(tokens, this.taskList);
    }

    private String handleUnmarkCommand(String[] tokens) throws IOException, NoCommandException,
            NotInListException, NumberFormatException {
        assert tokens.length == 2 : "Invalid unmark command";
        int taskToUnmark = Integer.parseInt(tokens[1]) - 1;
        if (taskToUnmark > this.taskList.size()) {
            throw new NotInListException();
        }
        this.taskList.get(taskToUnmark).unmark();
        this.taskStorage.saveTaskOnHardDisk(this.taskList);
        return ui.generateOutput(tokens, this.taskList);
    }

    private String handleToDoCommand(String[] tokens) throws IOException, NoCommandException {
        assert tokens.length == 2 : "Invalid todo command";
        Task todoTask = new ToDos(tokens[1]);
        if (this.taskList.containsDuplicate(todoTask)) {
            String output = duplicateTaskMessage(todoTask);
            this.taskList.add(todoTask);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            return output;
        }
        this.taskList.add(todoTask);
        this.taskStorage.saveTaskOnHardDisk(this.taskList);
        return ui.generateOutput(tokens, this.taskList);
    }

    private String handleDeadlineCommand(String[] tokens) throws IOException, NoCommandException {
        assert tokens.length == 3 : "Invalid deadline command";
        Task deadlineTask = new Deadlines(tokens[1], tokens[2]);
        if (this.taskList.containsDuplicate(deadlineTask)) {
            String output = duplicateTaskMessage(deadlineTask);
            this.taskList.add(deadlineTask);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            return output;
        }
        this.taskList.add(deadlineTask);
        this.taskStorage.saveTaskOnHardDisk(this.taskList);
        return ui.generateOutput(tokens, this.taskList);
    }

    private String handleEventCommand(String[] tokens) throws IOException, NoCommandException {
        assert tokens.length == 4 : "Invalid event command";
        Task eventTask = new Events(tokens[1], tokens[2], tokens[3]);
        if (this.taskList.containsDuplicate(eventTask)) {
            String output = duplicateTaskMessage(eventTask);
            this.taskList.add(eventTask);
            this.taskStorage.saveTaskOnHardDisk(this.taskList);
            return output;
        }
        this.taskList.add(eventTask);
        this.taskStorage.saveTaskOnHardDisk(this.taskList);
        return ui.generateOutput(tokens, this.taskList);
    }

    private String handleDeleteCommand(String[] tokens) throws IOException, NoCommandException,
            NotInListException, NumberFormatException {
        assert tokens.length == 2 : "Invalid delete command";
        int taskToDelete = Integer.parseInt(tokens[1]) - 1;
        if (taskToDelete > this.taskList.size()) {
            throw new NotInListException();
        }
        String output = ui.generateOutput(tokens, taskList);
        this.taskList.delete(taskToDelete);
        this.taskStorage.saveTaskOnHardDisk(this.taskList);
        return output;
    }

    private String handleFindCommand(String[] tokens) throws IOException, NoCommandException {
        assert tokens.length == 2 : "Invalid find command";
        return ui.generateOutput(tokens, taskList);
    }
}
