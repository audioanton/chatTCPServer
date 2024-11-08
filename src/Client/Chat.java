package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Chat implements Runnable {
    GUI gui;
    InetAddress ip;
    int port;
    Socket socket;

    public Chat(int port, String username) {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            ip = InetAddress.getLoopbackAddress();
        }

        this.port = port;
        gui = new GUI(username);
        gui.init();
    }

    public void connectToServer() {
        try (Socket socket = new Socket(ip,port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            gui.getTextArea().append(in.readLine());

            gui.getTextField().addActionListener(e -> {
                out.println(gui.getTextField().getText());

                try {
                    gui.getTextArea().setText(in.readLine());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

             } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public void run() {
        connectToServer();
    }
}
