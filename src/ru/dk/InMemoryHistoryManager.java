package ru.dk;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> watchedTasks = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null){
            Task tmpTask = new Task(task.getDescription(), task.getName());
            tmpTask.setStatus(task.getStatus());
            tmpTask.setId(task.getId());
            if (watchedTasks.size() < 10){
                watchedTasks.add(tmpTask);
            } else {
                watchedTasks.remove(0);
                watchedTasks.add(tmpTask);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return watchedTasks;
    }
}
