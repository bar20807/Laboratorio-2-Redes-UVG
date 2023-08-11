package Rodrigo;

public class Receptor {

    public static int calcRedundantBits(int m) {
        // Cálculo del número de bits redundantes necesario
        for (int i = 0; i < m; i++) {
            if (Math.pow(2, i) >= m + i + 1) {
                return i;
            }
        }
        return -1; // Esto podría ser un valor de retorno válido según tu lógica
    }

    public static String posRedundantBits(String data, int r) {
        // Colocar los bits redundantes en las posiciones que corresponden a las potencias de 2
        int j = 0;
        int k = 1;
        int m = data.length();
        StringBuilder res = new StringBuilder();

        for (int i = 1; i <= m + r; i++) {
            if (i == Math.pow(2, j)) {
                res.append('0');
                j++;
            } else {
                res.append(data.charAt(data.length() - k));
                k++;
            }
        }

        return res.reverse().toString();
    }

    public static String calcParityBits(String arr, int r) {
        // Cálculo de los bits de paridad
        int n = arr.length();
        for (int i = 0; i < r; i++) {
            int val = 0;
            for (int j = 1; j <= n; j++) {
                if ((j & (1 << i)) == (1 << i)) {
                    val = val ^ Integer.parseInt(String.valueOf(arr.charAt(arr.length() - j)));
                }
            }
            arr = arr.substring(0, n - (1 << i)) + val + arr.substring(n - (1 << i) + 1);
        }
        return arr;
    }

    public static int detectError(String arr, int nr) {
        // Cálculo de los bits de paridad nuevamente
        int n = arr.length();
        int res = 0;

        for (int i = 0; i < nr; i++) {
            int val = 0;
            for (int j = 1; j <= n; j++) {
                if ((j & (1 << i)) == (1 << i)) {
                    val = val ^ Integer.parseInt(String.valueOf(arr.charAt(arr.length() - j)));
                }
            }
            res = res + val * (int) Math.pow(10, i);
        }

        System.out.println("Resultado de res: "  + res);

        // Convertir binario a decimal
        return Integer.parseInt(String.valueOf(res), 2);
    }

    public static String ErrorCorrector(String dataReceived) {
        // Calcular el número de bits redundantes necesario
        int mReceived = dataReceived.length();
        int rReceived = calcRedundantBits(mReceived);
    
        // Calcular las posiciones de los bits redundantes
        String arrReceived = posRedundantBits(dataReceived, rReceived);
    
        // Calcular los bits de paridad
        arrReceived = calcParityBits(arrReceived, rReceived);
    
        // Comparar el bit de paridad recibido con el calculado para detectar errores
        int correction = detectError(dataReceived, rReceived);
    
        // Mostrar el resultado
        if (correction == 0) {
            // Eliminar los bits redundantes para obtener la cadena original
            StringBuilder originalData = new StringBuilder();
            int j = 0;
            for (int i = 1; i <= dataReceived.length(); i++) {
                if (i != Math.pow(2, j)) {
                    originalData.append(dataReceived.charAt(dataReceived.length() - i));
                } else {
                    j++;
                }
            }
            System.out.println("Mensaje que pienso es el original: " + originalData.reverse());
            return originalData.toString(); // Devuelve la cadena original sin codificación Hamming
        } else {
            int errorIndex = arrReceived.length() - correction + 1;
            if (errorIndex < 0 || errorIndex == 0) {
                System.out.println("Se detectó un error en la trama recibida en la posición: fuera de los límites");
                return "Error";
            } else {
                System.out.println("Se detectó un error en la trama recibida en la posición: " + errorIndex + " desde la izquierda.");
                String correctedData = arrReceived.substring(0, arrReceived.length() - correction) +
                        (1 - Integer.parseInt(String.valueOf(arrReceived.charAt(arrReceived.length() - correction)))) +
                        arrReceived.substring(arrReceived.length() - correction + 1);
                return correctedData;
            }
        }
    }
    

    public static void main(String[] args) {
        String receivedData = "01101010001101111011011100011010001111"; // Ejemplo de datos recibidos
        String correctedData = ErrorCorrector(receivedData);
        System.out.println("Datos corregidos: " + correctedData);
    }
}
