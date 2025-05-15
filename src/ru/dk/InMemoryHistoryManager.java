package ru.dk;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private final InnerLinkedList<Task> historyList = new InnerLinkedList<>();
    private final Map<Integer, InnerLinkedList.Node<Task>> historyHashMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (historyHashMap.containsKey(task.getId())) {
            historyList.removeNode(historyHashMap.get(task.getId()));
            historyHashMap.remove(task.getId());
        }
        Task taskShallowCopy = task.clone();
        historyList.linkLast(taskShallowCopy);
        historyHashMap.put(task.getId(), historyList.getTail());
    }

    @Override
    public List<Task> getHistoryList() {
        return new ArrayList<>(historyList.getTasks());
    }

    @Override
    public void remove(int id) {
        if (historyHashMap.containsKey(id)) {
            historyList.removeNode(historyHashMap.get(id));
        }
        historyHashMap.remove(id);
    }

    private static class InnerLinkedList<T extends Task> {

        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public void linkLast(T t) {
            final Node<T> oldTail = tail;
            final Node<T> newTask = new Node<>(oldTail, t, null);
            tail = newTask;
            if (oldTail == null) head = newTask;
            else oldTail.next = newTask;
            size++;
        }

        public Node<T> getTail() {
            return tail;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            if (head != null) {
                Node<T> node = head;
                while (node != null) {
                    tasks.add(node.data);
                    node = node.next;
                }
            }
            return tasks;
        }

        public void removeNode(Node<T> node) {
            if (node.prev == null && node.next == null) {
                head = tail = null;
                size--;
            } else if (node.prev != null && node.next == null) {
                node.prev.next = null;
                tail = node.prev;
                size--;
            } else if (node.prev == null) {
                node.next.prev = null;
                head = node.next;
                size--;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

        }

       private static class Node<T extends Task> {
            public T data;
            public Node<T> prev;
            public Node<T> next;

            public Node(Node<T> prev, T data, Node<T> next) {
                this.data = data;
                this.next = next;
                this.prev = prev;
            }
        }
    }

}