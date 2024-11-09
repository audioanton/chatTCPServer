package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    Database database;
    int port;
    Socket socket;
    List<ClientConnection> clients;

    public Server(int port) {
        database = new Database();
        this.port = port;
        clients = new ArrayList<>();
    }

    public void run() {
        startServer();
    }

    public void broadcast(String message) {
        for (ClientConnection client : clients) {
            client.sendMessage(message);
        }
    }

    public void startServer() {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server started");
            while (true) {
                socket = ss.accept();
                new Thread(new ClientConnection(socket, database, this)).start();
            }

        } catch (IOException e) {
            System.out.println("Server stopped");
            throw new RuntimeException(e);
        }
    }

}
