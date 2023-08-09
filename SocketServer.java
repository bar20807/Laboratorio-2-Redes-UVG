import java.io.*;
import java.net.*;
// import crc.crc_receptor;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String clientInput;
                    while ((clientInput = in.readLine()) != null) {
                        // System.out.println("Received: " + clientInput);
                        clientInput = crc.crc_receptor.inputReciever(clientInput);
                        if (clientInput != "Error") {
                            // System.out.println("Received modified: " + clientInput);
                            System.out.println(convertBinaryToText(clientInput));
                        } else {
                            System.out.println("Error no se pudo debido a que el mensaje esta incorrecto");
                        }

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + port);
            System.out.println(e.getMessage());
        }
    }

    public static String convertBinaryToText(String binaryString) {
        // System.out.println("codigo antes");
        // System.out.println(binaryString);
        // System.out.println("=================");
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 8) {
            String section = binaryString.substring(i, Math.min(i + 8, binaryString.length()));
            int decimalValue = Integer.parseInt(section, 2);
            textBuilder.append((char) decimalValue);
        }
        return textBuilder.toString();
    }

}
