import java.util.ArrayList;

/**
 * @author Matthew
 */

/**
 * Contains a list of all blocks on the board.
 */
public class BlockList {
    ArrayList<Block> blocks;

    /**
     * Initialise an empty list of blocks.
     */
    public BlockList() {
        blocks = new ArrayList<>();
    }

    /**
     * Adds a {@link Block} to the block list.
     * @param b {@link Block} to add
     */
    public void add(Block b) {
        blocks.add(b);
    }

    /**
     * Removes a {@link Block} from the blocklist.
     * @param b {@link Block} to remove.
     */
    public void remove(Block b) {
        blocks.remove(b);
    }

    /**
     * Returns an {@link Block} which is an element of the blocks on the board.
     * @param i Index of the block to return
     * @return i'th block of the block list.
     */
    public Block get(int i) {
        return blocks.get(i);
    }

    /**
     *
     * @return ArrayList of blocks.
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
