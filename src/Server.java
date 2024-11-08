import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Database database;
    int port = 44444;

    public Server() {
        database = new Database();

        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = ss.accept()) {
                    new Thread(new ClientConnection(socket, database)).start();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
