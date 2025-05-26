package ru.dk.entity;

import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;

public class Subtask extends Task{

    private Epic epic;

    public Subtask(String description, String name) {
        super(description, name);
    }

    public Subtask(TaskType type, String name, Status status, String description, Epic epic) {
        super(type, name, status, description);
        this.epic = epic;
    }

    public Subtask(int id, TaskType type, String name, Status status, String description) {
        super(id, type, name, status, description);
    }

    public Subtask(int id, TaskType type, String name, Status status, String description, Epic epic) {
        super(id, type, name, status, description);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "\n\tType: Subtask "  + "\n"
                        + "\tID: " + this.getId() + "\n"
                        + "\tName: " + this.getName() + "\n"
                        + "\tDescription: " + this.getDescription() + "\n"
                        + "\tStatus: " + this.getStatus() + "\n"
                        + "\tEpic: " + this.epic.getName() + "\n";
    }
}