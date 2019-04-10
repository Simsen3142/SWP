package controller.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ClientThread extends Thread {
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getInput() {
        if (socket == null) {
            input = null;
            return null;
        } else {
            if (input == null) {
                try {
                    if(socket.isConnected())
                        input = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return input;
        }
    }

    public void clearClient(){
        if(isAlive())
            interrupt();
        if (output != null) {
            output.close();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PrintWriter getOutput() {
        if (socket == null) {
            output = null;
            return null;
        } else {
            if (output == null) {
                try {
                    if(socket.isConnected())
                        output = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return output;
        }
    }

    protected ClientThread(Socket socket) {
        this.socket = socket;
        //TODO: Namen von Einstellungen holen
    }


    @Override
    public abstract void run();
}
