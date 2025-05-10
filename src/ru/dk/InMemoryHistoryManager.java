package ru.dk;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> watchedTasks = new ArrayList<>();
    private HashMap<Integer, Task> nodes = new HashMap<>();
    private HashMap<Integer, Task> newWatchedTasks = new HashMap<>();

    /**
     *<p>This method adds all types of tasks to history</p>
     *<p>Deep copying is implemented in the method</p>
     * @param task All types of tasks (see the {@link ru.dk.Task}
     * {@link ru.dk.Subtask} {@link ru.dk.Epic} classes)
     * */
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
    public void remove(int id) {
        newWatchedTasks.remove(id);
    }

    public void removeNode(Node<Task> node) {

    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(nodes.values());
    }
}