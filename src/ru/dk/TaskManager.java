package ru.dk;

import java.util.*;

public class TaskManager implements InMemoryTaskManager {
    private int id = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();

    @Override
    public void createTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic){
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask){
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public ArrayList<Task> getAllTasks(){
        return (ArrayList<Task>) tasks.values();
    }

    @Override
    public ArrayList<Epic> getAllEpics(){
        return (ArrayList<Epic>) epics.values();
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks(){
        return (ArrayList<Subtask>) subtasks.values();
    }

    @Override
    public void deleteAllTasks(){
        tasks.clear();
    }

    @Override
    public void deleteAllEpic(){
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks(){
        epics.values().forEach(epic -> {
                        epic.getSubtasks().clear();
                        updateEpicStatus(epic);
        });
        subtasks.clear();
    }

    @Override
    public void deleteTaskById(int id){
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id){
        epics.get(id).getSubtasks().clear();
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id){
        int epicId = subtasks.get(id).getEpic().getId();
        subtasks.remove(id);
        updateEpicStatus(epics.get(epicId));
    }

    @Override
    public Task getTaskById(int id){
        return tasks.get(id);
    }

    @Override
    public Task getEpicById(int id){
        return epics.get(id);
    }

    @Override
    public Task getSubtaskById(int id){
        return subtasks.get(id);
    }

    @Override
    public void updateTask(Task task){
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic){
        updateEpicStatus(epic);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask){
        updateEpicStatus(subtask.getEpic());
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic){
        return epic.getSubtasks();
    }

    @Override
    public void updateEpicStatus(Epic epic){
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

    @Override
    public int generateId(){
        return id++;
    }

    public List<Task> getHistory(){

    }
}