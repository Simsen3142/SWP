package controller.client;

import java.lang.reflect.Modifier;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import models.wifimessages.ClientMessage;
import models.wifimessages.SpielMessage;
import models.wifimessages.UserUIMessage;
import models.wifimessages.WifiMessage;

public class VierGewinntClientThread extends ClientThread {
    private static VierGewinntClientThread instance;
    private String userName;
    
    private static Set<Class<? extends SpielMessage>> spielMessageClasses;
    private static Set<Class<? extends UserUIMessage>> userUiMessageClasses;

    private ArrayList<SpielMessageObserver> spielMessageObservers=new ArrayList<>();
    private ArrayList<UserUiMesssageObserver> ansagenMessageObservers=new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPreferredUserName() {
        //TODO: Username von GUI, etc holen
        return "Simsen";
    }

    public void addSpielMessageObserver(SpielMessageObserver observer) {
        spielMessageObservers.add(observer);
    }

    public void removeSpielMessageObserver(SpielMessageObserver observer) {
        spielMessageObservers.remove(observer);
    }

    public void addAnsagenMessageObserver(UserUiMesssageObserver observer) {
        ansagenMessageObservers.add(observer);
    }

    public void removeAnsagenMessageObserver(UserUiMesssageObserver observer) {
        ansagenMessageObservers.remove(observer);
    }

    public void triggerSpielMessageObservers(SpielMessage message) {
        for (SpielMessageObserver observer : spielMessageObservers)
            observer.onMessageReceived(message);
    }

    public void triggerUiMessageObservers(UserUIMessage message) {
        for (UserUiMesssageObserver observer : ansagenMessageObservers)
            observer.onMessageReceived(message);
    }


    private VierGewinntClientThread(Socket socket) {
        super(socket);
        //TODO: Namen von Einstellungen holen
        setUserName(socket.getInetAddress()+"");
    }

    public static void initClientThread(Socket socket) {
        instance = new VierGewinntClientThread(socket);
        
        //TODO: Mit Reflections
//        if(spielMessageClasses==null)
//        	initReflectionClasses();
    }

    public static VierGewinntClientThread getInstance() {
        return instance;
    }

    public void requestUsername(String userName){
        WifiMessage.sendWifiMessageJSONToServer(new ClientMessage.UsernameRequestMessage(this.getUserName(),userName));
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                String command=null;
                if(getInput()!=null)
                    command = getInput().readLine();
                if (command == null || command.isEmpty()) {
                    Thread.sleep(50);
                    continue;
                }
                JsonObject jobj = new Gson().fromJson(command, JsonObject.class);
                String type = jobj.get("type").getAsString();
                

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
                    triggerSpielMessageObservers(message);
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
                    triggerUiMessageObservers(message);
                } else if (typeComponents[0].equals("client")) {
                    switch (typeComponents[1]) {
                        case "resp":{
                            switch (typeComponents[2]){
                                case "username": {
                                    ClientMessage.UsernameResponseMessage message = (ClientMessage.UsernameResponseMessage) WifiMessage.fromJSONtoWifiMessage(command, ClientMessage.UsernameResponseMessage.class);
                                    setUserName(message.getUsername());
                                    break;
                                }
                            }
                            break;
                        }
                        case "req": {
                            switch (typeComponents[2]) {
                                case "username": {
                                    ClientMessage.UsernameRequestMessage message = (ClientMessage.UsernameRequestMessage) WifiMessage.fromJSONtoWifiMessage(command, ClientMessage.UsernameRequestMessage.class);
                                    requestUsername(getPreferredUserName());
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                switch (command) {
                    case "QUIT":
                        interrupt();
                        return;
                    default:
                        break;
                }
            } catch (SocketException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private static void initReflectionClasses() {
    	spielMessageClasses=new HashSet<>();
    	userUiMessageClasses=new HashSet<>();
		
		Reflections reflections = new Reflections("models.wifimessages");
		reflections.getSubTypesOf(SpielMessage.class).forEach((clss)->{
			if(!Modifier.isAbstract(clss.getModifiers())) {
				spielMessageClasses.add(clss);
			}
		});

		reflections.getSubTypesOf(UserUIMessage.class).forEach((clss)->{
			if(!Modifier.isAbstract(clss.getModifiers())) {
				userUiMessageClasses.add(clss);
			}
		});
    }
}
