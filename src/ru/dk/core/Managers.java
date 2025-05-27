package ru.dk.core;

import ru.dk.abstracts.HistoryManager;
import ru.dk.abstracts.TaskManager;
import ru.dk.core.impl.InMemoryHistoryManager;
import ru.dk.core.impl.InMemoryTaskManager;

public class Managers{

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}