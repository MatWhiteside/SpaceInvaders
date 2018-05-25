import java.awt.*;
import java.util.ArrayList;

/**
 * @author Matthew
 */
public class EnemyList {
    ArrayList<Enemy> enemies;
    boolean isGoingRight = true;
    boolean moveDown = false;

    /**
     * Initialise an empty list of enemies.
     */
    public EnemyList() {
        enemies = new ArrayList<>();
    }

    /**
     * Adds a {@link Enemy} to the enemy list.
     * @param e {@link Enemy} to add
     */
    public void add(Enemy e) {
        enemies.add(e);
    }

    /**
     * Removes an {@link Enemy} from the enemy list.
     * @param e {@link Enemy} to remove.
     */
    public void remove(Enemy e) {
        enemies.remove(e);
    }

    /**
     * Returns an {@link Enemy} which is an element of the enemies on the board.
     * @param i Index of the {@link Enemy} to return
     * @return i'th {@link Enemy} of the enemy list.
     */
    public Enemy get(int i) {
        return enemies.get(i);
    }

    /**
     *
     * @return ArrayList of {@link Enemy}'s.
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Creates a rectangle with the bounds of: left most enemy on the board, right most enemy on the board.
     * @return {@link Rectangle} with left = left most {@link Enemy}, right = right most {@link Enemy}
     */
    public Rectangle getBounds() {
        Enemy rightMostInvader = enemies.get(0);
        Enemy leftMostInvader = enemies.get(0);

        for (Enemy e : enemies) {
            if (e.getX() > rightMostInvader.getX()) {
                rightMostInvader = e;
            }

            if (e.getX() < leftMostInvader.getX()) {
                leftMostInvader = e;
            }
        }

        return new Rectangle(leftMostInvader.getX(), leftMostInvader.getY(),
                (rightMostInvader.getX()-leftMostInvader.getX()), 10 /* PLACEHOLDER */);
    }

    /**
     * Moves the {@link Enemy}'s as a whole, instead of individually.
     */
    public void move() {

        if (isGoingRight && getBounds().getX() + getBounds().getWidth() > 750) {
            isGoingRight = false;
            moveDown = true;
        } else if (!isGoingRight && getBounds().getX() < 0) {
            isGoingRight = true;
            moveDown = true;
        }

        for (Enemy enemy : enemies) {
            enemy.setX(enemy.getX() + (isGoingRight?1:-1));
        }

        if (moveDown) {
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY()+5);
            }
            moveDown = false;
        }
    }
}
