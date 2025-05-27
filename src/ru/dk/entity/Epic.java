package ru.dk.entity;

import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(String description, String name) {
        super(description, name);
    }

    public Epic(TaskType type,String name,Status status,String description) {
        super(type, name, status, description);
    }

    public Epic(int id, TaskType type, String name, Status status, String description) {
        super(id, type, name, status, description);
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void addSubtask(Subtask subtask){
        subtasks.put(subtask.getId(), subtask);
        subtask.setEpic(this);
    }

    @Override
    public String toString() {
        return "\n\tType: Epic "  + "\n"
                + "\tID: " + this.getId() + "\n"
                + "\tName: " + this.getName() + "\n"
                + "\tDescription: " + this.getDescription() + "\n"
                + "\tStatus: " + this.getStatus() + "\n"
                + "\tSubtasks: " + this.subtasks.size() + "\n";
    }
}