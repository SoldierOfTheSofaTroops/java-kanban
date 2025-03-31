package ru.dk;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.createTask(new Task("Description task 1", "Task 1"));
        taskManager.createTask(new Task("Description task 2", "Task 2"));
        taskManager.createTask(new Task("Description task 3", "Task 3"));
        taskManager.createTask(new Task("Description task 4", "Task 4"));
        taskManager.createTask(new Task("Description task 5", "Task 5"));
        Epic epic = new Epic("Description epic 1","Epic 1");
        taskManager.createTask(epic);

        taskManager.getTaskById(2);
        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubtasks(epic)) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
