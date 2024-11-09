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
    Socket socket;
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
        addEventListeners();
        new Thread(() -> {
            try (Socket socket = new Socket(ip,port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String message;
                while ((message = in.readLine()) != null) {
                    gui.getTextArea().append(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void addEventListeners() {
        gui.getTextField().addActionListener((e) -> {
            sendMessage(gui.getTextField().getText());
        });
    }

    public void sendMessage(String message) {
        try (Socket socket = new Socket(ip,port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String mess = in.readLine();
            System.out.println(mess);

            out.println(message);

            gui.getTextArea().setText(in.lines().collect(Collectors.joining("\n")));


        } catch (Exception e) {
                e.printStackTrace();
        }
    }


}
