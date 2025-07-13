package ru.dk.entity;

import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Comparable<Task> {
    private int id;
    private String description;
    private String name;
    private Status status;
    private TaskType type;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String description, String name) {
        this.description = description;
        this.name = name;
        this.status = Status.NEW;
        this.type = TaskType.TASK;
    }

    public Task(TaskType type, String name, Status status, String description) {
        this.description = description;
        this.name = name;
        this.type = type;
        this.status = status;
    }

    public Task(int id, TaskType type, String name, Status status, String description) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.status = status;
        this.type = type;
    }

    public Task(String description, String name, Status status, TaskType type, LocalDateTime startTime, Duration duration) {
        this.description = description;
        this.name = name;
        this.status = status;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(int id, String description, String name, Status status, TaskType type, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.status = status;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status taskStatus) {
        this.status = taskStatus;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * This method returns an end time (LocalDateTime) for task and subtask
     * @since Sprint-8
     **/
    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\n\tType: Task "  + "\n"
                + "\tID: " + this.id + "\n"
                + "\tName: " + this.name + "\n"
                + "\tDescription: " + this.description + "\n"
                + "\tStatus: " + this.status + "\n"
                + "\tStart time: " + this.startTime + "\n"
                + "\tDuration: " + this.duration + "\n"
                + "\tEnd time: " + this.getEndTime() + "\n";
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Task o) {
        if (this.startTime.isAfter(o.getStartTime())){
            return 1;
        } else if (this.startTime.isBefore(o.getStartTime())){
            return -1;
        } else return 0;
    }
}

