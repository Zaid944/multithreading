package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException {
        int port = 8090;
        try (ServerSocket socket = new ServerSocket(port)) {
            // after this try block socket.close() will be called
            socket.setSoTimeout(10000000);
            while (true) {
                System.out.println("server is listening on port" + port);
                Socket acceptedConnection = socket.accept();
                System.out.println("connection accepted from client" + acceptedConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(acceptedConnection.getInputStream()));

                toClient.println("hello from server");
                toClient.close();
                fromClient.close();
                acceptedConnection.close();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}