package ru.dk.core.impl;

import ru.dk.abstracts.TaskManager;
import ru.dk.core.types.TaskType;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.io.File;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        return super.getTaskById(id);
    }

    @Override
    public Task getEpicById(int id) {
        return super.getEpicById(id);
    }

    @Override
    public Task getSubtaskById(int id) {
        return super.getSubtaskById(id);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return super.getEpicSubtasks(epic);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return super.getHistory();
    }

    public void save(){

    }

    public String toString(Task task){
        StringBuilder result = new StringBuilder(String.format("%d,%s,%s,%s,%s",
                                                        task.getId(),
                                                        task.getType(),
                                                        task.getName(),
                                                        task.getStatus(),
                                                        task.getDescription()));
        if (task.getType().equals(TaskType.TASK)
                || task.getType().equals(TaskType.EPIC)){
                    return result.toString();
        } else {
            Subtask subtask = (Subtask) task;
            result.append(",").append(subtask.getEpic().getId());
        }
        return result.toString();
    }

    public Task fromString(String value){

        return null;
    }

    static FileBackedTaskManager loadFromFile(File file){
        return null;
    }

}
