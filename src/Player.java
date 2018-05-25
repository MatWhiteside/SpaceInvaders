import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Matthew
 */
public class Player extends Sprite {
    private int dx;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean allowMoveLeft = true;
    private boolean allowMoveRight = true;
    private MissileList missileList;

    /**
     * Initialise the player.
     */
    public Player() {
        super(250, 500);
        initPlayer();
    }

    /*
    Initialise the player
     */
    private void initPlayer() {
        loadImage("/player.png");
        getImageDimensions();
        missileList = new MissileList();
    }

    /**
     * Move the player, call to update position of player.
     */
    public void move() {
        setX(getX() + dx);
    }

    /**
     * Return an {@link ArrayList} of {@link Missile}'s shot by the player.
     * @return {@link ArrayList} of {@link Missile}'s shot by the player.
     */
    public ArrayList<Missile> getMissiles() {
        return missileList.getMissiles();
    }

    /**
     * Fire a missile from the player.
     */
    public void fire() {
        missileList.add(new Missile((getX() + (getWidth()/2)-2), getY()));
    }

    /**
     * Set if the player is allowed to move left, usually set to false when the player hits the left boundary.
     * @param allowMoveLeft true = can move left, false = can't move left
     */
    public void setAllowMoveLeft(boolean allowMoveLeft) {
        this.allowMoveLeft = allowMoveLeft;
    }

    /**
     Set if the player is allowed to move right, usually set to false when the player hits the right boundary.
     * @param allowMoveRight true = can move right, false = can't move right
     */
    public void setAllowMoveRight(boolean allowMoveRight) {
        this.allowMoveRight = allowMoveRight;
    }

    /**
     * Check if the left key is pressed on the keyboard.
     * @return true = left key pressed, false = left key not pressed.
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Check if the right key is pressed on the keyboard.
     * @return true = right key pressed, false = right key not pressed.
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * Check if the player has hit the right boundary
     * @return true = hit the right boundary, false = not hit the right boundary.
     */
    public boolean isOutOfBoundsRight() {
        return getX() > 725;
    }

    /**
     * Check if the player has hit the left boundary
     * @return true = hit the left boundary, false = not hit the left boundary.
     */
    public boolean isOutOfBoundsLeft() {
        return getX() < 15;
    }

    /**
     * Resets the player movement, useful when right / left is being pressed and the player is killed.
     */
    public void stopPlayerMovement() {
        dx = 0;
    }

    /**
     * Detects key pressed for left, right and space.
     * @param e {@link KeyEvent} from the keyboard.
     */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            if (missileList.getMissiles().size() == 0) {
                fire();
            }
        }

        if (key == KeyEvent.VK_RIGHT && allowMoveRight) {
            dx = 4;
            rightPressed = true;
        }

        if (key == KeyEvent.VK_LEFT && allowMoveLeft) {
            dx = -4;
            leftPressed = true;
        }
    }

    /**
     * Detects key released for left, right and space.
     * @param e {@link KeyEvent} from the keyboard.
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            if (rightPressed) {
                dx = 4;
                leftPressed = false;
            } else {
                dx = 0;
                leftPressed = false;
            }
        }

        if (key == KeyEvent.VK_RIGHT) {
            if (leftPressed) {
                dx = -4;
                rightPressed = false;
            } else {
                dx = 0;
                rightPressed = false;
            }
        }
    }
}
