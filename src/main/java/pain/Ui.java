package pain;
import java.util.Scanner;
import java.util.ArrayList;

public class Ui {

    private String name;

    public Ui(String name) {
        this.name = name;
    }

    public void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Print program starting up text.
     */
    public void startUp() {
        printLine();
        System.out.println("    Nihao! I'm " + this.name);
        System.out.println("    Yo want you want");
        printLine();
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
    public String[] getAndParseInput(Scanner sc, Parser p) throws InvalidCommandException, EmptyCommandException, NoCommandException{
        String input = sc.nextLine();
        printLine();
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
    public void printOutput(String[] parseInput, TaskList taskList) throws NoCommandException{
        switch(parseInput[0]) {
        case "bye":
            System.out.println("    Bye. I will definitely see you again");
            break;
        case "list":
            System.out.println(taskList.toString());
            break;
        case "mark":
            int markIndex = Integer.parseInt(parseInput[1]) - 1;
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("      " + taskList.get(markIndex).toString());
            break;
        case "unmark":
            int unmarkIndex = Integer.parseInt(parseInput[1]) - 1;
            System.out.println("    OK, I've marked this task as not done yet:");
            System.out.println("      " + taskList.get(unmarkIndex).toString());
            break;
        case "todo":
        case "deadline":
        case "event":
            System.out.println("    Got it. I've added this task:");
            System.out.println("      " + taskList.get(taskList.size() - 1).toString());
            System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
            break;
        case "delete":
            int deleteIndex = Integer.parseInt(parseInput[1]) - 1;
            System.out.println("    Noted. I've removed this task:");
            System.out.println("      " + taskList.get(deleteIndex).toString());
            System.out.println("    Now you have " + (taskList.size() - 1) + " tasks in the list.");
            break;
        default:
            throw new NoCommandException();
        }
        printLine();
    }
}
