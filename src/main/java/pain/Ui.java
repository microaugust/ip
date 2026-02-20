package pain;
import java.util.Scanner;

/**
 * Handles all user interaction for Pain.
 * This class is responsible for printing user-facing messages and for reading input.
 */
public class Ui {

    private String name;

    public Ui(String name) {
        this.name = name;
    }

    /**
     * Print program starting up text.
     */
    public String startUp() {
        return "Nihao! I'm " + this.name + "\nWhat you want";
    }

    /**
     * Return an Array of String of the input being parsed without any delimiter.
     *
     * @param sc Scanner object.
     * @param p Parser object.
     * @return Array of String of the input being parsed without any delimiter.
     * @throws InvalidCommandException If the command content is invalid.
     * @throws EmptyCommandException If the command doesn't contain any input when required.
     * @throws NoCommandException If the command doesn't exist.
     */
    public String[] getAndParseInput(Scanner sc, Parser p) throws InvalidCommandException,
            EmptyCommandException, NoCommandException {
        String input = sc.nextLine();
        String[] parsedInput = p.parseInput(input);
        return parsedInput;
    }

    /**
     * Print the approrpiate output according the the user input command.
     *
     * @param parseInput String array splitted by whitespace of the user input
     * @param taskList Current TaskList
     * @throws NoCommandException If the command doesn't exist.
     */
    public String generateOutput(String[] parseInput, TaskList taskList) throws NoCommandException {
        switch(parseInput[0]) {
        case "bye":
            return "Bye. I will definitely see you again";
        case "list":
            return taskList.toString();
        case "mark":
            int markIndex = Integer.parseInt(parseInput[1]) - 1;
            return "Nice! I've marked this task as done:\n" + taskList.get(markIndex).toString();
        case "unmark":
            int unmarkIndex = Integer.parseInt(parseInput[1]) - 1;
            return "OK, I've marked this task as not done yet:\n" + taskList.get(unmarkIndex).toString();
        case "todo", "deadline", "event":
            String addedTask = taskList.get(taskList.size() - 1).toString();
            return "Got it. I've added this task:\n" + addedTask + 
                    "\nNow you have " + taskList.size() + " tasks in the list.";
        case "delete":
            int deleteIndex = Integer.parseInt(parseInput[1]) - 1;
            return "Noted. I've removed this task:\n" + taskList.get(deleteIndex).toString() + 
                    "\nNow you have " + (taskList.size() - 1) + " tasks in the list.";
        case "find":
            TaskList foundList = new TaskList(taskList.find(parseInput[1]));
            return "Here are the matching tasks in your list:\n" + foundList.toString();
        default:
            throw new NoCommandException();
        }
    }
}
