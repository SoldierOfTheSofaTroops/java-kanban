package ru.dk.core.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager fileBackedTaskManager;
    BufferedReader bufferedReader;

    @BeforeEach
    void setUp() throws IOException {
        File backup = new File("./src/ru/dk/resources/file/backup.csv");
        fileBackedTaskManager = FileBackedTaskManager.loadFromFile(backup);
        FileReader reader = new FileReader(backup);
        bufferedReader = new BufferedReader(reader);
    }

    /**
     * This test covers save() and toString() methods in FileBackedTaskManager
     * **/
    @Test
    void save() throws IOException {
        ArrayList<String> tasks = new ArrayList<>();

        Task task = new Task(TaskType.TASK, "Task", Status.NEW, "Description task");
        task.setStartTime(LocalDateTime.of(2025, 6, 15, 0, 0, 0));
        task.setDuration(Duration.ofMinutes(50));

        Epic epic = new Epic(TaskType.EPIC, "Epic", Status.NEW, "Description epic");

        Subtask subtask = new Subtask(TaskType.SUBTASK, "Subtask", Status.IN_PROGRESS, "Description subtask", epic);
        subtask.setStartTime(LocalDateTime.of(2026, 6, 15, 12, 0,0));
        subtask.setDuration(Duration.ofMinutes(10));

        epic.addSubtask(subtask);

        fileBackedTaskManager.createTask(task);
        fileBackedTaskManager.createEpic(epic);
        fileBackedTaskManager.createSubtask(subtask);

        bufferedReader.readLine();

        while (bufferedReader.ready()){
            String line = bufferedReader.readLine();
            tasks.add(line);
        }

        assertEquals(tasks.getFirst(), "0,TASK,Task,NEW,Description task,2025-06-15 00:00:00,50");
        assertEquals(tasks.get(1), "1,EPIC,Epic,NEW,Description epic,2026-06-15 12:00:00,10");
        assertEquals(tasks.getLast(), "2,SUBTASK,Subtask,IN_PROGRESS,Description subtask,2026-06-15 12:00:00,10,1");

        bufferedReader.close();
    }

    /**
     * This test covers loadFromFile() and fromString() methods in FileBackedTaskManager
     * @see FileBackedTaskManager#loadFromFile(File) 
     * @see FileBackedTaskManager#fromString(String)
     * @throws IOException
     **/
    @Test
    void loadFromFileTest() throws IOException {
        File testBackup = new File("./test/resources/file/backup.csv");
        fileBackedTaskManager = FileBackedTaskManager.loadFromFile(testBackup);

        Task testTask = new Task(0, TaskType.TASK, "Task", Status.NEW, "Description task");
        testTask.setStartTime(LocalDateTime.of(2025, 6, 15, 0, 0, 0));
        testTask.setDuration(Duration.ofMinutes(50));

        Epic testEpic = new Epic(1, TaskType.EPIC, "Epic", Status.IN_PROGRESS, "Description epic");

        Subtask testSubtask = new Subtask(2,TaskType.SUBTASK,
                                            "Subtask",
                                            Status.IN_PROGRESS,
                                            "Description subtask", testEpic);
        testSubtask.setStartTime(LocalDateTime.of(2026, 6, 15, 12, 0,0));
        testSubtask.setDuration(Duration.ofMinutes(10));

        testEpic.addSubtask(testSubtask);

        fileBackedTaskManager.createTask(testTask);
        fileBackedTaskManager.createEpic(testEpic);
        fileBackedTaskManager.createSubtask(testSubtask);

        assertEquals(fileBackedTaskManager.getTaskById(0),testTask);
        assertEquals(fileBackedTaskManager.getEpicById(1),testEpic);
        assertEquals(fileBackedTaskManager.getSubtaskById(2),testSubtask);
    }
}