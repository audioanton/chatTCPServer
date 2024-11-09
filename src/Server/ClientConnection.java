package Server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Socket socket;
    Database database;
    Server server;

    public ClientConnection(Socket socket, Database database, Server server) {
        this.socket = socket;
        this.database = database;
        this.server = server;
    }

    @Override
    public void run() {
        initializeClientConnection();
    }

    public void initializeClientConnection() {
        System.out.println(socket.getInetAddress().getHostAddress());
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println("testing");





                out.println("Connection Established.");
                String incomingMessage = in.readLine();
                database.addMessage(incomingMessage);
                out.println(database.getMessages());

                server.broadcast(incomingMessage);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        System.out.println("Got here");
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
