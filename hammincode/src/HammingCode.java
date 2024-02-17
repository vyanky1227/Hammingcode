import java.util.Scanner;

public class HammingCode {

    // Function to calculate parity bits
    static int[] calculateParityBits(int[] data) {
        int r = 0, m = data.length;
        while (Math.pow(2, r) < m + r + 1) {
            r++;
        }

        int[] encodedData = new int[m + r];
        int j = 0;
        for (int i = 0; i < encodedData.length; i++) {
            if ((i + 1) == Math.pow(2, j)) {
                j++;
            } else {
                encodedData[i] = data[i - j];
            }
        }

        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int parity = 0;
            for (int k = parityIndex; k < encodedData.length; k += (parityIndex + 1) * 2) {
                for (int l = k; l < Math.min(k + parityIndex + 1, encodedData.length); l++) {
                    parity ^= encodedData[l];
                }
            }
            encodedData[parityIndex] = parity;
        }

        return encodedData;
    }

    // Function to correct errors in the received data
    static int[] correctErrors(int[] receivedData) {
        int r = 0;
        while (Math.pow(2, r) < receivedData.length) {
            r++;
        }

        int[] correctedData = receivedData.clone();
        int errorPosition = 0;
        for (int i = 0; i < r; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int parity = 0;
            for (int k = parityIndex; k < correctedData.length; k += (parityIndex + 1) * 2) {
                for (int l = k; l < Math.min(k + parityIndex + 1, correctedData.length); l++) {
                    parity ^= correctedData[l];
                }
            }
            if (parity != 0) {
                errorPosition += parityIndex + 1;
            }
        }

        if (errorPosition > 0) {
            correctedData[errorPosition - 1] ^= 1;
        }

        return correctedData;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter data bits separated by spaces: ");
        String[] input = scanner.nextLine().split(" ");
        int[] data = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            data[i] = Integer.parseInt(input[i]);
        }

        int[] encodedData = calculateParityBits(data);
        System.out.print("Encoded data: ");
        for (int bit : encodedData) {
            System.out.print(bit + " ");
        }
        System.out.println();

        System.out.print("Enter received data bits separated by spaces: ");
        input = scanner.nextLine().split(" ");
        int[] receivedData = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            receivedData[i] = Integer.parseInt(input[i]);
        }

        int[] correctedData = correctErrors(receivedData);
        System.out.print("Corrected data: ");
        for (int bit : correctedData) {
            System.out.print(bit + " ");
        }
        System.out.println();
    }
}
