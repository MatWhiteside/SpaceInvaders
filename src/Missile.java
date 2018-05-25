/**
 * @author Matthew
 */
public class Missile extends Sprite {

    private final int MISSILE_SPEED = 4;
    private final int BOARD_TOP = 0;

    /**
     * Creates a new missile.
     * @param x coordinate
     * @param y coordinate
     */
    public Missile(int x, int y) {
        super(x, y);
        initMissile();
    }

    /*
    Initialise the missile
     */
    private void initMissile() {
        loadImage("/missile.png");
        getImageDimensions();
    }

    /**
     * Moves the missile.
     * @param down true = move down, false = move up
     */
    public void move(boolean down) {
        setY(getY() + (down?-MISSILE_SPEED:MISSILE_SPEED));

        if (getY() < BOARD_TOP) {
            setVisible(false);
        }
    }
}
