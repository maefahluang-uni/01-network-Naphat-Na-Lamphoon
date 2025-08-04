package th.mfu;

import java.io.*;
import java.net.*;

public class MockWebClientTest {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("MockWebClientTest started on port 8080");
            while (true) { // Infinite loop to accept connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                // Handle client in a separate thread (optional for concurrency)
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
    ) {
        // Read request (not used here, just consume input)
        StringBuilder request = new StringBuilder(); // <-- Add this line
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            request.append(line).append("\n");
        }
        System.out.println("Received request:\n" + request);

        // Send mock HTTP response
        String responseBody = "Hello, Web! on Port 8080";
        String response = "HTTP/1.1 200 OK\r\nContent-Length: " + responseBody.length() + "\r\n\r\n" + responseBody;
        out.print(response);
        out.flush();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            clientSocket.close();
        } catch (IOException e) {
            // Ignore
        }
    }
}
}