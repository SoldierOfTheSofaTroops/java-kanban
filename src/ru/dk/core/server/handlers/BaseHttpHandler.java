package ru.dk.core.server.handlers;

import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import ru.dk.core.Managers;
import ru.dk.core.impl.InMemoryTaskManager;
import ru.dk.core.server.Endpoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseHttpHandler {

    protected InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
        System.out.println("Response: sent " + resp.length + " bytes");
    }

    protected void sendNotFound(HttpExchange h, String json) throws IOException {
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(404, json.length());
        h.getResponseBody().write(json.length());
        h.close();
    }

    protected void sendHasOverlaps(HttpExchange h, String json) throws IOException {
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(406, json.length());
        h.getResponseBody().write(json.length());
        h.close();
    }

    /**
     * Accepts HttpExchange.
     * Retrieves the request path.
     * Returns a parsed string filtered for empty elements.  **/
    protected ArrayList<String> parseEndpointPath(String path) throws IOException {
        return Stream.of(path.split("/"))
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected Endpoint checkRequest(String body, String method, String path) throws IOException {

        Endpoint endpoint = null;
        ArrayList<String> parsedEndpointPath = parseEndpointPath(path);

        try{
            if (method.equals("GET")) {
                if (parsedEndpointPath.size() == 1 && parsedEndpointPath.getFirst().equals("tasks")) {
                    endpoint = Endpoint.GET_TASKS;
                } else if (parsedEndpointPath.size() == 2 && parsedEndpointPath.getFirst().equals("tasks")) {
                    Integer.parseInt(parsedEndpointPath.get(1));
                    endpoint = Endpoint.GET_TASKS_BY_ID;
                }
            } else if (method.equals("POST") && parsedEndpointPath.get(0).equals("tasks")) {
                boolean isIdExist = JsonParser.parseString(body).getAsJsonObject().has("id");
                if (isIdExist) {
                    endpoint = Endpoint.UPDATE_TASK;
                } else {
                    endpoint = Endpoint.CREATE_TASK_WITHOUT_ID;
                }
            } else if (method.equals("DELETE") && parsedEndpointPath.get(0).equals("tasks")) {
                endpoint = Endpoint.DELETE_TASK;
            }
        } catch (NumberFormatException exc){
            System.out.println("Id is incorrect. Id value is not a number");
        }
        return endpoint;
    }
}