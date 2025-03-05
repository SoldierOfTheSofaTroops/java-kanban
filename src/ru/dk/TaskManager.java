package ru.dk;

import java.util.*;

public class TaskManager {
    private int id = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();

    public void createTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic){
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask){
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
    }

    public ArrayList<Task> getAllTasks(){
        return (ArrayList<Task>) tasks.values();
    }

    public ArrayList<Epic> getAllEpics(){
        return (ArrayList<Epic>) epics.values();
    }

    public ArrayList<Subtask> getAllSubtasks(){
        return (ArrayList<Subtask>) subtasks.values();
    }

    public void deleteAllTasks(){
        tasks.clear();
    }

    public void deleteAllEpic(){
        subtasks.clear();
        epics.clear();
    }

    public void deleteAllSubtasks(){
        epics.values().forEach(epic -> {
                        epic.getSubtasks().clear();
                        updateEpicStatus(epic);
        });
        subtasks.clear();
    }

    public void deleteTaskById(int id){
        tasks.remove(id);
    }

    public void deleteEpicById(int id){
        epics.get(id).getSubtasks().clear();
        epics.remove(id);
    }

    public void deleteSubtaskById(int id){
        int epicId = subtasks.get(id).getEpic().getId();
        subtasks.remove(id);
        updateEpicStatus(epics.get(epicId));
    }

    public Task getTaskById(int id){
        return tasks.get(id);
    }

    public Task getEpicById(int id){
        return epics.get(id);
    }

    public Task getSubtaskById(int id){
        return subtasks.get(id);
    }

    public void updateTask(Task task){
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic){
        updateEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask){
        updateEpicStatus(subtask.getEpic());
        subtasks.put(subtask.getId(), subtask);
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic){
        return epic.getSubtasks();
    }

    private void updateEpicStatus(Epic epic){
                boolean isAllSubtasksDone = epic.getSubtasks()
                                                .stream()
                                                .anyMatch(subtask -> subtask.getStatus().equals(Status.NEW)
                                                        || subtask.getStatus().equals(Status.IN_PROGRESS));
                if (epic.getSubtasks().isEmpty() && epic.getStatus() != Status.NEW){
                    epic.setStatus(Status.NEW);
                } else if (!isAllSubtasksDone){
                    epic.setStatus(Status.DONE);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
    }

    private int generateId(){
        return id++;
    }
}