import javax.swing.*;
import java.awt.*;

/**
 * @author Matthew
 */

/**
 * Creates an application window that the {@link Board} is added to. Contains the main method to execute the program.
 */
public class Application extends JFrame {
    public Application() {
        initUI();
    }

    /*
    Initialise the user interface, and setup the JFrame
     */
    private void initUI() {
        add(new Board());
        pack();

        setSize(800, 600);
        setTitle("Space invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Executes the application.
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }
}
