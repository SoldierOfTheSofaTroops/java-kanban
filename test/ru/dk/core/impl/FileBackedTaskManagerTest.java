package ru.dk.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.core.types.Status;
import ru.dk.core.types.TaskType;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    void setUp() {
        fileBackedTaskManager = new FileBackedTaskManager();
    }

    @Test
    void save() {
    }

    @Test
    void testToString() {

        /* Assert for TaskType.TASK & TaskType.EPIC*/
        Task testTask = new Task("Test task description", "Test task", TaskType.TASK);
        testTask.setId(0);
        testTask.setStatus(Status.NEW);
        assertEquals("0,TASK,Test task,NEW,Test task description", fileBackedTaskManager.toString(testTask));

        /* Assert for TaskType.SUBTASK */
        Epic testEpic_0 = new Epic("Test epic description",
                                        "Test epic",
                                        TaskType.EPIC);
        testEpic_0.setId(100);
        testEpic_0.setStatus(Status.NEW);
        assertEquals("100,EPIC,Test epic,NEW,Test epic description", fileBackedTaskManager.toString(testEpic_0));

        /* Assert for TaskType.SUBTASK */
        Subtask testSubtask_1_ForEpic_0 = new Subtask("Test subtask_1 description",
                                                        "Test subtask_1",
                                                        TaskType.SUBTASK,
                                                        testEpic_0);
        Subtask testSubtask_2_ForEpic_0 = new Subtask("Test subtask_2 description",
                                                        "Test subtask_2",
                                                        TaskType.SUBTASK,
                                                        testEpic_0);
        testSubtask_1_ForEpic_0.setId(1);
        testSubtask_1_ForEpic_0.setStatus(Status.NEW);
        testSubtask_2_ForEpic_0.setId(2);
        testSubtask_2_ForEpic_0.setStatus(Status.DONE);
        testEpic_0.addSubtask(testSubtask_1_ForEpic_0);
        testEpic_0.addSubtask(testSubtask_2_ForEpic_0);
        assertEquals("1,SUBTASK,Test subtask_1,NEW,Test subtask_1 description,100",
                            fileBackedTaskManager.toString(testSubtask_1_ForEpic_0));
        assertEquals("2,SUBTASK,Test subtask_2,DONE,Test subtask_2 description,100",
                            fileBackedTaskManager.toString(testSubtask_2_ForEpic_0));
    }

    @Test
    void fromString() {

    }

    @Test
    void loadFromFile() {
    }
}