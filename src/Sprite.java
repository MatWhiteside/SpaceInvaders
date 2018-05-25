import javax.swing.*;
import java.awt.*;

/**
 * @author Matthew
 */
public class Sprite {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean vis = true;
    private Image image;

    /**
     * Sets the x,y coordinates of the sprite.
     * @param x coordinate
     * @param y coordinate
     */
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the image of the sprite to the image in the given path.
     * @param imageName Image path.
     */
    public void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(getClass().getResource(imageName));
        image = ii.getImage();
    }

    /**
     * Gets the dimensions of the given image.
     */
    public void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    /**
     * @return {@link Image} being used for the sprite.
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return X coordinate of the sprite.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y coordinate of the sprite.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x coordinate of the sprite.
     * @param x coordinate to move the sprite to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate of the sprite.
     * @param y coordinate to move the sprite to.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Height of the sprite (image height)
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return Width of the sprite (image width)
     */
    public int getWidth() {
        return width;
    }

    /**
     * Is the sprite visible or not.
     * @return true = visible, false = not visible
     */
    public boolean isVisible() {
        return vis;
    }

    /**
     * WARNING - MORE OF AN INDICATOR TO WHETHER SOMETHING SHOULD BE ON THE BOARD OR NOT RATHER THAN ACTUALLY HIDING A SPRITE
     * Sets if the sprite is visible or not.
     * @param visible true = visible, false = not visible
     */
    public void setVisible(Boolean visible) {
        vis = visible;
    }

    /**
     * Creates a rectangle with the bounds of the sprite.
     * @return {@link Rectangle} containing the bounds of the sprite.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
