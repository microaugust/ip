package pain;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Parser {

    /**
     * Return a String concatentation of every element except the first element.
     * 
     * @param splittedInput Input array that was splitted by white space.
     * @return String concatenation of every element except the first element.
     */
    public static String removeFirstElementAndCombineString(String[] splittedInput) {
        String task = "";
        for(int i = 1; i < splittedInput.length; i++) {
            task += (splittedInput[i] + " ");
        }
        return task.substring(0, task.length() - 1);
    }

    /**
     * Return an Array of String of the input being parsed without any delimiter.
     * 
     * @param input String input from the user.
     * @return Array of String of the input parsed without any delimiter.
     * @throws InvalidCommandException If the command content is invalid.
     * @throws EmptyCommandException If the command doesn't contain any input when required.
     * @throws NoCommandException If the command doesn't exist.
     */
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
