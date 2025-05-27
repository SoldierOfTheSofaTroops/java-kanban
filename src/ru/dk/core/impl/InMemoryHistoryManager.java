package ru.dk.core.impl;

import ru.dk.abstracts.HistoryManager;
import ru.dk.entity.Node;
import ru.dk.entity.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> history = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task task) {
        Task taskShallowCopy = task.clone();
        if (history.isEmpty()) {
            linkFirst(new Node<>(null, taskShallowCopy, null));
            } else
                linkLast(new Node<>(null, taskShallowCopy, null));
    }

    public void linkLast(Node<Task> node) {
        if (history.containsKey(node.getData().getId())) {
            removeNode(node);
            if (history.isEmpty()){
                linkFirst(node);
            } else checkTailAndLink(node);
        } else checkTailAndLink(node);
    }

    public void removeNode(Node<Task> node) {
        if (head.getData().getId() == node.getData().getId()){
            if (head.getNext() != null){
                head = head.getNext();
                history.remove(node.getData().getId());
            } else {
                history.remove(node.getData().getId());
                head = null;
            }
        } else if(tail.getData().getId() == node.getData().getId()) {
            tail = tail.getPrev();
            history.remove(node.getData().getId());
        }else {
            Node<Task> removableNode = history.get(node.getData().getId());
            Node<Task> prevNode = removableNode.getPrev();
            Node<Task> nextNode = removableNode.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        Node<Task> removableNode  = history.get(id);
        removeNode(removableNode);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<Task> node = head;
        while (node != null) {
            tasks.add(node.getData());
            node = node.getNext();
        }
        return tasks;
    }

    private void linkFirst(Node<Task> firstNode) {
        head = firstNode;
        history.put(firstNode.getData().getId(), firstNode);
    }

    private void checkTailAndLink(Node<Task> node){
        if (tail == null){
            tail = node;
            tail.setPrev(head);
            head.setNext(node);
            history.put(node.getData().getId(), node);
        } else {
            history.put(node.getData().getId(), node);
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
    }
    }