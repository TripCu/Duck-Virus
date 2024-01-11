import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.util.Random;

public class DuckSprite extends JPanel {
    private Image duckImage;
    private Point mouseClickPoint;
    private JFrame topFrame;
    private final String passcode = "DIE";

    private Timer movementTimer;
    private Random random;
    private int dx = 10; // Horizontal movement delta
    private int dy = 10; // Vertical movement delta

    public DuckSprite() {
        // Load the duck image with transparency
        try {
            duckImage = ImageIO.read(new File("C:\\Users\\wolfp\\IdeaProjects\\Duck boy\\src\\duck_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOpaque(false);

        random = new Random();

        MouseAdapter ma = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseClickPoint = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                if (topFrame != null) {
                    int xMoved = e.getX() - mouseClickPoint.x;
                    int yMoved = e.getY() - mouseClickPoint.y;
                    Point windowLocation = topFrame.getLocation();
                    windowLocation.translate(xMoved, yMoved);
                    topFrame.setLocation(windowLocation);
                }
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    private void setupFrame(JFrame frame) {
        this.topFrame = frame;

        int delay = 0; // Faster movement for smooth glide
        movementTimer = new Timer(delay, e -> glideDuck());
        movementTimer.start();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F12) {
                    setUIDarkTheme();
                    String userInput = JOptionPane.showInputDialog(frame, "Enter passcode to exit:");
                    if (passcode.equals(userInput)) {
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect passcode.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    resetUIDefaults();
                }
            }
        });
    }

    private void glideDuck() {
        if (topFrame == null) return;

        Point currentLocation = topFrame.getLocation();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (currentLocation.x < 0 || currentLocation.x > screenSize.width - topFrame.getWidth()) {
            dx = -dx; // Reverse horizontal direction
        }
        if (currentLocation.y < 0 || currentLocation.y > screenSize.height - topFrame.getHeight()) {
            dy = -dy; // Reverse vertical direction
        }

        currentLocation.translate(dx, dy);
        topFrame.setLocation(currentLocation);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (duckImage != null) {
            g.drawImage(duckImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private void setUIDarkTheme() {
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.foreground", Color.red);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("TextField.background", Color.BLACK);
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.caretForeground", Color.WHITE);
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", Color.RED);
        UIManager.put("Button.select", Color.BLACK);
    }

    private void resetUIDefaults() {
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.foreground", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("TextField.background", null);
        UIManager.put("TextField.foreground", null);
        UIManager.put("TextField.caretForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.select", null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            DuckSprite spritePanel = new DuckSprite();

            frame.setUndecorated(true);
            frame.setAlwaysOnTop(true);
            frame.add(spritePanel);
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setBackground(new Color(0, 0, 0, 0));
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setType(Window.Type.UTILITY);

            spritePanel.setupFrame(frame);
            frame.setVisible(true);
        });
    }
}
