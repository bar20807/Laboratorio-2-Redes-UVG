import java.util.*;

public class crc_receptor {

    // Función para calcular el CRC
    public static boolean crcCalculation(List<Integer> tramaBits, List<Integer> crc) {
        List<Integer> copiaTrama = new ArrayList<>(tramaBits);
        List<Integer> calculate = new ArrayList<>();

        for (int x = 0; x < crc.size(); x++) {
            calculate.add(copiaTrama.remove(0));
        }

        while (!copiaTrama.isEmpty()) {
            // System.out.println("calculate");
            // System.out.println(calculate);
            for (int i = 0; i < crc.size(); i++) {
                calculate.set(i, calculate.get(i) ^ crc.get(i));
            }

            while (!calculate.isEmpty() && calculate.get(0) == 0) {
                calculate.remove(0);
            }

            while (calculate.size() < crc.size() && !copiaTrama.isEmpty()) {
                calculate.add(copiaTrama.remove(0));
            }

        }

        if (crc.size() == calculate.size()) {
            for (int i = 0; i < crc.size(); i++) {
                calculate.set(i, calculate.get(i) ^ crc.get(i));
            }
        }

        boolean isCorrupt = calculate.contains(1);
        if (isCorrupt) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        // Array de CRC-32
        List<Integer> crc_32 = Arrays.asList(1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1);
        List<Integer> crc_3 = Arrays.asList(1, 0, 0, 1);

        // Obtener la trama del usuario y convertirla en una lista de enteros
        System.out.println("Ingrese la trama (solo 0s y 1s): ");
        Scanner scanner = new Scanner(System.in);
        String trama = scanner.nextLine();
        List<Integer> tramaBits = new ArrayList<>();
        for (char bit : trama.toCharArray()) {
            tramaBits.add(Character.getNumericValue(bit));
        }

        // Elegir el CRC deseado (crc_3 o crc_32)
        List<Integer> crc = crc_32; // Cambiar a crc_32 si se quiere usar CRC-32
        // System.out.println("trama bits");
        // System.out.println(tramaBits.toString());

        // Calcular los últimos bits del CRC según la longitud del CRC
        boolean crcResult = crcCalculation(tramaBits, crc);
        List<Integer> tramaSinCRC = tramaBits.subList(0, tramaBits.size() - crc.size() + 1);
        if (crcResult) {
            System.out.println("Todo correcto");
            System.out.println(tramaSinCRC);
        } else {
            System.out.println("problema! trama con problema");
        }

    }
}
