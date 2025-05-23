package ru.dk.entity;

import ru.dk.core.types.TaskType;

public class Subtask extends Task{

    private Epic epic;

    public Subtask(String description, String name) {
        super(description, name);
    }

    public Subtask(String description, String name, TaskType type, Epic epic) {
        super(description, name, type);
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