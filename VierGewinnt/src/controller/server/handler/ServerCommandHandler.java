package controller.server.handler;

import controller.server.Server;
import controller.server.controller.ServerController;

public abstract class ServerCommandHandler {
    protected ServerController controller;

    public void setController(ServerController controller) {
        this.controller = controller;
    }

    public abstract boolean doForward();
    public abstract boolean doForwardToAll();
    public abstract String handleCommand(String command,String type, Server.ClientForHost clientThread);

    public abstract void initialize(Server server);
}
