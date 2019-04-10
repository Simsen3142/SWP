package controller.server.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import controller.server.Server;
import controller.server.handler.ServerCommandHandler;

public abstract class ServerController {
    private Server server;
    private List<ServerCommandHandler> handlers = new ArrayList<>();

    public ServerController(Server server){
        this.server=server;
    }

    public void addServerCommandHandler(ServerCommandHandler handler) {
        handler.setController(this);
        handlers.add(handler);
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.initialize(server);
            }
        }).start();
    }

    public List<ServerCommandHandler> getServerCommandHandlers(){
        return handlers;
    }

    public void removeServerCommandHandler(ServerCommandHandler handler) {
        handlers.remove(handler);
        handler.setController(null);
    }

    public void clear(){
        handlers.clear();
    }

    public void notifyCommandHandlers(String message, Server.ClientForHost client){
        JsonObject jobj = new Gson().fromJson(message, JsonObject.class);
        String type = jobj.get("type").getAsString();

        for (ServerCommandHandler commandHandler : getServerCommandHandlers()) {
            String response = commandHandler.handleCommand(message,type, client);
            server.sendMessageToClient(response,client);
            if (commandHandler.doForward()) {
                if(commandHandler.doForwardToAll()){
                    server.sendMessageToClients(message);
                }else{
                    server.sendMessageToClients(message, client);
                }
            }
        }
    }
}
