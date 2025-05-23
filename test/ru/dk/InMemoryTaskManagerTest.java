package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.abstracts.TaskManager;
import ru.dk.core.impl.InMemoryTaskManager;
import ru.dk.core.types.Status;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void createTask() {
        Task task = new Task("Task description", "Task name");
        taskManager.createTask(task);
        final int taskId = taskManager.getTaskById(0).getId();
        Task savedTask = taskManager.getTaskById(taskId);
        assertNotNull(savedTask, "Task not found");
        assertEquals(task, savedTask, "Tasks not equal");
    }

    @Test
    void createEpic() {
        Epic epic = new Epic("Epic description", "Epic name");
        taskManager.createEpic(epic);
        final int epicId = taskManager.getEpicById(0).getId();
        Epic savedEpic = (Epic) taskManager.getEpicById(epicId);
        assertNotNull(savedEpic, "Epic not found");
        assertEquals(epic, savedEpic, "Epics not equal");
    }

    @Test
    void createSubtask() {
        Subtask subtask = new Subtask("Subtask description", "Subtask name");
        taskManager.createSubtask(subtask);
        final int subtaskId = taskManager.getSubtaskById(0).getId();
        Subtask savedSubtask = (Subtask) taskManager.getSubtaskById(subtaskId);
        assertNotNull(savedSubtask, "Subtask not found");
        assertEquals(subtask, savedSubtask, "Subtasks not equal");
    }

    @Test
    void taskManagerCanAddAllTypeTasksAndCanFindById(){
        assertDoesNotThrow(() -> taskManager.createTask(new Task("Task description", "Task name")));
        assertDoesNotThrow(() -> taskManager.createEpic(new Epic("Epic description", "Epic name")));
        assertDoesNotThrow(() -> taskManager.createSubtask(new Subtask("Subtask description", "Subtask name")));
        assertNotNull(taskManager.getTaskById(0), "Task not found");
        assertNotNull(taskManager.getEpicById(1), "Epic not found");
        assertNotNull(taskManager.getSubtaskById(2), "Subtask not found");
        taskManager.createTask(new Epic("Description for test epic", "Test epic"));
    }

    @Test
    void idConflictTest(){
        Task task1 = new Task("Task description", "Task name");
        Task task2 = new Task("Task description", "Task name");
        task2.setId(0);
        assertDoesNotThrow(() -> taskManager.createTask(task1), "An error occurred while creating task");
        assertDoesNotThrow(() -> taskManager.createTask(task2), "An error occurred while creating task");
    }

    @Test
    void immutabilityOfTheTaskTest(){
        Task task1 = new Task("Task description", "Task name");

        task1.setStatus(Status.IN_PROGRESS);
        taskManager.createTask(task1);
        assertEquals(task1.getDescription(), taskManager.getTaskById(0).getDescription(), "Task description not equal");
        assertEquals(task1.getName(), taskManager.getTaskById(0).getName(), "Task name not equal");
        assertEquals(task1.getStatus(), taskManager.getTaskById(0).getStatus(), "Task status not equal");

        Epic epic = new Epic("Epic description", "Epic name");
        taskManager.createEpic(epic);
        assertEquals(epic.getDescription(), taskManager.getEpicById(1).getDescription(), "Epic description not equal");
        assertEquals(epic.getName(), taskManager.getEpicById(1).getName(), "Epic name not equal");
        assertEquals(epic.getStatus(), taskManager.getEpicById(1).getStatus(), "Epic status not equal");

        Subtask subtask = new Subtask("Subtask description", "Subtask name");
        taskManager.createSubtask(subtask);
        assertEquals(subtask.getDescription(), taskManager.getSubtaskById(2).getDescription(), "Subtask description not equal");
        assertEquals(subtask.getName(), taskManager.getSubtaskById(2).getName(), "Subtask name not equal");
        assertEquals(subtask.getStatus(), taskManager.getSubtaskById(2).getStatus(), "Subtask status not equal");
    }

    @Test
    void getHistory(){
        ArrayList<Task> history = taskManager.getHistory();
        assertNotNull(history, "Returned history is null");
    }
}