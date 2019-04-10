package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import controller.server.controller.ServerController;

/**
 * @Important: Server needs controller to be initialized
 */
public class Server {
    private static Server instance;
    private ServerSocket serverSocketThis;
    private static int portnr = 9091;
    private List<ClientForHost> clients = Collections.synchronizedList(new ArrayList<>());
    private WaiterForClients waiterForClients;
    private ServerController controller;
    private List<ServerObserver> observers = new ArrayList<>();

    public List<ClientForHost> getClients() {
        return clients;
    }

    public ServerController getController() {
        return controller;
    }

    public void setController(ServerController controller) {
        this.controller = controller;
    }

    private Server() {
        try {
            initServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
            logThis("Socket", "Socket hatte Fehler");
        }
    }

    public static Server getInstance() {
        if (instance == null)
            instance = new Server();
        if(instance.serverSocketThis ==null || instance.serverSocketThis.isClosed()){
            try {
                instance.initServerSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void initController(ServerController controller) {
        this.controller = controller;
    }

    private void initServerSocket() throws IOException {
        serverSocketThis = new ServerSocket(portnr);
    }

    public void clearServer() {
        for (ClientForHost client : clients) {
            if (client.isAlive()) {
                client.interrupt();
            }
            if (client.output != null) {
                client.output.close();
            }
            if (client.getSocket() != null) {
                try {
                    client.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(clients!=null)
            clients.clear();
        if(observers!=null)
            observers.clear();
        if(controller!=null)
            controller.clear();
        try {
            if (this.getServerSocket() != null) {
                this.getServerSocket().close();
                serverSocketThis=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addServerObserver(ServerObserver observer) {
        observers.add(observer);
    }

    public void removeServerObserver(ServerObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(ClientForHost newClient) {
        for (ServerObserver observer : observers) {
            observer.onClientConnected(newClient, clients);
        }
    }


    public ServerSocket getServerSocket() {
        return serverSocketThis;
    }

    public static int getPortnr() {
        return portnr;
    }

    public void sendMessageToClients(String message, ClientForHost... excldedClients) {
        if (message == null || message.isEmpty())
            return;
        ArrayList<ClientForHost> excludedClients = new ArrayList<>(Arrays.asList(excldedClients));
        for (ClientForHost client : clients) {
            if (excludedClients.contains(client)) {
//                continue;
            }
            client.output.println(message);
        }
    }

    public void sendMessageToClient(String message, ClientForHost client) {
        if (message == null || message.isEmpty())
            return;
        client.output.println(message);
    }

    public void startWaitingForClientsToJoin() {
        stopWaitingForClientsToJoin();
        waiterForClients = new WaiterForClients();
        waiterForClients.start();
    }

    public void stopWaitingForClientsToJoin() {
        if (waiterForClients == null || !waiterForClients.isAlive())
            return;
        waiterForClients.interrupt();
        waiterForClients = null;
    }

    public class WaiterForClients extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    if (serverSocketThis == null)
                        initServerSocket();
                    logThis("Server", "" + serverSocketThis);
                    ClientForHost client = new ClientForHost(serverSocketThis.accept());
                    clients.add(client);

                    notifyObservers(client);
                    logThis("NewClient", client.socket + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ClientForHost extends Thread {
        private Socket socket;
        private BufferedReader input;
        private PrintWriter output;
        private String clientName;

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public ClientForHost(Socket socket) {
            this.socket = socket;
            clientName = socket.getLocalAddress() + "";
            try {
                this.input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                this.output = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException ex) {
            }
        }

        public Socket getSocket() {
            return socket;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    String command = input.readLine();
                    if (command == null || command.isEmpty()) {
                        Thread.sleep(50);
                        continue;
                    }

                    System.out.println("NOTIFYING!!!!");
                    controller.notifyCommandHandlers(command, this);
                    //ZUG IN RUNDE


                } catch (SocketException ex) {
                    logThis("Connection Error", "Socket nicht mehr verbunden");
                    //TODO: Benachrichtigung senden
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientForHost client = (ClientForHost) o;
            return Objects.equals(socket, client.socket) &&
                    Objects.equals(input, client.input) &&
                    Objects.equals(output, client.output) &&
                    Objects.equals(clientName, client.clientName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(socket, input, output, clientName);
        }
    }

    public void logThis(String tag, String text) {
        System.out.println(tag+": "+text);
    }
}