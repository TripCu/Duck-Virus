import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class DuckSprite extends JPanel {
    private Image duckImage;
    private Point mouseClickPoint;

    public DuckSprite() {
        // Load the duck image with transparency
        try {
            duckImage = ImageIO.read(new File("C:\\Users\\wolfp\\IdeaProjects\\Duck boy\\src\\duck_sprite.png")); // Ensure this is a transparent PNG
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
                // The window's current location
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(DuckSprite.this);
                int xMoved = e.getX() - mouseClickPoint.x;
                int yMoved = e.getY() - mouseClickPoint.y;
                Point windowLocation = topFrame.getLocation();
                windowLocation.translate(xMoved, yMoved);
                topFrame.setLocation(windowLocation);
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

    public static void main(String[] args) {
        // Start the application in a separate process
        if(args.length == 0) {
            try {
                ProcessBuilder builder = new ProcessBuilder(
                        "java",
                        "-cp",
                        System.getProperty("java.class.path"),
                        "DuckSprite", // The current class name
                        "restart" // Pass a command-line argument to indicate a restart
                );
                Process process = builder.start();
                System.exit(0); // Exit the current process to allow the new process to run
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Normal application start
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame();
                DuckSprite spritePanel = new DuckSprite();

                // Remove the window's title bar
                frame.setUndecorated(true);
                // Set the window to always be on top
                frame.setAlwaysOnTop(true);
                // Add the sprite panel
                frame.add(spritePanel);
                frame.setSize(300, 300); // Adjust size as needed
                frame.setLocationRelativeTo(null); // Center the frame
                frame.setBackground(new Color(0, 0, 0, 0)); // Transparent background for the frame

                // Exit the application when the window is closed
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        try {
                            ProcessBuilder builder = new ProcessBuilder(
                                    "java",
                                    "-cp",
                                    System.getProperty("java.class.path"),
                                    "DuckSprite", // The current class name
                                    "restart" // Pass a command-line argument to indicate a restart
                            );
                            builder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                frame.setVisible(true);
            });
        }
    }
}
