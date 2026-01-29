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

    private static String getTaskName(String[] input) {
        String task = "";
        for(int i = 1; i < input.length; i++) {
            task += (input[i] + " ");
        }
        return task.substring(0, task.length() - 1);
    }

    public static void main(String[] args) throws IOException{
        File data = new File("data");
        File dataText = new File(PATHNAME);
        Storage taskStorage = new Storage(PATHNAME);
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
                String[] splittedInput = input.split("\\s+"); 
                printLine();
                switch(splittedInput[0]) {
                case "bye":
                    System.out.println("    Bye. I will definitely see you again");
                    printLine();
                    sc.close();
                    System.exit(0);
                case "list":
                    System.out.println(taskList.toString());
                    break;
                case "mark":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    int taskToMark = Integer.parseInt(splittedInput[1]) - 1;
                    if(taskToMark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToMark).mark();
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "unmark":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    int taskToUnmark = Integer.parseInt(splittedInput[1]) - 1;
                    if(taskToUnmark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToUnmark).unmark();
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "todo":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    Task todoTask = new ToDos(getTaskName(splittedInput));
                    taskList.add(todoTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + todoTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "deadline":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    } else if((!input.contains(" /by "))) {
                        throw new InvalidCommandException();
                    }
                    String deadlineName = getTaskName(splittedInput);
                    String[] splitDate = deadlineName.split(" /by ");
                    Task deadlineTask = new Deadlines(splitDate[0], splitDate[1]);
                    taskList.add(deadlineTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + deadlineTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "event":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    } else if((!input.contains(" /from ")) || (!input.contains(" /to "))) {
                        throw new InvalidCommandException();
                    }
                    String tempString = getTaskName(splittedInput);
                    String[] tempOne = tempString.split(" /to ");
                    String[] tempTwo = tempOne[0].split(" /from ");
                    String toDate = tempOne[1];
                    String fromDate = tempTwo[1];
                    String taskName = tempTwo[0];
                    Task eventTask = new Events(taskName, fromDate, toDate);
                    taskList.add(eventTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + eventTask.toString());
                    System.out.println("    Now you have " + taskList.size() + " tasks in the list.");
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;
                case "delete":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    int taskToDelete = Integer.parseInt(splittedInput[1]);
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