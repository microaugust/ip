import java.util.Scanner;

public class Pain {

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    public static void main(String[] args) {
        Task userTasks = new Task();
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
                System.out.println("    " + userTasks.toString());
            } else if(splittedInput[0].equals("mark")) {
                int taskNum = Integer.parseInt(splittedInput[1]);
                userTasks.mark(taskNum);
            }else if(splittedInput[0].equals("unmark")) {
                int taskNum = Integer.parseInt(splittedInput[1]);
                userTasks.unmark(taskNum);
            }
            else {
                userTasks.add(input);
            }
            printLine();
            input = sc.nextLine();
            splittedInput = input.split("\\s+");
        }
        sc.close();
    }
}
