package ru.dk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.dk.entity.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task("Description task 1", "Task-1");
        task2 = new Task("Description task 2", "Task-2");
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
}