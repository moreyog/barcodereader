package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BarcodeGenerator {
    public static void main(String[] args) throws Exception {
        String text = "Book - The Power of one thought - Ananya"; // Text to encode
        String filePath = "barcode.png"; // Output file path
        int width = 400; // Barcode width
        int height = 150; // Barcode height

        Writer writer = new Code128Writer();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.CODE_128, width, height);

        Path path = Paths.get(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        System.out.println("Barcode generated at: " + filePath);
    }
}
