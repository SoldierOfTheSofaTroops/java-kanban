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
     * This test covered save() and toString() methods in FileBackedTaskManager
     * **/
    @Test
    void save() throws IOException {
        ArrayList<String> tasks = new ArrayList<>();
        Task task = new Task(TaskType.TASK, "Task", Status.NEW, "Description task");
        Epic epic = new Epic(TaskType.EPIC, "Epic", Status.DONE, "Description epic");
        Subtask subtask = new Subtask(TaskType.SUBTASK, "Subtask", Status.IN_PROGRESS, "Description subtask", epic);
        fileBackedTaskManager.createTask(task);
        fileBackedTaskManager.createTask(epic);
        fileBackedTaskManager.createTask(subtask);

        bufferedReader.readLine();

        while (bufferedReader.ready()){
            String line = bufferedReader.readLine();
            tasks.add(line);
        }

        assertEquals(tasks.getFirst(), "0,TASK,Task,NEW,Description task");
        assertEquals(tasks.get(1), "1,EPIC,Epic,DONE,Description epic");
        assertEquals(tasks.getLast(), "2,SUBTASK,Subtask,IN_PROGRESS,Description subtask,1");

        bufferedReader.close();
    }

    /**
     * This test covered loadFromFile() and fromString() methods in FileBackedTaskManager
     * **/
    @Test
    void loadFromFileTest() throws IOException {
        File testBackup = new File("./test/resources/file/backup.csv");
        fileBackedTaskManager = FileBackedTaskManager.loadFromFile(testBackup);

        Task testTask = new Task(0,TaskType.TASK, "Task", Status.NEW, "Description task");
        Epic testEpic = new Epic(1,TaskType.EPIC, "Epic", Status.DONE, "Description epic");
        Subtask testSubtask = new Subtask(2,TaskType.SUBTASK,
                                            "Subtask",
                                            Status.IN_PROGRESS,
                                            "Description subtask", testEpic);

        assertEquals(fileBackedTaskManager.getTaskById(0),testTask);
        assertEquals(fileBackedTaskManager.getEpicById(1),testEpic);
        assertEquals(fileBackedTaskManager.getSubtaskById(2),testSubtask);
    }
}