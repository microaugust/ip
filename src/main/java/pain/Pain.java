package pain;
import java.util.Scanner;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Pain{

    static final String PATHNAME = "data/pain.txt";
    static final String NAME = "Pain";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException{
        File data = new File("data");
        File dataText = new File(PATHNAME);
        Storage taskStorage = new Storage(PATHNAME);
        Parser parser = new Parser();
        Ui ui = new Ui(NAME);

        if(!data.exists()) {
            data.mkdirs();
        }
        TaskList taskList;
        if(taskStorage.exists()) {
            taskList = new TaskList(taskStorage.retrieveTask());
        } else {
            taskList = new TaskList();
        }

        ui.startUp();

        while(true) {
            try{
                String[] parsedInput = ui.getAndParseInput(sc, parser);
                switch(parsedInput[0]) {
                case "bye":
                    ui.printOutput(parsedInput, taskList);
                    sc.close();
                    System.exit(0);

                case "list":
                    ui.printOutput(parsedInput, taskList);
                    break;

                case "mark":
                    int taskToMark = Integer.parseInt(parsedInput[1]) - 1;
                    if(taskToMark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToMark).mark();
                    ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "unmark":
                    int taskToUnmark = Integer.parseInt(parsedInput[1]) - 1;
                    if(taskToUnmark >= taskList.size()) {
                        throw new NotInListException();
                    }
                    taskList.get(taskToUnmark).unmark();
                    ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "todo":
                    Task todoTask = new ToDos(parsedInput[1]);
                    taskList.add(todoTask);
                    ui.printOutput(parsedInput, taskList);                    
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "deadline":
                    Task deadlineTask = new Deadlines(parsedInput[1], parsedInput[2]);
                    taskList.add(deadlineTask);
                    ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "event":
                    Task eventTask = new Events(parsedInput[1], parsedInput[2], parsedInput[3]);
                    taskList.add(eventTask);
                    ui.printOutput(parsedInput, taskList);
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                case "delete":
                    int taskToDelete = Integer.parseInt(parsedInput[1]) - 1;
                    if(taskToDelete >= taskList.size()) {
                        throw new NotInListException();
                    }
                    ui.printOutput(parsedInput, taskList);
                    taskList.delete(taskToDelete);                    
                    taskStorage.saveTaskOnHardDisk(taskList);
                    break;

                default:
                    throw new NoCommandException();
                }
            } catch (NoCommandException e) {
                System.out.println("    brotha, what are you typing? i don't understand u");
                ui.printLine();
            } catch (EmptyCommandException e) {
                System.out.println("    ooooooh, this cannot be empty lah");
                ui.printLine();
            } catch (InvalidCommandException e) {
                System.out.println("    aiya recheck whether this correct format or not aah");
                ui.printLine();
            } catch (NotInListException e) {
                System.out.println("    that index not in the list lol");
                ui.printLine();
            } catch (NumberFormatException e) {
                System.out.println("    need to be a number");
                ui.printLine();
            } catch (DateTimeParseException e) {
                System.out.println("    invalid date time format (need to be dd/mm/yyyy hh:mm:ss)");
                ui.printLine();
            }
        }
    }
}