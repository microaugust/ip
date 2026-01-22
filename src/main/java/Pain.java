import java.util.Scanner;

public class Pain {

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    private static void printList(Task[] taskList, int n) {
        if(n == 0) {
            System.out.println("NOTHING HERE");
            return;
        }
        System.out.println("    Here are the tasks in your list:");
        for(int i = 0; i < n; i++) {
            System.out.println("    " + (i + 1) + ". " + taskList[i].toString());
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
        Task[] userTasks = new Task[100];
        int numOfTasks = 0;
        printLine();
        System.out.println("    Nihao! I'm Pain");
        System.out.println("    Yo want you want");
        printLine();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splittedInput = input.split("\\s+"); 
        printLine();
        while(true){
            if(splittedInput[0].equals("bye")) {
                System.out.println("    Bye. I will definitely see you again");
                printLine();
                break;
            } else if(splittedInput[0].equals("list")) {
                printList(userTasks, numOfTasks);
            } else if(splittedInput[0].equals("mark")) {
                int taskNum = Integer.parseInt(splittedInput[1]) - 1;
                userTasks[taskNum].mark();
            } else if(splittedInput[0].equals("unmark")) {
                int taskNum = Integer.parseInt(splittedInput[1]) - 1;
                userTasks[taskNum].unmark();
            } else if(splittedInput[0].equals("todo")) {
                Task temp = new ToDos(getTaskName(splittedInput));
                userTasks[numOfTasks] = temp;
                numOfTasks++;
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + temp.toString());
                System.out.println("    Now you have " + numOfTasks + " in the list.");
            } else if(splittedInput[0].equals("deadline")) {
                String tempString = getTaskName(splittedInput);
                String[] splitDate = tempString.split("/by ");
                Task temp = new Deadlines(splitDate[0], splitDate[1]);
                userTasks[numOfTasks] = temp;
                numOfTasks++;
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + temp.toString());
                System.out.println("    Now you have " + numOfTasks + " in the list."); 
            } else if(splittedInput[0].equals("event")) {
                String tempString = getTaskName(splittedInput);
                String[] tempOne = tempString.split(" /to ");
                String[] tempTwo = tempOne[0].split(" /from ");
                String to = tempOne[1];
                String from = tempTwo[1];
                String taskName = tempTwo[0];
                Task temp = new Events(taskName, from, to);
                userTasks[numOfTasks] = temp;
                numOfTasks++;
                System.out.println("    Got it. I've added this task:");
                System.out.println("      " + temp.toString());
                System.out.println("    Now you have " + numOfTasks + " in the list."); 
            }
            else {
                System.out.println("NOT A VALID COMMAND");
            }
            printLine();
            input = sc.nextLine();
            printLine();
            splittedInput = input.split("\\s+");
        }
        sc.close();
    }
}
