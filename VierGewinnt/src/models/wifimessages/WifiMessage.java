package models.wifimessages;

import com.google.gson.Gson;

import controller.client.VierGewinntClientThread;
import controller.server.Server;

import java.lang.reflect.Type;

public class WifiMessage {
    private String sender;
    private String type;

    public String getSender(){
        return sender;
    }

    public String getType() {
        return type;
    }

    public WifiMessage(String sender, String type){
        this.sender=sender;
        this.type=type;
    }

    public static void sendWifiMessageJSONToServer(WifiMessage wifimessage){
        String message=JSONParser.toJSON(wifimessage);

        VierGewinntClientThread client=VierGewinntClientThread.getInstance();
        client.getOutput().println(message);
        client.getOutput().flush();
        
        System.out.println(message);
    }

    public static void sendWifiMessageJSONToClients(WifiMessage wifimessage){
        String message=JSONParser.toJSON(wifimessage);

        Server server=Server.getInstance();
        server.sendMessageToClients(message);
    }

    public static String fromWifMessageToJSON(WifiMessage wifimessage){
        return JSONParser.toJSON(wifimessage);
    }

    public static WifiMessage fromJSONtoWifiMessage(String json, Type type){
        Gson gson = new Gson();
        WifiMessage msg = gson.fromJson(json, type);
        return msg;
    }

}
