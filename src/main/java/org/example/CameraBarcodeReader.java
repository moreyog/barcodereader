package org.example;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class CameraBarcodeReader {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        VideoCapture camera = new VideoCapture(0); // Open default camera (0)
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not accessible");
            return;
        }

        Mat frame = new Mat();
        JFrame jFrame = new JFrame("Camera Barcode Reader");
        JLabel vidLabel = new JLabel();
        jFrame.setContentPane(vidLabel);
        jFrame.setSize(800, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        while (true) {
            if (camera.read(frame)) {
                // Convert Mat to BufferedImage
                BufferedImage image = matToBufferedImage(frame);

                // Display the video frame
                vidLabel.setIcon(new ImageIcon(image));
                jFrame.repaint();

                // Try to decode the barcode
                String barcodeText = decodeBarcode(image);
                if (barcodeText != null) {
                    System.out.println("Barcode Found: " + barcodeText);
                    break;
                }
            }
        }
        camera.release();
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        int type = mat.channels() > 1 ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;
        BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }

    private static String decodeBarcode(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            // No barcode found
            return null;
        }
    }
}
