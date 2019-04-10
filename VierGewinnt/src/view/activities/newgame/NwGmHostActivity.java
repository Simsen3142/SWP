package view.activities.newgame;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import controller.client.VierGewinntClientThread;
import controller.server.Server;
import controller.server.ServerObserver;
import controller.server.controller.VierGewinntServerController;
import controller.server.handler.ClientHandler;
import view.MainActivity;
import view.activities.CustomAppCompatActivity;

public class NwGmHostActivity extends CustomAppCompatActivity {

    private Server server;
    private int amtPlayers;
    private Client4HostListView clientListView;

    @Override
    public void onCreate(Object... messages) {
        server = Server.getInstance();
        server.initController(new VierGewinntServerController(server));

        
        //TODO: CHANGE HARDCODED ZUWEISUNG
        amtPlayers = 2;

        System.out.println("Intent: AmtPlayers = " + amtPlayers);

        clientListView = new Client4HostListView();
        this.add(clientListView);
        createConnection();
    }

    @Override
    public void onDestroy() {
        server.stopWaitingForClientsToJoin();
        super.onDestroy();
    }

    public void createConnection() {
        server.clearServer();
        server.startWaitingForClientsToJoin();

        server.addServerObserver(new JoinedToServerObserver());

        Socket socket = new Socket();
        VierGewinntClientThread.initClientThread(socket);
        try {
            socket.connect(new InetSocketAddress("127.0.0.1", Server.getPortnr()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        VierGewinntClientThread.getInstance().start();
    }


    private class JoinedToServerObserver implements ServerObserver {
        @Override
        public void onClientConnected(Server.ClientForHost newClient, List<Server.ClientForHost> clients) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    clientListView.initClientListView(clients);
                }
            }).start();
            if (clients.size() == amtPlayers) {
                server.stopWaitingForClientsToJoin();
                startGame();

            }
        }
    }

    public void startGame() {
        for (Server.ClientForHost client : server.getClients()) {
            client.start();
        }

        ClientHandler handler=new ClientHandler();
        Server.getInstance().getController().addServerCommandHandler(handler);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime=System.currentTimeMillis();
                while (System.currentTimeMillis()-startTime<2000 && handler.anyUserNamesToBeAltered()){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                List<String> names=handler.getUsernames();
				//TODO: CHANGE!
                startActivity(MainActivity.getInstance());
            }
        }).start();
    }

}
