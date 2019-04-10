package controller.server.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.server.Server;
import models.wifimessages.ClientMessage;
import models.wifimessages.WifiMessage;

public class ClientHandler extends ServerCommandHandler {
    private transient boolean forward = true;
    private transient boolean forwardToAll = false;
    private List<Server.ClientForHost> userNamesToBeAltered;

    public ClientHandler() {
        userNamesToBeAltered=Collections.synchronizedList(new ArrayList<>(Server.getInstance().getClients()));
    }

    public boolean anyUserNamesToBeAltered(){
        if(userNamesToBeAltered==null)
            return false;
        return userNamesToBeAltered.size()>0;
    }

    public void initDefaultUserNames(){

    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> userNames=new ArrayList<>();
        for(Server.ClientForHost client:Server.getInstance().getClients()){
            userNames.add(client.getClientName());
        }
        return userNames;
    }

    @Override
    public boolean doForward() {
        return forward;
    }

    @Override
    public boolean doForwardToAll() {
        return forwardToAll;
    }

    @Override
    public String handleCommand(String command,String type, Server.ClientForHost clientThread) {
        String response = "";
        forward = true;
        forwardToAll = false;

        type = type.toLowerCase();
        String[] typeComponents = type.split("_");

        if (typeComponents[0].equals("client")) {
            switch (typeComponents[1]) {
                case "req": {
                    switch (typeComponents[2]) {
                        case "username": {
                            ClientMessage.UsernameRequestMessage message = (ClientMessage.UsernameRequestMessage) WifiMessage.fromJSONtoWifiMessage(command, ClientMessage.UsernameRequestMessage.class);
                            String username = message.getUsername();
                            ArrayList<String> userNames=getUsernames();
                            if (userNames.contains(username)) {
                                for (int i = 0; true; i++) {
                                    if (!userNames.contains(username + "_" + i)) {
                                        username += "_" + i;
                                        break;
                                    }
                                }
                            }
                            clientThread.setClientName(username);
                            if(userNamesToBeAltered!=null && userNamesToBeAltered.size()>0){
                                userNamesToBeAltered.remove(clientThread);
                            }
                            if(!anyUserNamesToBeAltered())
                                userNamesToBeAltered=null;
                            forward=false;
                            return WifiMessage.fromWifMessageToJSON(new ClientMessage.UsernameResponseMessage("HOST",username));
                        }
                    }
                    break;
                }
                case "resp": {
                    break;
                }
            }
        }else {
            forward=false;
        }

        return response;
    }

    @Override
    public void initialize(Server server) {
        WifiMessage.sendWifiMessageJSONToClients(new ClientMessage.UsernameRequestMessage("HOST",""));
    }
}
