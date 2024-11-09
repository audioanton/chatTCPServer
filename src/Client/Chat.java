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

        new Thread(() -> {
            System.out.println("starting client listening thread");
            try (Socket socket = new Socket(ip,port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                System.out.println("trying listening connection");
                out.println("listening");
                String message;
                while ((message = in.readLine()) != null) {
                    gui.getTextArea().append("\n" + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        addEventListeners();
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



            out.println(username +": " + message);

//            gui.getTextArea().append("\n" + message);


        } catch (Exception e) {
                e.printStackTrace();
        }
    }


}
