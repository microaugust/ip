import java.util.Scanner;

public class Pain {

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }

    public static void main(String[] args) {
        printLine();
        System.out.println("    Nihao! I'm Pain");
        System.out.println("    Yo want you want");
        printLine();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        printLine();
        while(!input.equals("bye")) {
            System.out.println("    " + input);
            printLine();
            input = sc.nextLine();
            printLine();
        }
        System.out.println("    Bye. I will definitely see you again");
        printLine();
    }
}
