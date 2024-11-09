package Client;

import Requests.Request;
import Requests.RequestType;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Chat implements Runnable {
    private static int clientIDIncrementor = 1;
    private final int clientID;
    GUI gui;
    InetAddress ip;
    int port;
    String username;

    public Chat(int port, String username) {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            ip = InetAddress.getLoopbackAddress();
        }

        this.clientID = clientIDIncrementor++;
        this.port = port;
        this.username = username;

    }

    public void run() {
        gui = new GUI(username);
        gui.init();

        startServerListenerThread();
        addEventListeners();
    }

    private void startServerListenerThread() {
        new Thread(() -> {
            try (Socket socket = new Socket(ip,port);
                 ObjectOutputStream out = new ObjectOutputStream((socket.getOutputStream()));
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.writeObject(new Request(clientID, RequestType.LISTENING, username, ""));

                String broadcastedMessageFromServer;
                while ((broadcastedMessageFromServer = in.readLine()) != null) {
                    gui.getTextArea().append(broadcastedMessageFromServer + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void addEventListeners() {
        gui.getTextField().addActionListener((e) -> {
            sendMessage(gui.getTextField().getText());
        });
    }

    public void sendMessage(String message) {
        try (Socket socket = new Socket(ip,port);
             ObjectOutputStream out = new ObjectOutputStream((socket.getOutputStream()))) {

            out.writeObject(new Request(clientID, RequestType.MESSAGE, username, message));

        } catch (Exception e) {
                e.printStackTrace();
        }
    }


}
