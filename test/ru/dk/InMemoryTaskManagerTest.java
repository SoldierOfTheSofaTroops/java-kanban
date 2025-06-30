package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.core.Managers;
import ru.dk.core.impl.InMemoryTaskManager;
import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager taskManager;

    @BeforeEach
    public void setUp() throws Exception {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }

    @Test
    public void getPrioritizedTasks(){
        Task testTask0 = new Task(0, TaskType.TASK, "Test task-0 description", Status.NEW, "Test task-0 name");
        testTask0.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,0));
        testTask0.setDuration(Duration.ofMinutes(5));
        Task testTask1 = new Task(1, TaskType.TASK, "Test task-1 description", Status.NEW, "Test task-1 name");
        testTask1.setStartTime(LocalDateTime.of(2025, 6, 15, 0,6,0));
        testTask1.setDuration(Duration.ofMinutes(5));
        Task testTask2 = new Task(2, TaskType.TASK, "Test task-2 description", Status.NEW, "Test task-2 name");
        testTask2.setStartTime(LocalDateTime.of(2025, 6, 15, 0,12,0));
        testTask2.setDuration(Duration.ofMinutes(5));
        Task testTask3 = new Task(3, TaskType.TASK, "Test task-3 description", Status.NEW, "Test task-3 name");
        testTask3.setStartTime(LocalDateTime.of(2025, 6, 15, 0,18,0));
        testTask3.setDuration(Duration.ofMinutes(5));
        Task testTask4 = new Task(4, TaskType.TASK, "Test task-4 description", Status.NEW, "Test task-4 name");
        testTask4.setStartTime(LocalDateTime.of(2025, 6, 15, 0,24,0));
        testTask4.setDuration(Duration.ofMinutes(5));

        Subtask testSubtask5 = new Subtask(5, TaskType.SUBTASK, "Test subtask-5 description", Status.NEW, "Test subtask-5 name");
        testSubtask5.setStartTime(LocalDateTime.of(2025, 6, 15, 0,30,5));
        Subtask testSubtask6 = new Subtask(6, TaskType.SUBTASK, "Test subtask-6 description", Status.NEW, "Test subtask-6 name");
        testSubtask6.setStartTime(LocalDateTime.of(2025, 6, 15, 0,36,6));
        Subtask testSubtask7 = new Subtask(7, TaskType.SUBTASK, "Test subtask-7 description", Status.NEW, "Test subtask-7 name");
        testSubtask7.setStartTime(LocalDateTime.of(2025, 6, 15, 0,42,7));
        Subtask testSubtask8 = new Subtask(8, TaskType.SUBTASK, "Test subtask-8 description", Status.NEW, "Test subtask-8 name");
        testSubtask8.setStartTime(LocalDateTime.of(2025, 6, 15, 0,48,8));
        Subtask testSubtask9 = new Subtask(9, TaskType.SUBTASK, "Test subtask-9 description", Status.NEW, "Test subtask-9 name");
        testSubtask9.setStartTime(LocalDateTime.of(2025, 6, 15, 0,54,9));
        Subtask testSubtask10 = new Subtask(10, TaskType.SUBTASK, "Test subtask-10 description", Status.NEW, "Test subtask-10 name");
        testSubtask10.setStartTime(LocalDateTime.of(2025, 6, 15, 0,59,10));
        testSubtask5.setDuration(Duration.ofMinutes(5));
        testSubtask6.setDuration(Duration.ofMinutes(5));
        testSubtask7.setDuration(Duration.ofMinutes(5));
        testSubtask8.setDuration(Duration.ofMinutes(5));
        testSubtask9.setDuration(Duration.ofMinutes(5));
        testSubtask10.setDuration(Duration.ofMinutes(5));
        Epic testEpic11 = new Epic(11, TaskType.EPIC, "Test epic 11 description", Status.NEW, "Test epic 11 name");

        testEpic11.addSubtask(testSubtask5);
        testEpic11.addSubtask(testSubtask6);
        testEpic11.addSubtask(testSubtask7);
        testEpic11.addSubtask(testSubtask8);
        testEpic11.addSubtask(testSubtask9);
        testEpic11.addSubtask(testSubtask10);

        taskManager.createTask(testTask0);
        taskManager.createTask(testTask1);
        taskManager.createTask(testTask2);
        taskManager.createTask(testTask3);
        taskManager.createTask(testTask4);
        taskManager.createSubtask(testSubtask5);
        taskManager.createSubtask(testSubtask6);
        taskManager.createSubtask(testSubtask7);
        taskManager.createSubtask(testSubtask8);
        taskManager.createSubtask(testSubtask9);
        taskManager.createSubtask(testSubtask10);
        taskManager.createEpic(testEpic11);

        TreeSet<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
        assertEquals(testTask0, prioritizedTasks.first());
        assertEquals(testSubtask10, prioritizedTasks.last());
    }

    @Test
    public void setEpicStartEndTimeAndDuration(){
        Subtask testSubtask5 = new Subtask(5, TaskType.SUBTASK, "Test subtask-5 description", Status.NEW, "Test subtask-5 name");
        testSubtask5.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,5));
        Subtask testSubtask6 = new Subtask(6, TaskType.SUBTASK, "Test subtask-6 description", Status.NEW, "Test subtask-6 name");
        testSubtask6.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,6));
        Subtask testSubtask7 = new Subtask(7, TaskType.SUBTASK, "Test subtask-7 description", Status.NEW, "Test subtask-7 name");
        testSubtask7.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,7));
        Subtask testSubtask8 = new Subtask(8, TaskType.SUBTASK, "Test subtask-8 description", Status.NEW, "Test subtask-8 name");
        testSubtask8.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,8));
        Subtask testSubtask9 = new Subtask(9, TaskType.SUBTASK, "Test subtask-9 description", Status.NEW, "Test subtask-9 name");
        testSubtask9.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,9));
        Subtask testSubtask10 = new Subtask(10, TaskType.SUBTASK, "Test subtask-10 description", Status.NEW, "Test subtask-10 name");
        testSubtask10.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,10));
        testSubtask5.setDuration(Duration.ofMinutes(10));
        testSubtask6.setDuration(Duration.ofMinutes(10));
        testSubtask7.setDuration(Duration.ofMinutes(10));
        testSubtask8.setDuration(Duration.ofMinutes(10));
        testSubtask9.setDuration(Duration.ofMinutes(10));
        testSubtask10.setDuration(Duration.ofMinutes(9));
        Epic testEpic11 = new Epic(11, TaskType.EPIC, "Test epic 11 description", Status.NEW, "Test epic 11 name");

        testEpic11.addSubtask(testSubtask5);
        testEpic11.addSubtask(testSubtask6);
        testEpic11.addSubtask(testSubtask7);
        testEpic11.addSubtask(testSubtask8);
        testEpic11.addSubtask(testSubtask9);
        testEpic11.addSubtask(testSubtask10);

        taskManager.createEpic(testEpic11);

        assertEquals(Duration.ofMinutes(59), testEpic11.getDuration());
        assertEquals(LocalDateTime.of(2025, 6, 15, 0,0,5), testEpic11.getStartTime());
        assertEquals(LocalDateTime.of(2025, 6, 15, 0,9,10), testEpic11.getEndTime());
    }

    @Test
    public void isTasksOverlap(){
        Task testTask0 = new Task(0, TaskType.TASK, "Test task-0 description", Status.NEW, "Test task-0 name");
        testTask0.setStartTime(LocalDateTime.of(2025, 6, 15, 0,0,0));
        testTask0.setDuration(Duration.ofMinutes(9));

        Task testTask1 = new Task(1, TaskType.TASK, "Test task-1 description", Status.NEW, "Test task-1 name");
        testTask1.setStartTime(LocalDateTime.of(2025, 6, 15, 0,23,1));
        testTask1.setDuration(Duration.ofMinutes(10));

        Task testTask2 = new Task(2, TaskType.TASK, "Test task-2 description", Status.NEW, "Test task-2 name");
        testTask2.setStartTime(LocalDateTime.of(2025, 6, 15, 0,22,2));
        testTask2.setDuration(Duration.ofMinutes(10));

        Task testTask3 = new Task(3, TaskType.TASK, "Test task-3 description", Status.NEW, "Test task-3 name");
        testTask3.setStartTime(LocalDateTime.of(2025, 6, 15, 0,33,3));
        testTask3.setDuration(Duration.ofMinutes(10));

        Task testTask4 = new Task(4, TaskType.TASK, "Test task-4 description", Status.NEW, "Test task-4 name");
        testTask4.setStartTime(LocalDateTime.of(2025, 6, 15, 0,44,3));
        testTask4.setDuration(Duration.ofMinutes(10));

        taskManager.createTask(testTask2);
        taskManager.createTask(testTask3);
        taskManager.createTask(testTask4);

        assertFalse(taskManager.isTasksOverlap(testTask0));
        assertTrue(taskManager.isTasksOverlap(testTask1));
    }
}