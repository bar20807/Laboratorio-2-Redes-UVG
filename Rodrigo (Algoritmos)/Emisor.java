import java.util.Scanner;

public class Emisor {

    public static int calcRedundantBits(int m) {
        for (int i = 0; ; i++) {
            if (Math.pow(2, i) >= m + i + 1) {
                return i;
            }
        }
    }

    public static String posRedundantBits(String data, int r) {
        int j = 0;
        int k = 1;
        int m = data.length();
        StringBuilder res = new StringBuilder();

        for (int i = 1; i <= m + r; i++) {
            if (i == Math.pow(2, j)) {
                res.append('0');
                j++;
            } else {
                res.append(data.charAt(m - k));
                k++;
            }
        }

        return res.reverse().toString();
    }

    public static String calcParityBits(String arr, int r) {
        int n = arr.length();
        char[] arrChars = arr.toCharArray();

        for (int i = 0; i < r; i++) {
            int val = 0;
            for (int j = 1; j <= n; j++) {
                if ((j & (1 << i)) == (1 << i)) {
                    val ^= Character.getNumericValue(arrChars[n - j]);
                }
            }

            arrChars[n - (int) Math.pow(2, i)] = Character.forDigit(val, 10);
        }

        return new String(arrChars);
    }


    public static void main(String[] args) {
        String data = "1011";
        int m = data.length();
        int r = calcRedundantBits(m);
        String arr = posRedundantBits(data, r);
        arr = calcParityBits(arr, r);

        System.out.println("Data transferred is " + arr);

    }
}

