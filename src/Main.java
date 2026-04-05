import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends Frame implements Runnable {

    private static Robot robot;
    private static BufferedImage img;
    private static BufferedImage buf;
    private static Color color;

    private static int box = 8;
    private static int scale = 8;
    
    private static Point lastMousePos = new Point(-1, -1);
    private static boolean lastBlinkState = false;

    public Main() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "Could not initialize Robot: " + e.getMessage());
            System.exit(1);
        }

        setSize(220, 80);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 20;
        int y = screenSize.height - getHeight() - 50;

        buf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.exit(0);
            }
        });

        setLocation(x, y);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setVisible(true);

        new Thread(this).start();
    }

    private static void snapshot() {
        PointerInfo pi = MouseInfo.getPointerInfo();
        if (pi == null) return;
        
        int lastX = (int) pi.getLocation().getX();
        int lastY = (int) pi.getLocation().getY();

        color = robot.getPixelColor(lastX, lastY);

        Rectangle area = new Rectangle(lastX - box, lastY - box, box * 2 + 1, box * 2 + 1);
        img = robot.createScreenCapture(area);

        Graphics2D g = img.createGraphics();

        if (System.currentTimeMillis() % 500 < 250) {
            double brightness = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114) / 255;
            g.setColor(brightness > 0.5 ? Color.BLACK : Color.WHITE);

            g.drawLine(box, box + 1, box, box + 3);
            g.drawLine(box + 1, box, box + 3, box);
            g.drawLine(box, box - 3, box, box - 1);
            g.drawLine(box - 3, box, box - 1, box);
        }

        g.dispose();
    }

    public static void main(String[] args) {
        new Main();
    }

    public void run() {
        while (true) {
            PointerInfo pi = MouseInfo.getPointerInfo();
            if (pi != null) {
                Point currentPos = pi.getLocation();
                long currentTime = System.currentTimeMillis();
                boolean currentBlinkState = (currentTime % 500 < 250);

                // Only snapshot and draw if the mouse moved or the blink state changed
                if (!currentPos.equals(lastMousePos) || currentBlinkState != lastBlinkState) {
                    lastMousePos = currentPos;
                    lastBlinkState = currentBlinkState;
                    snapshot();
                    draw();
                }
            }

            try {
                Thread.sleep(8);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void draw() {
        Graphics2D g = buf.createGraphics();

        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        if (img != null) {
            g.drawImage(img, 8, 8, 8 + box * scale, 8 + box * scale, 0, 0, box * 2 + 1, box * 2 + 1, null);
        }

        String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        String hsbHex = String.format("#%02X%02X%02X", (int) (hsb[0] * 255), (int) (hsb[1] * 255), (int) (hsb[2] * 255));

        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        double brightness = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114) / 255;
        g.setColor(brightness > 0.5 ? Color.BLACK : Color.WHITE);
        g.drawString("rgb: " + hex, 100, 36);
        g.drawString("hsb: " + hsbHex, 100, 52);

        g.dispose();

        g = (Graphics2D) getGraphics();
        if (g != null) {
            g.drawImage(buf, 0, 0, null);
            g.dispose();
        }
    }
}
