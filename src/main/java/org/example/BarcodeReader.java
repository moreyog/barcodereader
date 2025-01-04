package org.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarcodeReader {
    public static void main(String[] args) {
        String filePath = "barcode.png"; // Path to the barcode image

        try {
            // Load the image
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));

            // Convert the image to a binary bitmap source
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decode the barcode
            Result result = new MultiFormatReader().decode(bitmap);

            // Print the result
            System.out.println("Barcode Text: " + result.getText());
            System.out.println("Barcode Format: " + result.getBarcodeFormat());
        } catch (NotFoundException e) {
            System.out.println("No barcode found in the image.");
        } catch (IOException e) {
            System.out.println("Error reading the image: " + e.getMessage());
        }
    }
}
