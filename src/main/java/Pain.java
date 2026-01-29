import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Pain{

    static final String PATHNAME = "data/pain.txt";

    static Scanner sc = new Scanner(System.in);

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    public static void main(String[] args) throws IOException{
        File data = new File("data");
        File dataText = new File(PATHNAME);
        Storage taskStorage = new Storage(PATHNAME);
        Parser parser = new Parser();
        if(!data.exists()) {
            data.mkdirs();
        }
        TaskList taskList;
        if(taskStorage.exists()) {
            taskList = new TaskList(taskStorage.retrieveTask());
        } else {
            taskList = new TaskList();
        }

        printLine();
        System.out.println("    Nihao! I'm Pain");
        System.out.println("    Yo want you want");
        printLine();
        while(true) {
            try{
                String input = sc.nextLine();
                printLine();
                String[] parsedInput = parser.parseInput(input);
                switch(parsedInput[0]) {
                case "bye":
                    System.out.println("    Bye. I will definitely see you again");
                    printLine();
                    sc.close();
                    System.exit(0);

                case "list":
                    System.out.println(taskList.toString());
                    break;

                case "mark":
                    int taskToMark = Integer.parseInt(parsedInput[1]) - 1;
                    if(taskToMark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToMark).mark();
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "unmark":
                    int taskToUnmark = Integer.parseInt(parsedInput[1]) - 1;
                    if(taskToUnmark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToUnmark).unmark();
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "todo":
                    Task todoTask = new ToDos(parsedInput[1]);
                    taskList.add(todoTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + todoTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "deadline":
                    Task deadlineTask = new Deadlines(parsedInput[1], parsedInput[2]);
                    taskList.add(deadlineTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + deadlineTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "event":
                    Task eventTask = new Events(parsedInput[1], parsedInput[2], parsedInput[3]);
                    taskList.add(eventTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + eventTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "delete":
                    int taskToDelete = Integer.parseInt(parsedInput[1]);
                    if(taskToDelete >= taskList.size()) {
                        throw new NotInListException();
                    }
                    System.out.println("    Noted. I've removed this task:");
                    System.out.println("      " + taskList.get(taskToDelete).toString());
                    taskList.delete(taskToDelete);
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
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
            } finally {
                printLine();
            }
        }
    }
}