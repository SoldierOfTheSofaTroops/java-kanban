package ru.dk;

import java.util.Objects;

public class Task implements Cloneable {
    private int id;
    private String description;
    private String name;
    private Status status;

    public Task(String description, String name) {
        this.description = description;
        this.name = name;
        this.status = Status.NEW;
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

