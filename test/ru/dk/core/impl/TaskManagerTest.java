package ru.dk.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.abstracts.TaskManager;
import ru.dk.core.Managers;
import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest extends ru.dk.abstracts.TaskManagerTest<TaskManager> {

    private InMemoryTaskManager taskManager;
    private Task testTask0;
    private Task testTask1;
    private Epic testEpic;
    private Epic testEpic2;
    private Subtask testSubtask;

    @BeforeEach
    void setUp() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
        testTask0 = new Task(TaskType.TASK, "Test task-0", Status.NEW, "Test testTask-0 description");
        testTask0.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,0));
        testTask0.setDuration(Duration.ofHours(2));

        testTask1 = new Task(TaskType.TASK, "Test task-1", Status.NEW, "Test testTask-1 description");
        testTask1.setStartTime(LocalDateTime.of(2025, 6, 15, 2,1,1));
        testTask1.setDuration(Duration.ofHours(2));

        testEpic = new Epic(TaskType.EPIC, "Test testEpic", Status.NEW, "Test testEpic description");

        testEpic2 = new Epic(TaskType.EPIC, "Test testEpic2", Status.NEW, "Test testEpic2 description");

        testSubtask = new Subtask(TaskType.SUBTASK, "Test subtask", Status.NEW, "Test subtask", testEpic);
        testSubtask.setStartTime(LocalDateTime.of(2025, 6, 15, 8,0,0));
        testSubtask.setDuration(Duration.ofHours(2));

        testEpic.addSubtask(testSubtask);
    }

    @Test
    void createTask() {
        taskManager.createTask(testTask0);
        assertEquals(taskManager.getTaskById(0), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testTask0);
    }

    @Test
    void createEpic() {
        taskManager.createEpic(testEpic);
        assertEquals(taskManager.getEpicById(0), testEpic);
    }

    @Test
    void createSubtask() {
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getSubtaskById(0), testSubtask);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testSubtask);
    }

    @Test
    void getAllTasks() {
        taskManager.createTask(testTask0);
        assertEquals(taskManager.getAllTasks().size(), 1);
    }

    @Test
    void getAllEpics() {
        taskManager.createEpic(testEpic);
        assertEquals(taskManager.getAllEpics().size(), 1);
    }

    @Test
    void getAllSubtasks() {
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getAllSubtasks().size(), 1);
    }

    @Test
    void deleteAllTasks() {
        taskManager.createTask(testTask0);
        assertEquals(taskManager.getAllTasks().size(), 1);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testTask0);
        taskManager.deleteAllTasks();
        assertEquals(taskManager.getAllTasks().size(), 0);
        assertEquals(taskManager.getPrioritizedTasks().size(), 0);
    }

    @Test
    void deleteAllEpic() {
        taskManager.createEpic(testEpic);
        assertEquals(taskManager.getAllEpics().size(), 1);
        taskManager.deleteAllEpic();
        assertEquals(taskManager.getAllEpics().size(), 0);
    }

    @Test
    void deleteAllSubtasks() {
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getAllSubtasks().size(), 1);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testSubtask);
        taskManager.deleteAllSubtasks();
        assertEquals(taskManager.getAllSubtasks().size(), 0);
        assertEquals(taskManager.getPrioritizedTasks().size(), 0);
    }

    @Test
    void deleteTaskById() {
        taskManager.createTask(testTask0);
        taskManager.createTask(testTask1);
        assertEquals(taskManager.getTaskById(0), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testTask0);
        assertEquals(taskManager.getTaskById(1), testTask1);
        assertEquals(taskManager.getPrioritizedTasks().last(), testTask1);
        taskManager.deleteTaskById(0);
        assertThrows(NullPointerException.class, ()-> taskManager.getTaskById(0));
        assertFalse(() -> taskManager.getPrioritizedTasks().contains(testTask0));
    }

    @Test
    void deleteEpicById() {
        taskManager.createEpic(testEpic);
        assertEquals(taskManager.getEpicById(0), testEpic);
        taskManager.deleteEpicById(0);
        assertFalse(taskManager.getAllEpics().contains(testEpic));
    }

    @Test
    void deleteSubtaskById() {
        taskManager.createEpic(testEpic);
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getSubtaskById(1), testSubtask);
        taskManager.deleteSubtaskById(1);
        assertFalse(taskManager.getAllSubtasks().contains(testSubtask));
    }

    @Test
    void getTaskById() {
        taskManager.createTask(testTask0);
        assertEquals(taskManager.getTaskById(0), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testTask0);
        taskManager.deleteTaskById(0);
        assertThrows(NullPointerException.class, ()-> taskManager.getTaskById(0));
        assertFalse(taskManager.getPrioritizedTasks().contains(testTask0));
    }

    @Test
    void getEpicById() {
        taskManager.createEpic(testEpic);
        assertEquals(taskManager.getEpicById(0), testEpic);
    }

    @Test
    void getSubtaskById() {
        taskManager.createEpic(testEpic);
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getSubtaskById(1), testSubtask);
    }

    @Test
    void updateTask() {
        taskManager.createTask(testTask0);
        taskManager.createTask(testTask1);
        assertEquals(taskManager.getTaskById(0), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().size(), 2);
        assertEquals(taskManager.getTaskById(1), testTask1);
        assertEquals(taskManager.getAllTasks().size(), 2);
        assertEquals(taskManager.getPrioritizedTasks().size(), 2);
        testTask0.setName("Hello world!");
        taskManager.updateTask(testTask0);
        assertEquals(taskManager.getTaskById(0), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().getFirst(), testTask0);
        assertEquals(taskManager.getPrioritizedTasks().size(), 2);
    }

    @Test
    void updateEpic() {
        taskManager.createEpic(testEpic);
        taskManager.createEpic(testEpic2);
        assertEquals(taskManager.getEpicById(0), testEpic);
        assertEquals(taskManager.getEpicById(1), testEpic2);
        testEpic.setDescription("New description for testEpic2");
        taskManager.updateEpic(testEpic);
        assertEquals(taskManager.getEpicById(0).getDescription(), testEpic.getDescription());
    }

    @Test
    void updateSubtask() {
        taskManager.createEpic(testEpic);
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getSubtaskById(1).getName(), testSubtask.getName());
        testSubtask.setName("New name for testSubtask");
        taskManager.updateSubtask(testSubtask);
        assertEquals(taskManager.getSubtaskById(1).getName(), testSubtask.getName());
    }

    @Test
    void getEpicSubtasks() {
        taskManager.createEpic(testEpic);
        taskManager.createSubtask(testSubtask);
        assertEquals(taskManager.getEpicSubtasks(testEpic).getFirst(), testSubtask);
    }

    @Test
    void getHistory() {
        taskManager.createEpic(testEpic);
        taskManager.createSubtask(testSubtask);
        taskManager.createTask(testTask0);
        taskManager.createTask(testTask1);
        taskManager.createEpic(testEpic2);
        taskManager.getEpicById(0);
        taskManager.getSubtaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
        taskManager.getEpicById(4);
        assertEquals(taskManager.getHistory().size(), 5);
        assertInstanceOf(ArrayList.class, taskManager.getHistory());
    }
}
