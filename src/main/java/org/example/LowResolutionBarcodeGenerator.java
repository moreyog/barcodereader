package org.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LowResolutionBarcodeGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text for the barcode (max 100 characters):");
        String text = scanner.nextLine();

        if (text.length() > 100) {
            System.out.println("Error: Text exceeds the 100-character limit.");
            return;
        }

        System.out.println("Enter output file name (without extension):");
        String fileName = scanner.nextLine();

        System.out.println("Enter resolution (e.g., 100 for low resolution):");
        int resolution = scanner.nextInt();

        generateBarcode(text, fileName, resolution);
    }

    private static void generateBarcode(String text, String fileName, int resolution) {
        try {
            // Create a QR Code Writer
            Writer writer = new QRCodeWriter();

            // Generate the QR code matrix
            BitMatrix bitMatrix = writer.encode(
                    text, BarcodeFormat.QR_CODE, resolution, resolution
            );

            // Save the barcode as an image file
            Path path = Paths.get(fileName + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("Barcode generated successfully: " + path.toAbsolutePath());
        } catch (WriterException | java.io.IOException e) {
            System.err.println("Error generating barcode: " + e.getMessage());
        }
    }
}
