import java.util.ArrayList;

/**
 * @author Matthew
 */
public class Enemy extends Sprite {

    private MissileList missileList;

    /**
     * Creates a new enemy.
     * @param x coordinate
     * @param y coordinate
     */
    public Enemy(int x, int y) {
        super(x, y);
        initEnemy();
    }

    /*
    Initialise the enemy
     */
    private void initEnemy() {
        loadImage("/enemy.png");
        getImageDimensions();
        missileList = new MissileList();
    }

    /**
     * Returns an {@link ArrayList} of {@link Missile}s the player has shot.
     * @return {@link ArrayList} of {@link Missile}s the player has shot.
     */
    public ArrayList<Missile> getMissiles() {
        return missileList.getMissiles();
    }

    /**
     * Fires a missile from the player.
     */
    public void fire() {
        missileList.add(new Missile((getX() + getWidth()/2), getY()));
    }
}
