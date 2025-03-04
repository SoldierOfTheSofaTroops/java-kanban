package ru.dk;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        taskManager.createTask(new Task("s1", "s1"));
        taskManager.createTask(new Task("s2", "s2"));
        taskManager.createSubtask(new Subtask("s1", "s1"));
        taskManager.createSubtask(new Subtask("s2", "s2"));
        taskManager.createEpic(new Epic("e1", "e1"));
    }
}
