import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.util.Random; // Additional import for Random

public class DuckSprite extends JPanel {
    private Image duckImage;
    private Point mouseClickPoint;
    private JFrame topFrame;
    private final String passcode = "DIE"; // Replace with your actual passcode

    // Timer for periodic updates and Random instance
    private Timer movementTimer;
    private Random random;

    public DuckSprite() {
        // Load the duck image with transparency
        try {
            duckImage = ImageIO.read(new File("C:\\Users\\wolfp\\IdeaProjects\\Duck boy\\src\\duck_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error here
        }
        setOpaque(false); // Make the JPanel transparent

        // Initialize random object for generating random positions
        random = new Random();

        // Make the panel draggable
        MouseAdapter ma = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseClickPoint = e.getPoint(); // Remember the click point
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

        // Start the timer for random movement here
        int delay = 1000; // Delay in milliseconds (1 second)
        movementTimer = new Timer(delay, e -> moveFrameRandomly());
        movementTimer.start();

        // Set up the key listener for F12
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F12) {
                    setUIDarkTheme();
                    String userInput = JOptionPane.showInputDialog(frame, "Enter passcode to exit:");
                    if (passcode.equals(userInput)) {
                        // Exit normally with a specific code indicating intentional closure
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Incorrect passcode.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    resetUIDefaults(); // Reset to the default theme
                }
            }
        });
    }

    // Method to move the frame randomly
    private void moveFrameRandomly() {
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Randomly calculate new X and Y within screen bounds
        int newX = random.nextInt(screenWidth - topFrame.getWidth());
        int newY = random.nextInt(screenHeight - topFrame.getHeight());

        // Update the frame location
        topFrame.setLocation(newX, newY);
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

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
