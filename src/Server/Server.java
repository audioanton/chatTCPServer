package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    Database database;
    int port;
    Socket socket;

    public Server(int port) {
        database = new Database();
        this.port = port;
    }

    public void run() {
        startServer();
    }

    public void startServer() {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server started");
            while (true) {

                try {
                    socket = ss.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client connected");
                new Thread(new ClientConnection(socket, database)).start();
//                try (Socket socket = ss.accept()) {
//                    System.out.println("Client connected");
//                    new Thread(new ClientConnection(socket, database)).start();
//                }
            }

        } catch (IOException e) {
            System.out.println("Server stopped");
            throw new RuntimeException(e);
        }
    }

}
