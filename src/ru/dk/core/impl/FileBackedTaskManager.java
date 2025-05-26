package ru.dk.core.impl;

import ru.dk.abstracts.TaskManager;
import ru.dk.core.exception.ManagerSaveException;
import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;
import ru.dk.entity.Epic;
import ru.dk.entity.Subtask;
import ru.dk.entity.Task;

import java.io.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private File backup;
    private static HashMap<Integer, Integer> linksBetweenSubtasksAndEpics;

    public FileBackedTaskManager() throws IOException {
        String pathname = "./src/ru/dk/resources/file/backup.csv";
        backup = new File(pathname);
        linksBetweenSubtasksAndEpics = new HashMap<>();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        return super.getTaskById(id);
    }

    @Override
    public Task getEpicById(int id) {
        return super.getEpicById(id);
    }

    @Override
    public Task getSubtaskById(int id) {
        return super.getSubtaskById(id);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return super.getEpicSubtasks(epic);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return super.getHistory();
    }

    /**
     * This method save current status of the manager.
     * It uses toString() method which is in this class
     * @throws IOException if errors occur during the writing process to the file
     * @throws ManagerSaveException if errors occur during the writing process to the file
     * @see FileBackedTaskManager#toString(Task)
     * @see ManagerSaveException
     * @since Sprint-7
     * **/
    public void save() {
        BufferedWriter bw;
        try {
             bw = new BufferedWriter(new FileWriter(backup));
             bw.write("id,type,name,status,description,epic\n");
             if (!getAllTasks().isEmpty()){
                 for (Task task : getAllTasks()) {
                     try {
                         bw.write(toString(task));
                         bw.newLine();
                     } catch (IOException e) {
                         throw new ManagerSaveException(e.getMessage());
                     }
                 }
             }
            if (!getAllEpics().isEmpty()){
                for (Epic epic : getAllEpics()) {
                    try {
                        bw.write(toString(epic));
                        bw.newLine();
                    } catch (IOException e) {
                        throw new ManagerSaveException(e.getMessage());
                    }
                }
            }

            if (!getAllSubtasks().isEmpty()){
                for (Subtask subtask : getAllSubtasks()) {
                    try {
                        bw.write(toString(subtask));
                        bw.newLine();
                    } catch (IOException e) {
                        throw new ManagerSaveException(e.getMessage());
                    }
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method returns string representation of the Task (Epic, Subtask).
     * It's use for save tasks (epics, subtasks) to backup.csv file.
     * @param task Task, Epic or Subtask which needs to be saved
     * @see FileBackedTaskManager#save()
     * @since Sprint-7
     * **/
    protected String toString(Task task){
        StringBuilder result = new StringBuilder(String.format("%d,%s,%s,%s,%s",
                                                        task.getId(),
                                                        task.getType(),
                                                        task.getName(),
                                                        task.getStatus(),
                                                        task.getDescription()));
        if (task.getType().equals(TaskType.TASK)
                || task.getType().equals(TaskType.EPIC)){
                    return result.toString();
        } else {
            Subtask subtask = (Subtask) task;
            result.append(",").append(subtask.getEpic().getId());
        }
        return result.toString();
    }

    /**
     * This method converts Tasks (Epic, Subtask) from a string representation.
     * @see FileBackedTaskManager#loadFromFile(File)
     * @throws ClassCastException
     * @since Sprint-7
     * **/
    public Task fromString(String value) {
          String[] parsedString = value.split(",");
          int id = Integer.parseInt(parsedString[0]);
          TaskType type = TaskType.valueOf(parsedString[1]);
          String name = parsedString[2];
          Status status = Status.valueOf(parsedString[3]);
          String description = parsedString[4];
          Task restoredObject = null;

          try{
              if (type.equals(TaskType.SUBTASK)){
                  int epicId = Integer.parseInt(parsedString[5]);
                  FileBackedTaskManager.linksBetweenSubtasksAndEpics.put(id, epicId);
                  restoredObject = new Subtask (id, type, name, status, description);
              } else if (type.equals(TaskType.EPIC)) {
                  restoredObject = new Epic(id, type, name, status, description);
              } else {
                  restoredObject = new Task(id, type, name, status, description);
              }
          } catch (ClassCastException exception){
              exception.printStackTrace();
          }
          return restoredObject;
    }

   /**
    * This method loads string representation Tasks (Epic, Subtasks) from backup.csv file, then converts them to objects.
    * After that restores links between Epics and Subtasks.
    * @throws IOException if errors occur during the reading process from the file
    * @throws ManagerSaveException if errors occur during the reading process from the file
    * @see FileBackedTaskManager#fromString(String)
    * @since Sprint-7
    * **/
   public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        LinkedHashMap<Integer, Task> tempStorage = new LinkedHashMap<>();
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);

       /* Skip the first line */

       /* Reading file, converting from a String to an object, saving object to tempStorage */
       try (bufferedReader) {
           String nextLine;
           bufferedReader.readLine();
           while (bufferedReader.ready()) {
               nextLine = bufferedReader.readLine();
               Task task = fileBackedTaskManager.fromString(nextLine);
               tempStorage.put(task.getId(), task);
           }
       } catch (IOException e) {
           throw new ManagerSaveException(e.getMessage());
       }

       /* Restoring links between Epic and Subtasks */
       linksBetweenSubtasksAndEpics.forEach((subtaskId, epicId) -> {
           Subtask subtask = (Subtask) tempStorage.get(subtaskId);
           Epic epic = (Epic) tempStorage.get(epicId);
           subtask.setEpic(epic);
           epic.addSubtask(subtask);
           tempStorage.put(subtaskId, subtask);
           tempStorage.put(epicId, epic);
       });

        /* Reading tempStorage and updating objects */
           for (Task value : tempStorage.values()) {
               if (value.getType().equals(TaskType.TASK)) {
                   fileBackedTaskManager.updateTask(value);
               }else if (value.getType().equals(TaskType.EPIC)) {
                   fileBackedTaskManager.updateEpic((Epic) value);
               } else if (value.getType().equals(TaskType.SUBTASK)) {
                   fileBackedTaskManager.updateSubtask((Subtask) value);
               }
           }

           return fileBackedTaskManager;
    }
}
