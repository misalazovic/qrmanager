package qrmanager;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 *
 * @author Misa
 * @version {@value qrmanager.QRManager#VERSION}
 */
public class QRManager implements Serializable {

    /**
     * Current version of QR Manager
     */
    public static final double VERSION = 1.2;
    private static final long serialVersionUID = 1991L;
    private static final QRManager singletonInstance = new QRManager();

    /**
     * Private constructor
     */
    private QRManager() {
    }

    /**
     * Returns singleton instance of class QRManager
     *
     * @return singleton instance of class QRManager
     */
    public static QRManager getInstance() {
        return singletonInstance;
    }

    /**
     * Reads content of QR code on desired location
     *
     * @param pictureToRead path to QR code
     * @return QR code text or error message
     */
    public String read(String pictureToRead) {
        try {
            BufferedImage qrbi = ImageIO.read(new FileInputStream(pictureToRead)); // name and extension (png, gif, jpg, jpeg) of picture
            LuminanceSource source = new BufferedImageLuminanceSource(qrbi);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Reader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);

            return result.getText(); // QR code text
        } catch (IOException | NotFoundException | ChecksumException | FormatException | NullPointerException e) {
            return e.getMessage();
        }
    }

    /**
     * Creates QR code out of desired text on desired location with given width
     * and height with selected file extension (png, jpg, jpeg or gif)
     *
     * @param textToWrite text for transforming to QR code
     * @param saveLocation absolute path to location on which QR code will be
     * saved
     * @param dimensions width and height of QR code
     * @param format file extension of picture containing QR code (png, jpg,
     * jpeg, gif)
     * @return 0 if writing was successful, otherwise -1
     */
    public int write(String textToWrite, String saveLocation, int dimensions, String format) {
        try {
            BitMatrix bMatrix = new QRCodeWriter().encode(textToWrite, BarcodeFormat.QR_CODE, dimensions, dimensions);
            MatrixToImageWriter.writeToStream(bMatrix, format, new FileOutputStream(new File(saveLocation + "." + format)));

            return 0;
        } catch (java.lang.OutOfMemoryError e) {
            System.err.println(e.getMessage());
            return -2;
        } catch (WriterException | IOException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
}
