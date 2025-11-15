import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to chat server.");

            new Thread(new ReadThread(socket)).start();
            new Thread(new WriteThread(socket)).start();
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}

class ReadThread implements Runnable {
    private BufferedReader reader;

    public ReadThread(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            String response;
            while ((response = reader.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}

class WriteThread implements Runnable {
    private PrintWriter writer;
    private BufferedReader console;

    public WriteThread(Socket socket) throws IOException {
        writer = new PrintWriter(socket.getOutputStream(), true);
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            String input;
            while ((input = console.readLine()) != null) {
                writer.println(input);
            }
        } catch (IOException e) {
            System.out.println("Error writing to server.");
        }
    }
}
