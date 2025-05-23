package ru.dk.core;

import ru.dk.abstracts.HistoryManager;
import ru.dk.Status;
import ru.dk.abstracts.TaskManager;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

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
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics(){
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks(){
        return new ArrayList<>(subtasks.values());
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
        historyManager.remove(id);
    }

    @Override
    public void deleteEpicById(int id){
        epics.get(id).getSubtasks().clear();
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id){
        int epicId = subtasks.get(id).getEpic().getId();
        subtasks.remove(id);
        updateEpicStatus(epics.get(epicId));
        historyManager.remove(id);
    }

    @Override
    public Task getTaskById(int id){
        Task task = tasks.get(id);
        historyManager.add(task);
        return tasks.get(id);
    }

    @Override
    public Task getEpicById(int id){
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Task getSubtaskById(int id){
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
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
    public ArrayList<Task> getHistory(){
        return (ArrayList<Task>) historyManager.getHistory();
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