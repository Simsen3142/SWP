package controller.server.handler;

import controller.server.Server;
import models.Spiel;
import models.wifimessages.SpielMessage;
import models.wifimessages.UserUIMessage;
import models.wifimessages.WifiMessage;

public class SpielServerHandler extends ServerCommandHandler {
    private Spiel spiel;
    private boolean forward = true;
    private boolean forwardToAll = false;

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public SpielServerHandler(Spiel spiel) {
        this.spiel = spiel;
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
    public String handleCommand(String command, String type, Server.ClientForHost clientThread) {
        String response = "";
        forward = true;
        forwardToAll = false;

        type = type.toLowerCase();
        String[] typeComponents = type.split("_");
        if (typeComponents[0].equals("game")) {
            SpielMessage message = null;
            switch (typeComponents[1]) {
                case "back":
                    message = (SpielMessage.BackMessage) WifiMessage.fromJSONtoWifiMessage(command, SpielMessage.BackMessage.class);
                    break;
                case "forward":
                    message = (SpielMessage.ForwardMessage) WifiMessage.fromJSONtoWifiMessage(command, SpielMessage.ForwardMessage.class);
                    break;
                case "reset":
                    message = (SpielMessage.ResetMessage) WifiMessage.fromJSONtoWifiMessage(command, SpielMessage.ResetMessage.class);
                    break;
                case "add":
                    message = (SpielMessage.AddChipMessage) WifiMessage.fromJSONtoWifiMessage(command, SpielMessage.AddChipMessage.class);
                    break;
                case "remove":
                    message = (SpielMessage.RemoveChipMessage) WifiMessage.fromJSONtoWifiMessage(command, SpielMessage.RemoveChipMessage.class);
                    break;
            }
        } else if (typeComponents[0].equals("ui")) {
            UserUIMessage message = null;
            switch (typeComponents[1]) {
                case "back":
                    message = (UserUIMessage.BackMessage) WifiMessage.fromJSONtoWifiMessage(command, UserUIMessage.BackMessage.class);
                    break;
                case "forward":
                    message = (UserUIMessage.ForwardMessage) WifiMessage.fromJSONtoWifiMessage(command, UserUIMessage.ForwardMessage.class);
                    break;
                case "reset":
                    message = (UserUIMessage.ResetMessage) WifiMessage.fromJSONtoWifiMessage(command, UserUIMessage.ResetMessage.class);
                    break;
                case "clicked":
                    message = (UserUIMessage.ColumnClickedMessage) WifiMessage.fromJSONtoWifiMessage(command, UserUIMessage.ColumnClickedMessage.class);
                    break;
                case "selected":
                    message = (UserUIMessage.ColumnSelectedMessage) WifiMessage.fromJSONtoWifiMessage(command, UserUIMessage.ColumnSelectedMessage.class);
                    break;
            }
        }else {
            forward=false;
        }

//        switch (command) {
//            case "QUIT":
//                clientThread.interrupt();
//                break;
//            default:
//                break;
//        }

        return response;
    }

    @Override
    public void initialize(Server server) {
    }
}
