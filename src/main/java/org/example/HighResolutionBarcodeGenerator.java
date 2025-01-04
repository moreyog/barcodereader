package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HighResolutionBarcodeGenerator {
    public static void main(String[] args) {
        String primaryKey = "BOOK_2025_123456"; // Example primary key
        int resolution = 300; // Use 300x300 resolution

        generateBarcode(primaryKey, "high_res_barcode", resolution);
    }

    private static void generateBarcode(String text, String fileName, int resolution) {
        try {
            // Create QR Code
            Writer writer = new com.google.zxing.qrcode.QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, resolution, resolution);

            // Save as PNG
            Path path = Paths.get(fileName + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("Barcode generated: " + path.toAbsolutePath());
        } catch (WriterException | java.io.IOException e) {
            System.err.println("Error generating barcode: " + e.getMessage());
        }
    }
}
