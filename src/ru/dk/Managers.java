package ru.dk;

public class Managers{

    public TaskManager getDefault(){
        return null;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}