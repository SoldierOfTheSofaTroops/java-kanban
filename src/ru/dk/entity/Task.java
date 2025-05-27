package ru.dk.entity;

import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;

import java.util.Objects;

public class Task implements Cloneable {
    private int id;
    private String description;
    private String name;
    private Status status;
    private TaskType type;

    public Task(String description, String name) {
        this.description = description;
        this.name = name;
        this.status = Status.NEW;
        this.type = TaskType.TASK;
    }

    public Task(TaskType type, String name, Status status,String description) {
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
                + "\tStatus: " + this.status + "\n";
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

