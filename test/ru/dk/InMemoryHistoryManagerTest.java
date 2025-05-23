package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.core.impl.InMemoryHistoryManager;
import ru.dk.core.impl.InMemoryTaskManager;
import ru.dk.core.Managers;
import ru.dk.entity.Task;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    public InMemoryHistoryManager historyManager;
    public InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    void setUpBeforeClass() throws Exception {
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
    }

    @Test
    void add() {
        Task task1 = new Task("Task 1 description", "Task 1");
        Task task2 = new Task("Task 2 description", "Task 2");
        Task task3 = new Task("Task 3 description", "Task 3");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createTask(task3);
        inMemoryTaskManager.getTaskById(0);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        ArrayList<Task> history = inMemoryTaskManager.getHistory();
        assertEquals(3, history.size());
    }

    @Test
    void getHistory() {
        assertNotNull(historyManager.getHistory(), "History shouldn't be null");
    }

    @Test
    void linkLast(){
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            Task task = new Task("Task " + i + " description", "Task "+i);
            inMemoryTaskManager.createTask(task);
        }
        for (int n = 0; n < 10000; n++) {
            inMemoryTaskManager.getTaskById(random.nextInt(10000));
        }

        ArrayList<Task> history = inMemoryTaskManager.getHistory();

        for (Task task : history) {
            assertNotEquals(2, history.stream().filter(t -> t.getId() == task.getId()).count());
        }
    }
}