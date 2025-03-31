package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    void setUpBeforeClass() throws Exception {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        historyManager.add(new Task("Task description", "Task-1"));
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "History shouldn't be null");
        assertFalse(history.isEmpty(), "History shouldn't be empty");
    }

    @Test
    void getHistory() {
        assertNotNull(historyManager.getHistory(), "History shouldn't be null");
    }

    @Test
    void savePreviousTaskVersionAfterAddToHistory(){
        Task task = new Task("Initial Task description", "Initial name");
        historyManager.add(task);
        task.setDescription("New description");
        task.setStatus(Status.DONE);
        task.setName("New name");
        assertNotEquals(historyManager.getHistory().get(0).getName(), task.getName(), "Names are equal");
        assertNotEquals(historyManager.getHistory().get(0).getDescription(), task.getDescription(), "Description are equal");
        assertNotEquals(historyManager.getHistory().get(0).getStatus(), task.getStatus(), "Status are equal");
    }
}