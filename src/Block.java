/**
 * @author Matthew
 */
public class Block extends Sprite {

    /**
     * Creates a new block.
     * @param x coordinate
     * @param y coordinate
     */
    public Block(int x, int y) {
        super(x, y);
        initBlock();
    }

    /*
    Loads the block image and sets the dimensions used for collision.
     */
    private void initBlock() {
        loadImage("/block.png");
        getImageDimensions();
    }
}
