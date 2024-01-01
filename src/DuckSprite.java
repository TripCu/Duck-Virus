import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class DuckSprite extends JPanel {
    private Image duckImage;
    private Point mouseClickPoint;
    private JFrame topFrame;
    private final String passcode = "DIE"; // Replace with your actual passcode

    public DuckSprite() {
        // Load the duck image with transcdparency
        try {
            duckImage = ImageIO.read(new File("C:\\Users\\wolfp\\IdeaProjects\\Duck boy\\src\\duck_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error here
        }
        setOpaque(false); // Make the JPanel transparent

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (duckImage != null) {
            g.drawImage(duckImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private void setupFrame(JFrame frame) {
        this.topFrame = frame;

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

            // Remove the window's title bar and make sure it's always on top
            frame.setUndecorated(true);
            frame.setAlwaysOnTop(true);

            // Add the sprite panel and set the size
            frame.add(spritePanel);
            frame.setSize(300, 300); // Adjust size as needed
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setBackground(new Color(0, 0, 0, 0)); // Transparent background for the frame

            // Prevent the JFrame from closing when the user presses the close button
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Remove the program from the taskbar and alt-tab list
            frame.setType(Window.Type.UTILITY);

            // Set up the frame for key listening and prevent closing
            spritePanel.setupFrame(frame);

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
