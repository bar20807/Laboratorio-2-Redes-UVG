import java.io.*;
import java.net.*;
import crc.crc_receptor;
import Rodrigo.Receptor;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        int successes = 0;
        int failures = 0;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String clientInput;
                    while ((clientInput = in.readLine()) != null) {
                        //System.out.println("Este es clientInput: " + clientInput);
                        // System.out.println("Received: " + clientInput);
                        String[] data = clientInput.split(",");

                        int scanner = Integer.parseInt(data[2]);
                        if (scanner == 1) {
                            String inputData = data[0];

                            int deci = Integer.parseInt(data[1]);
                            if (deci == 1) {
                                clientInput = crc.crc_receptor.inputReciever(inputData);
                            } else if (deci == 2 ) {
                                System.out.println("Data que le llega al hamming: "  + inputData); 
                                clientInput = Rodrigo.Receptor.ErrorCorrector(inputData);
                            }
                            else {
                                System.out.println("ERROR, no existe dicha opción");
                            }

                            if (clientInput != "Error") {
                                System.out.println("Received modified: " + clientInput);
                                System.out.println(convertBinaryToText(clientInput));
                            } else {
                                System.out.println("Error no se pudo debido a que el mensaje esta incorrecto");
                            }
                        } else {
                            String inputData = data[0];
                            int deci = Integer.parseInt(data[1]);
                            if (deci == 1) {
                                clientInput = crc.crc_receptor.inputReciever(inputData);
                            } else if (deci == 2 ){
                                clientInput = Rodrigo.Receptor.ErrorCorrector(inputData);
                            }

                            if (clientInput != "Error") {
                                // System.out.println("Received modified: " + clientInput);
                                successes += 1;
                                //System.out.println("successes: " + successes);
                            } else {
                                failures += 1;
                                //System.out.println("failures: " + failures);
                            }    
                        }
                    }
                    
                    System.out.println("Total successes: " + successes);
                    System.out.println("Total failures: " + failures);
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
