package ru.dk.core.impl;

import ru.dk.abstracts.HistoryManager;
import ru.dk.abstracts.TaskManager;
import ru.dk.core.Managers;
import ru.dk.core.type.Status;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private TreeSet<Task> prioritizedTasks = new TreeSet<>();

    /**
     * This method register new task in the Task manager.
     * Before a task is registered,it is checked for time overlap with existing tasks.
     * After verification, the task is added to the prioritized task list.
     * @see Task
     * @see InMemoryTaskManager#isTasksOverlap(Task)
     * @see InMemoryTaskManager#prioritizedTasks
     * **/
    @Override
    public void createTask(Task task){
        if (!isTasksOverlap(task)){
            task.setId(generateId());
            tasks.put(task.getId(), task);
            if (task.getStartTime() != null){
                prioritizedTasks.add(task);
            }
        }
    }

    @Override
    public void createEpic(Epic epic){
            setEpicStartEndTimeAndDuration(epic);
            epic.setId(generateId());
            epics.put(epic.getId(), epic);
    }

    /**
     * This method register new subtask in the Task manager.
     * Before a subtask is registered, it is checked for time overlap with existing tasks and subtasks.
     * After verification, the subtask is added to the prioritized task list.
     * @see Task
     * @see Subtask
     * @see InMemoryTaskManager#isTasksOverlap(Task)
     * @see InMemoryTaskManager#prioritizedTasks
     * **/
    @Override
    public void createSubtask(Subtask subtask){
        if (!isTasksOverlap(subtask)){
            subtask.setId(generateId());
            subtasks.put(subtask.getId(), subtask);
            if (subtask.getStartTime() != null){
                prioritizedTasks.add(subtask);
            }
        }
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
        prioritizedTasks = prioritizedTasks.stream()
                .filter(task -> !(task instanceof Task))
                .collect(Collectors.toCollection(TreeSet::new));
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
        prioritizedTasks = prioritizedTasks
                .stream()
                .filter(task -> !(task instanceof Subtask))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public void deleteTaskById(int id){
        prioritizedTasks.remove(tasks.get(id));
        this.tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpicById(int id){
        prioritizedTasks.remove(epics.get(id));
        epics.get(id).getSubtasks().clear();
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id){
        int epicId = subtasks.get(id).getEpic().getId();
        prioritizedTasks.remove(subtasks.get(id));
        subtasks.remove(id);
        updateEpicStatus(epics.get(epicId));
        historyManager.remove(id);
    }

    @Override
    public Task getTaskById(int id){
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
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
        if (!isTasksOverlap(task)){
            tasks.put(task.getId(), task);
            if (task.getStartTime() != null){
                prioritizedTasks.add(task);
            }
        }
    }

    @Override
    public void updateEpic(Epic epic){
            updateEpicStatus(epic);
            epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask){
        if (isTasksOverlap(subtask)){
            updateEpicStatus(subtask.getEpic());
            subtasks.put(subtask.getId(), subtask);
            if (subtask.getStartTime() != null){
                prioritizedTasks.add(subtask);
            }
        }
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic){
        return epic.getSubtasks();
    }

    @Override
    public ArrayList<Task> getHistory(){
        return (ArrayList<Task>) historyManager.getHistory();
    }

    /**
     * This method returns sorted subtasks of epic (TreeSet).
     * Subtasks sorted from later to earlier subtask
     * @see Task#compareTo(Task)
     **/
    public TreeSet<Task> getPrioritizedTasks(){
        return prioritizedTasks;
    }

    /**
     * This method performs a time intersection check for all types of tasks.
     * @since Sprint-8
     * **/
    public boolean isTasksOverlap(Task task){
        if (!prioritizedTasks.isEmpty()){
            Predicate<Task> taskPredicate = (currentTask) ->
                    !task.getStartTime().isAfter(currentTask.getEndTime())
                            && !currentTask.getStartTime().isAfter(task.getEndTime());
            return prioritizedTasks.stream().anyMatch(taskPredicate);
        }
        return false;
    }

/**
 * This method calculate epic startTime, endTime and duration.
 * Epic startTime is the first subtask startTime.
 * Epic endTime is the last subtask endTime.
 * Epic duration it is the sum of all subtasks durations.
 * @see Task
 * @see Subtask
 * @see Epic
 * @since Sprint-8
 * **/
    private void setEpicStartEndTimeAndDuration(Epic epic){
        if (!epic.getSubtasks().isEmpty()){
            TreeSet<Subtask> epicPrioritizedTasks = new TreeSet<>(epic.getSubtasks());
            epic.setStartTime(epicPrioritizedTasks.first().getStartTime());
            epic.setEndTime(epicPrioritizedTasks.last().getEndTime());
            epic.setDuration(epicPrioritizedTasks.parallelStream()
                    .map(Subtask::getDuration)
                    .reduce(Duration.ZERO, Duration::plus));
        }

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

    /**
     * This method generates new id for each new Task, Subtask, Epic
     * @see Task
     * @see Subtask
     * @see Epic
     * @since Sprint-5
     * **/
    private int generateId(){
        return id++;
    }
}