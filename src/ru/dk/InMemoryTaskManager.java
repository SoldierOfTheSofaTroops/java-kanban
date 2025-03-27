package ru.dk;

import java.util.ArrayList;

public interface InMemoryTaskManager {
    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    void deleteAllTasks();

    void deleteAllEpic();

    void deleteAllSubtasks();

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    Task getTaskById(int id);

    Task getEpicById(int id);

    Task getSubtaskById(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    ArrayList<Subtask> getEpicSubtasks(Epic epic);

    void updateEpicStatus(Epic epic);

    int generateId();
}
