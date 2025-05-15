package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    public InMemoryHistoryManager historyManager;
    public InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    void setUpBeforeClass() throws Exception {
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
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
        assertNotNull(historyManager.getHistoryList(), "History shouldn't be null");
    }

    @Test
    void innerLinkedListTest(){
            for (int i = 0; i < 10; i++) {
                inMemoryTaskManager.createTask(new Task("Description " + i, "Task " + i));
                inMemoryTaskManager.getTaskById(i);
            }

        assertEquals(10, inMemoryTaskManager.getHistory().size(), "Incorrect number of tasks");
        inMemoryTaskManager.deleteTaskById(0);
        assertEquals(9, inMemoryTaskManager.getHistory().size(),"Result of deleting first node is incorrect");
        inMemoryTaskManager.deleteTaskById(9);
        assertEquals(8, inMemoryTaskManager.getHistory().size(),"Result of deleting last node is incorrect");
        inMemoryTaskManager.deleteTaskById(4);
        assertEquals(7, inMemoryTaskManager.getHistory().size(),"Result of deleting middle node is incorrect");
    }
}