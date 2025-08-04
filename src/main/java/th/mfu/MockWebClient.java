package th.mfu;

import java.io.*;
import java.net.*;

// call mockup server at port 8080
public class MockWebClient {
    public static void main(String[] args) {
 try (
            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Send an HTTP GET request to the web server
            out.print("GET / HTTP/1.1\r\n");
            out.print("Host: localhost\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");
            out.flush();

            // Read the response from the web server and print out to console
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
