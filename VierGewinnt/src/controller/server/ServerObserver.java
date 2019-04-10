package controller.server;

import java.util.List;

public interface ServerObserver {
    public void onClientConnected(Server.ClientForHost newClient, List<Server.ClientForHost> clients);
}
