package th.mfu;

import org.junit.*;
import java.io.*;
import java.net.*;

/**
 * Unit test for simple App.
 */
public class MockWebServerTest {
    // Test constants
    private static final int PORT = 8080;
    private static String lineSeparator = System.lineSeparator();
    private static final String TEST_RESPONSE = "HTTP/1.1 200 OK"+lineSeparator+"Content-Type: text/html"+lineSeparator+lineSeparator+"<html><body>Hello, Web! on Port "+PORT+"</body></html>"+lineSeparator;

    // Mock Web Server instance
    private Thread mockWebServer;

    @Before
    public void setUp() {
        // Start the mock web server before each test
        mockWebServer = new Thread(new MockWebServer(PORT));
        mockWebServer.start();
    }

    @After
    public void tearDown() {
        // Stop the mock web server after each test
        mockWebServer.interrupt();
    }

    @Test
    public void testMockWebServer() throws IOException {
       // Create input and output streams for the socket
       Socket socket = new Socket("localhost", PORT);

       PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
       BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       
       // Send an HTTP GET request to the web server
       String request = "GET / HTTP/1.1"+lineSeparator+"Host: localhost"+lineSeparator+lineSeparator;
       out.println(request);

       // Read the response from the web server
       StringBuffer allresponse = new StringBuffer();
       String response;
       while ((response = in.readLine()) != null) {
        allresponse.append(response+lineSeparator);

       }

       Assert.assertEquals(TEST_RESPONSE, allresponse.toString());
       
       // Close the socket
       socket.close();
        // Assert that the response matches the expected response
    }


}
