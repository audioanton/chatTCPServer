package Client;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class Chat implements Runnable {
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
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out.println("--JOIN_REQUEST-- " + username);
                String serverResponse = in.readLine();
                gui.getTextArea().append(serverResponse + "\n");
                String message;
                while ((message = in.readLine()) != null) {
                    gui.getTextArea().append(message + "\n");
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
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(username +": " + message);

        } catch (Exception e) {
                e.printStackTrace();
        }
    }


}
