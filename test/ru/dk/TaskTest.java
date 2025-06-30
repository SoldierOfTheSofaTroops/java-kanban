package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.entity.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        task1 = new Task("Description task 1", "Task-1");
        task2 = new Task("Description task 2", "Task-2");
        task3 = new Task("Description task 3", "Task-3");
    }

    @Test
    void testEquals() {
        task2.setId(task1.getId());
        assertEquals(task1, task2, "Tasks are not equal");
    }

    @Test
    void getEndTime(){
        DateTimeFormatter TASK_START_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Duration taskDuration = Duration.ofHours(12).plusMinutes(30).plusSeconds(30);
        task1.setStartTime(LocalDateTime.parse("2025-06-14 00:00:00", TASK_START_TIME));
        task1.setDuration(taskDuration);
        LocalDateTime taskEndTime = task1.getEndTime();
        assertEquals(taskEndTime, LocalDateTime.parse("2025-06-14 12:30:30", TASK_START_TIME), "Task end time is incorrect");
        assertEquals(taskEndTime, LocalDateTime.of(2025, 6, 14, 12, 30, 30), "Task end time is incorrect");
    }

    @Test
    void compareTo(){
        TreeSet<Task> taskSet = new TreeSet<>();
        task1.setStartTime(LocalDateTime.of(2025, 6, 14, 0, 0, 0));
        task2.setStartTime(LocalDateTime.of(2025, 6, 14, 0, 0, 1));
        task3.setStartTime(LocalDateTime.of(2025, 6, 14, 0, 0, 2));
        taskSet.add(task1);
        taskSet.add(task2);
        taskSet.add(task3);
        assertTrue(task1.compareTo(task2) < 0);
        assertTrue(task2.compareTo(task3) < 0);
        assertEquals(task3, taskSet.first());
        assertEquals(task1, taskSet.last());
    }
}