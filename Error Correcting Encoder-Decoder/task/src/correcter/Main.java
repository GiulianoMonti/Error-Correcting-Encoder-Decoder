package correcter;

import java.io.*;
import java.util.*;

public class Main {

    public static int getBit(byte b, int index) {
        return (b & (1 << (7 - index))) == 0 ? 0 : 1;
    }

    public static byte setBit(byte b, int bit, int index) {
        return (byte)(b | (bit << (7 - index)));
    }


    public static byte randomFlip(Random random) {
        int index = random.nextInt(8);
        return (byte)(1 << index);
    }
    public static void jumble(byte[] inp) {
        Random random = new Random();
        for (int i = 0; i < inp.length; i++) {
            inp[i] ^= randomFlip(random);
        }
    }

    public static void encode() {
        File file = new File("send.txt");
        int numBytes = (int) file.length();
        int numBits = numBytes * 8;
        int numOutBytes = numBits / 3 + (numBits % 3 == 0 ? 0 : 1);
        byte[] data = new byte[numBytes];
        byte[] dataOut = new byte[numOutBytes];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < numOutBytes; i++) {
            dataOut[i] = 0;
            int xored = 0;
            for (int j = 0; j < 3; j++) {
                int bitIndex = i * 3 + j;
                int byteIndex = bitIndex / 8;
                int bytePos = bitIndex % 8;
                int bit;
                if (byteIndex >= data.length) {
                    bit = 0;
                } else {
                    bit = getBit(data[byteIndex], bytePos);
                }
                xored ^= bit;
                dataOut[i] = setBit(dataOut[i], bit, j*2);
                dataOut[i] = setBit(dataOut[i], bit, j*2+1);
            }
            dataOut[i] = setBit(dataOut[i], xored, 6);
            dataOut[i] = setBit(dataOut[i], xored, 7);
        }
        File outFile = new File("encoded.txt");
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(dataOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send() {
        File file = new File("encoded.txt");
        byte[] data = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jumble(data);
        File outFile = new File("received.txt");
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte decodeByte(byte input) {
        int xorall = 0;
        for (int i = 0; i < 4; i++) {
            xorall ^= getBit(input, i * 2);
        }
        for (int i = 0; i < 4; i++) {
            if (getBit(input, i*2) != getBit(input, i*2+1)) {
                return (byte)(input ^ (xorall << (7 - i*2)));
            }
        }
        return input;
    }

    public static void decode() {
        File file = new File("received.txt");
        int inputBytes = (int) file.length();
        int outputBytes = (inputBytes * 3) / 8;
        byte[] data = new byte[(int) file.length()];
        byte[] outData = new byte[outputBytes];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int index = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = decodeByte(data[i]);
            for (int j = 0; j < 3; j++) {
                int bitIndex = i * 3 + j;
                int byteIndex = bitIndex / 8;
                int bytePos = bitIndex % 8;
                int bit = getBit(data[i], j*2);
                if (byteIndex >= outputBytes) {
                    continue;
                }
                outData[byteIndex] = setBit(outData[byteIndex], bit, bytePos);
            }
        }
        File outFile = new File("decoded.txt");
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(outData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.print("Write a mode: ");
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.next();
        if (mode.equals("send")) {
            send();
        }
        else if (mode.equals("encode")) {
            encode();
        }
        else if (mode.equals("decode")) {
            decode();
        }
    }
}