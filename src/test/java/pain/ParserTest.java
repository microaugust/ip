package pain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.beans.Transient;

import javax.management.NotCompliantMBeanException;

public class ParserTest {
    @Test
    public void testParseInputToDo() throws InvalidCommandException, EmptyCommandException, NoCommandException {
        Parser parser = new Parser();
        String[] parsedToDo = {"todo", "smt"};
        assertArrayEquals(parsedToDo, parser.parseInput("todo smt"));
    }

    @Test
    public void testParseInputDeadline() throws InvalidCommandException, EmptyCommandException, NoCommandException {
        Parser parser = new Parser();
        String[] parsedToDo = {"deadline", "smt", "12/12/2012 12:12:12"};
        assertArrayEquals(parsedToDo, parser.parseInput("deadline smt /by 12/12/2012 12:12:12"));
    }

   @Test
    public void testParseInputEvent() throws InvalidCommandException, EmptyCommandException, NoCommandException {
        Parser parser = new Parser();
        String[] parsedToDo = {"event", "smt", "12/12/2012 12:12:12", "01/01/2019 12:12:12"};
        assertArrayEquals(parsedToDo, parser.parseInput("event smt /from 12/12/2012 12:12:12 /to 01/01/2019 12:12:12"));
    } 
}
