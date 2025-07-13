package ru.dk.core.server.handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.dk.core.server.Endpoint;
import ru.dk.core.server.adapters.LocalDateTimeAdapter;
import ru.dk.core.type.Status;
import ru.dk.core.type.TaskType;
import ru.dk.entity.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    private final Gson gson = gsonBuilder.create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String httpMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        Endpoint endpoint = checkRequest(body, httpMethod, path);

        switch (endpoint){

            case GET_TASKS -> {
                String tasksString = gson.toJson(inMemoryTaskManager.getAllTasks());
                sendText(exchange, tasksString);
            }

            case GET_TASKS_BY_ID -> {
                ArrayList<String> parsedPath = parseEndpointPath(path);
                int id = Integer.parseInt(parsedPath.get(1));
                ArrayList<Task> tasks = inMemoryTaskManager.getAllTasks();
                Task task = tasks.stream().filter(task1 -> task1.getId()==id).findFirst().get();
                sendText(exchange, gson.toJson(task));
            }

            case CREATE_TASK_WITHOUT_ID -> {
                JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
                TaskType taskType = TaskType.valueOf(jsonObject.get("type").getAsString());
                String name = jsonObject.get("name").getAsString();
                Status status = Status.valueOf(jsonObject.get("status").getAsString());
                String description = jsonObject.get("description").getAsString();
                LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Duration duration = Duration.parse(jsonObject.get("duration").getAsString());

                Task task = new Task(description, name, status, taskType, startTime, duration);

                if (inMemoryTaskManager.isTasksOverlap(task)){
                    sendHasOverlaps(exchange, "Task has overlap");
                } else {
                    inMemoryTaskManager.createTask(task);
                    exchange.sendResponseHeaders(201, 0);
                    exchange.close();
                    System.out.println(inMemoryTaskManager.getTaskById(0));
                }
            }

            case UPDATE_TASK -> {
                    JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
                    int id = jsonObject.get("id").getAsInt();
                    TaskType taskType = TaskType.valueOf(jsonObject.get("type").getAsString());
                    String name = jsonObject.get("name").getAsString();
                    Status status = Status.valueOf(jsonObject.get("status").getAsString());
                    String description = jsonObject.get("description").getAsString();
                    LocalDateTime startTime = LocalDateTime.parse(jsonObject.get("startTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    Duration duration = Duration.parse(jsonObject.get("duration").getAsString());

                    Task task = new Task(id, description, name, status, taskType, startTime, duration);

                    inMemoryTaskManager.updateTask(task);
                    exchange.sendResponseHeaders(201, 0);
                    exchange.close();
                }

            case DELETE_TASK -> {
                int id = Integer.parseInt(parseEndpointPath(path).get(1));
                inMemoryTaskManager.deleteTaskById(id);
                sendText(exchange, "Task has been deleted");
                }
            }
        }
    }