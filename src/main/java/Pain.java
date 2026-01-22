import java.util.Scanner;
import java.util.ArrayList;

public class Pain {

    static ArrayList<Task> userTasks = new ArrayList<Task>();

    static Scanner sc = new Scanner(System.in);

    private static void query() {
        try{
            String input = sc.nextLine();
            String[] splittedInput = input.split("\\s+"); 
            printLine();
            if(splittedInput[0].equals("bye")) {
                System.out.println("    Bye. I will definitely see you again");
                printLine();
                sc.close();
                System.exit(0);
            } else if(splittedInput[0].equals("list")) {
                printList(userTasks);
            } else if(splittedInput[0].equals("mark")) {
                if(splittedInput.length == 1) {
                    throw new EmptyCommandException();
                }
                int taskNum = Integer.parseInt(splittedInput[1]) - 1;
                if(taskNum >= userTasks.size()) {
                    throw new NotInListException();
                }
                userTasks.get(taskNum).mark();
            } else if(splittedInput[0].equals("unmark")) {
                if(splittedInput.length == 1) {
                    throw new EmptyCommandException();
                }
                int taskNum = Integer.parseInt(splittedInput[1]) - 1;
                if(taskNum >= userTasks.size()) {
                    throw new NotInListException();
                }
                userTasks.get(taskNum).unmark();
            } else if(splittedInput[0].equals("todo")) {
                if(splittedInput.length == 1) {
                    throw new EmptyCommandException();
                }
                Task temp = new ToDos(getTaskName(splittedInput));
                userTasks.add(temp);
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + temp.toString());
                System.out.println("    Now you have " + userTasks.size() + " tasks in the list.");
            } else if(splittedInput[0].equals("deadline")) {
                if(splittedInput.length == 1) {
                    throw new EmptyCommandException();
                } else if((!input.contains(" /by "))) {
                    throw new InvalidCommandException();
                }
                String tempString = getTaskName(splittedInput);
                String[] splitDate = tempString.split("/by ");
                Task temp = new Deadlines(splitDate[0], splitDate[1]);
                userTasks.add(temp);
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + temp.toString());
                System.out.println("    Now you have " + userTasks.size() + " tasks in the list."); 
            } else if(splittedInput[0].equals("event")) {
                if(splittedInput.length == 1) {
                    throw new EmptyCommandException();
                } else if((!input.contains(" /from ")) || (!input.contains(" /to "))) {
                    throw new InvalidCommandException();
                }
                String tempString = getTaskName(splittedInput);
                String[] tempOne = tempString.split(" /to ");
                String[] tempTwo = tempOne[0].split(" /from ");
                String to = tempOne[1];
                String from = tempTwo[1];
                String taskName = tempTwo[0];
                Task temp = new Events(taskName, from, to);
                userTasks.add(temp);
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + temp.toString());
                System.out.println("    Now you have " + userTasks.size() + " tasks in the list."); 
            } else if(splittedInput[0].equals("delete")) {
                if(splittedInput.length == 1) {
                    throw new EmptyCommandException();
                }
                int taskNum = Integer.parseInt(splittedInput[1]);
                if(taskNum >= userTasks.size()) {
                    throw new NotInListException();
                }
                System.out.println("    Noted. I've removed this task:");
                System.out.println("      " + userTasks.get(taskNum).toString());
                userTasks.remove(taskNum);
                System.out.println("    Now you have " + userTasks.size() + " tasks in the list.");

            }
            else {
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
        } finally {
            printLine();
        }
    }

    private static void runPain() {
        while(true) {
            query();
        }
    }

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

    public static void main(String[] args) {
        printLine();
        System.out.println("    Nihao! I'm Pain");
        System.out.println("    Yo want you want");
        printLine();
        runPain();
    }
}