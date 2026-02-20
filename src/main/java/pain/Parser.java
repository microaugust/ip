package pain;

/**
 * Parses user input into a normalized command array for processing
 *
 * @throws EmptyCommandException If required arguments are missing
 * @throws InvalidCommandException If the command format is invalid
 * @throws NoCommandException If the command word is unrecognized
 */
public class Parser {

    /**
     * Return an Array of String of the input being parsed without any delimiter.
     *
     * @param input String input from the user.
     * @return Array of String of the input parsed without any delimiter.
     * @throws InvalidCommandException If the command content is invalid.
     * @throws EmptyCommandException If the command doesn't contain any input when required.
     * @throws NoCommandException If the command doesn't exist.
     */
    public String[] parseInput(String input) throws InvalidCommandException, EmptyCommandException, NoCommandException, NumberFormatException {
        String[] splitBySpace = input.split("\\s+");
        switch (splitBySpace[0]) {
        case "bye", "list":
            return parseSingleTokenCommand(splitBySpace);
        case "mark", "unmark", "delete":
            return parseSecondTokenIntegerCommand(splitBySpace);
        case "todo":
            return parseToDoCommand(splitBySpace);
        case "deadline":
            return parseDeadlineCommand(splitBySpace, input);
        case "event":
            return parseEventCommand(splitBySpace, input);
        case "find":
            return parseFindCommand(splitBySpace);
        default:
            throw new NoCommandException();
        }
    }

    /**
     * Return a String concatentation of every element except the first element.
     *
     * @param splittedInput Input array that was splitted by white space.
     * @return String concatenation of every element except the first element.
     */
    private static String removeFirstElementAndCombineString(String[] splittedInput) {
        String task = "";
        for (int i = 1; i < splittedInput.length; i++) {
            task += (splittedInput[i] + " ");
        }
        return task.substring(0, task.length() - 1);
    }

    private static String[] parseSingleTokenCommand(String[] tokens) throws InvalidCommandException {
        if (tokens.length > 1) {
            throw new InvalidCommandException();
        }
        return tokens;
    }

    private static String[] parseSecondTokenIntegerCommand(String[] tokens) throws EmptyCommandException, InvalidCommandException, NumberFormatException {
        if (tokens.length == 1) {
            throw new EmptyCommandException();
        } else if (tokens.length > 2) {
            throw new InvalidCommandException();
        }
        Integer.parseInt(tokens[1]); 
        return tokens;
    }

    private static String[] parseToDoCommand(String[] tokens) throws EmptyCommandException {
        if (tokens.length == 1) {
            throw new EmptyCommandException();
        }
        String taskName = removeFirstElementAndCombineString(tokens);
        String[] todoParsed = {tokens[0], taskName};
        return todoParsed;
    }

    private static String[] parseDeadlineCommand(String[] tokens, String input) throws EmptyCommandException, InvalidCommandException {
        if (tokens.length == 1) {
            throw new EmptyCommandException();
        } else if ((!input.contains(" /by "))) {
            throw new InvalidCommandException();
        }
        String deadlineName = removeFirstElementAndCombineString(tokens);
        String[] splitDate = deadlineName.split(" /by ");
        String[] deadlineParsed = {tokens[0], splitDate[0], splitDate[1]}; 
        return deadlineParsed;
    }

    private static String[] parseEventCommand(String[] tokens, String input) throws EmptyCommandException, InvalidCommandException {
        if (tokens.length == 1) {
            throw new EmptyCommandException();
        } else if ((!input.contains(" /from ")) || (!input.contains(" /to "))) {
            throw new InvalidCommandException();
        }
        String inputRemovedEvent = removeFirstElementAndCombineString(tokens);
        String[] tempOne = inputRemovedEvent.split(" /to ");
        String[] tempTwo = tempOne[0].split(" /from ");
        String toDate = tempOne[1];
        String fromDate = tempTwo[1];
        String eventName = tempTwo[0];
        String[] eventParsed = {tokens[0], eventName, fromDate, toDate};
        return eventParsed;
    }

    private static String[] parseFindCommand(String[] tokens) throws EmptyCommandException, InvalidCommandException {
        if (tokens.length == 1) {
            throw new EmptyCommandException();
        } else if (tokens.length > 2) {
            throw new InvalidCommandException();
        }
        String[] findParsed = {tokens[0], tokens[1]};
        return findParsed;
    }
}
