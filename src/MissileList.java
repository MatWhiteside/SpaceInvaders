import java.util.ArrayList;

/**
 * @author Matthew
 */
public class MissileList {
    ArrayList<Missile> missiles;

    /**
     * Initialise an empty list of {@link Missile}'s.
     */
    public MissileList() {
        missiles = new ArrayList<>();
    }

    /**
     * Adds a {@link Missile} to the {@link Missile} list.
     * @param m {@link Missile} to add
     */
    public void add(Missile m) {
        missiles.add(m);
    }

    /**
     * @return {@link ArrayList} of {@link Missile}'s.
     */
    public ArrayList<Missile> getMissiles() {
        return missiles;
    }
}
