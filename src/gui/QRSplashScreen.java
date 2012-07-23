package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Misa
 * @version {@value qrmanager.QRManager#VERSION}
 */
public class QRSplashScreen extends JFrame {

    /**
     * Creates splash screen
     */
    public QRSplashScreen() {
        JLabel l = new JLabel(new ImageIcon("resource/splash.png"));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width / 2 - (labelSize.width / 2), screenSize.height / 2 - (labelSize.height / 2));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                dispose();
            }
        });

        final Runnable closerRunner = new Runnable() {
            @Override
            public void run() {
                setVisible(false);
                dispose();
            }
        };

        Runnable waitRunner = new Runnable() {
            @Override
            public void run() {
                try {
                    SwingUtilities.invokeAndWait(closerRunner);
                } catch (InterruptedException | InvocationTargetException e) {
                    System.err.println(e.getMessage());
                }
            }
        };

        setVisible(true);
        Thread splashThread = new Thread(waitRunner);
        splashThread.start();
    }
}
