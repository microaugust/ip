package pain;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Parser {

    public static String removeFirstElementAndCombineString(String[] splittedInput) {
        String task = "";
        for(int i = 1; i < splittedInput.length; i++) {
            task += (splittedInput[i] + " ");
        }
        return task.substring(0, task.length() - 1);
    }

    public String[] parseInput(String input) throws InvalidCommandException, EmptyCommandException, NoCommandException {
        String[] splitBySpace = input.split("\\s+"); 
            switch (splitBySpace[0]){
            case "bye":
            case "list":
                if(splitBySpace.length > 1) {
                    throw new InvalidCommandException();
                }
                return splitBySpace;
            case "mark":
            case "unmark":
            case "delete":
                if(splitBySpace.length == 1) {
                    throw new EmptyCommandException();
                } else if (splitBySpace.length > 2) {
                    throw new InvalidCommandException();
                } 
                Integer.parseInt(splitBySpace[1]);
                return splitBySpace;
            case "todo":
                if(splitBySpace.length == 1) {
                    throw new EmptyCommandException();
                } 
                String taskName = removeFirstElementAndCombineString(splitBySpace);
                String[] todoParsed = {splitBySpace[0], taskName};
                return todoParsed;
            case "deadline":
                if(splitBySpace.length == 1) {
                    throw new EmptyCommandException();
                } else if((!input.contains(" /by "))) {
                    throw new InvalidCommandException();
                }
                String deadlineName = removeFirstElementAndCombineString(splitBySpace);
                String[] splitDate = deadlineName.split(" /by ");
                String[] deadlineParsed  = {splitBySpace[0], splitDate[0], splitDate[1]};
                return deadlineParsed;
            case "event":
                if(splitBySpace.length == 1) {
                    throw new EmptyCommandException();
                } else if((!input.contains(" /from ")) || (!input.contains(" /to "))) {
                    throw new InvalidCommandException();
                }
                String inputRemovedEvent = removeFirstElementAndCombineString(splitBySpace);
                String[] tempOne = inputRemovedEvent.split(" /to ");
                String[] tempTwo = tempOne[0].split(" /from ");
                String toDate = tempOne[1];
                String fromDate = tempTwo[1];
                String eventName = tempTwo[0];
                String[] eventParsed = {splitBySpace[0], eventName, fromDate, toDate};
                return eventParsed;
            default:
                throw new NoCommandException();
            }
        //CATCH A VALUEERROR FOR PARSING INTEGER
    }
}
