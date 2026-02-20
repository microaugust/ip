package pain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.Transient;

import javax.management.NotCompliantMBeanException;

public class TaskListTest {
    @Test
    public void testAdd() {
        TaskList taskList = new TaskList();
        taskList.add(new ToDos("smt"));
        assertEquals(taskList.size(), 1);
    }

    @Test
    public void testRemove() {
        TaskList taskList = new TaskList();
        taskList.add(new ToDos("smt"));
        taskList.add(new ToDos("smt2"));
        taskList.delete(1);
        assertEquals(taskList.size(), 1);
    }
}
