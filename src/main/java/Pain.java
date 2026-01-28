import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Pain{

    static final String PATHNAME = "data/pain.txt";

    static ArrayList<Task> userTasks = new ArrayList<Task>();

    static Scanner sc = new Scanner(System.in);

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static void printList(ArrayList<Task> taskList) {
        int n = taskList.size();
        if(n == 0) {
            System.out.println("    NOTHING HERE");
            return;
        }
        System.out.println("    Here are the tasks in your list:");
        for(int i = 0; i < n; i++) {
            System.out.println("    " + (i + 1) + ". " + taskList.get(i).toString());
        }
    }

    private static String getTaskName(String[] input) {
        String task = "";
        for(int i = 1; i < input.length; i++) {
            task += (input[i] + " ");
        }
        return task.substring(0, task.length() - 1);
    }

    private static void saveTaskOnHardDisk() throws IOException {
        File dataText = new File(PATHNAME);
        FileWriter fw = new FileWriter(dataText);
        for(Task t: userTasks) {
            fw.write(t.saveText() + System.lineSeparator());
        }
        fw.close();
    }

    private static void retrieveTask(Scanner sc) throws IOException {
        try {
            while(sc.hasNextLine()) {
                String parse = sc.nextLine();
                if(parse.isEmpty()) {
                    return;
                }
                String[] splittedInput= parse.split("\\s*\\|\\s*");
                switch(splittedInput[0]){
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
            }
        } catch(CorruptedInputException | IndexOutOfBoundsException | DateTimeParseException e) {
            System.out.println("Corrupted File. New file will be made");
            FileWriter dataFile = new FileWriter(PATHNAME);
            dataFile.close();
            userTasks = new ArrayList<Task>();
        }
    }

    public static void main(String[] args) throws IOException{
        File data = new File("data");
        File dataText = new File(PATHNAME);
        if(!data.exists()) {
            data.mkdirs();
        }
        if(dataText.exists()) {
            Scanner scanFile = new Scanner(dataText);
            retrieveTask(scanFile);
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
                    printList(userTasks);
                    break;
                case "mark":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    int taskToMark = Integer.parseInt(splittedInput[1]) - 1;
                    if(taskToMark >= userTasks.size()) {
                        throw new NotInListException();
                    }
                    userTasks.get(taskToMark).mark();
                    saveTaskOnHardDisk();
                    break;
                case "unmark":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    int taskToUnmark = Integer.parseInt(splittedInput[1]) - 1;
                    if(taskToUnmark >= userTasks.size()) {
                        throw new NotInListException();
                    }
                    userTasks.get(taskToUnmark).unmark();
                    saveTaskOnHardDisk();
                    break;
                case "todo":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    Task todoTask = new ToDos(getTaskName(splittedInput));
                    userTasks.add(todoTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + todoTask.toString());
                    System.out.println("    Now you have " + userTasks.size() + " tasks in the list.");
                    saveTaskOnHardDisk();
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
                    userTasks.add(deadlineTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + deadlineTask.toString());
                    System.out.println("    Now you have " + userTasks.size() + " tasks in the list.");
                    saveTaskOnHardDisk();
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
                    userTasks.add(eventTask);
                    System.out.println("    Got it. I've added this task:");
                    System.out.println("      " + eventTask.toString());
                    System.out.println("    Now you have " + userTasks.size() + " tasks in the list.");
                    saveTaskOnHardDisk();
                    break;
                case "delete":
                    if(splittedInput.length == 1) {
                        throw new EmptyCommandException();
                    }
                    int taskToDelete = Integer.parseInt(splittedInput[1]);
                    if(taskToDelete >= userTasks.size()) {
                        throw new NotInListException();
                    }
                    System.out.println("    Noted. I've removed this task:");
                    System.out.println("      " + userTasks.get(taskToDelete).toString());
                    userTasks.remove(taskToDelete);
                    System.out.println("    Now you have " + userTasks.size() + " tasks in the list.");
                    saveTaskOnHardDisk();
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