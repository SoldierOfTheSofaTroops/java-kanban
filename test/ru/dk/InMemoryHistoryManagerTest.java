package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.core.impl.InMemoryHistoryManager;
import ru.dk.core.impl.InMemoryTaskManager;
import ru.dk.core.Managers;
import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;
import ru.dk.entity.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    public InMemoryHistoryManager historyManager;
    public InMemoryTaskManager inMemoryTaskManager;
    private Task testTask0;
    private Task testTask1;
    private Task testTask2;

    @BeforeEach
    void setUpBeforeClass() throws Exception {
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();

        testTask0 = new Task(TaskType.TASK, "Test task-0", Status.NEW, "Test testTask-0 description");
        testTask0.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,0));
        testTask0.setDuration(Duration.ofHours(2));

        testTask1 = new Task(TaskType.TASK, "Test task-1", Status.NEW, "Test testTask-1 description");
        testTask1.setStartTime(LocalDateTime.of(2025, 6, 15, 3,1,1));
        testTask1.setDuration(Duration.ofHours(2));

        testTask2 = new Task(TaskType.TASK, "Test task-2", Status.NEW, "Test testTask-2 description");
        testTask2.setStartTime(LocalDateTime.of(2025, 6, 15, 10,1,1));
        testTask2.setDuration(Duration.ofHours(2));
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

    @Test
    void emptyHistory() {
        ArrayList<Task> history = inMemoryTaskManager.getHistory();
        assertEquals(0, history.size());
    }

    @Test
    public void duplicationOfTasks(){
        inMemoryTaskManager.createTask(testTask0);
        inMemoryTaskManager.createTask(testTask1);
        inMemoryTaskManager.getTaskById(0);
        inMemoryTaskManager.getTaskById(1);
        assertEquals(inMemoryTaskManager.getHistory().getFirst(), testTask0);
        assertEquals(inMemoryTaskManager.getHistory().getLast(), testTask1);
        inMemoryTaskManager.getTaskById(0);
        assertEquals(inMemoryTaskManager.getHistory().getLast(), testTask0);
        assertEquals(inMemoryTaskManager.getHistory().size(), 2);
    }

    @Test
    public void removeFromHistory(){
        inMemoryTaskManager.createTask(testTask0);
        inMemoryTaskManager.createTask(testTask1);
        inMemoryTaskManager.createTask(testTask2);
        inMemoryTaskManager.getTaskById(0);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        assertEquals(inMemoryTaskManager.getHistory().size(), 3);

        //remove from head
        inMemoryTaskManager.deleteTaskById(0);
        assertEquals(inMemoryTaskManager.getHistory().size(), 2);
        assertFalse(inMemoryTaskManager.getHistory().contains(testTask0));

        inMemoryTaskManager.createTask(testTask0);
        inMemoryTaskManager.getTaskById(3);
        assertEquals(inMemoryTaskManager.getHistory().size(), 3);
        assertEquals(inMemoryTaskManager.getHistory().get(2), testTask0);

        //remove between
        inMemoryTaskManager.deleteTaskById(2);
        assertEquals(inMemoryTaskManager.getHistory().size(), 2);
        assertTrue(inMemoryTaskManager.getHistory().contains(testTask1));
    }
}