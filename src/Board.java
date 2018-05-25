import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Matthew
 */
public class Board extends JPanel implements ActionListener {

    private final int DELAY = 10;
    private Player player;
    private EnemyList enemyList;
    private BlockList blockList;
    private int count = 0;
    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int START_X = BOARD_WIDTH/2;
    private int playerLives = 3;
    private boolean isGameOver;
    private boolean movementEnabled;

    /**
     * Creates the board and initialises the game.
     */
    public Board() {
        initBoard();
    }

    /*
    Initialises the board and game properties.
     */
    private void initBoard() {
        // Set properties for the board
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setDoubleBuffered(true);

        // Initialise the timer so objects move
        Timer timer = new Timer(DELAY, this);
        timer.start();

        // Initialise all game objects
        player = new Player();
        initEnemies();
        initBlocks();
        isGameOver = false;
        movementEnabled = true;
    }

    /*
    Initialises three groups of blocks.
     */
    private void initBlocks() {
        blockList = new BlockList();
        // Left blocks
        for (int x = 150; x < 250; x+=15) {
            for (int y = 350; y < 400; y += 15) {
                blockList.add(new Block(x, y));
            }
        }

        // Middle blocks
        for (int x = 350; x < 450; x+=15) {
            for (int y = 350; y < 400; y += 15) {
                blockList.add(new Block(x, y));
            }
        }

        // Right blocks
        for (int x = 550; x < 650; x+=15) {
            for (int y = 350; y < 400; y += 15) {
                blockList.add(new Block(x, y));
            }
        }
    }

    /*
    Initialise the enemies.
     */
    private void initEnemies() {
        enemyList = new EnemyList();
        for (int x = 50; x <= 400; x += 70) {
            for (int y = 50; y <= 210; y += 40) {
                enemyList.add(new Enemy(x, y));
            }
        }
    }

    /*
    Checks for collisions between objects that can collide
     */
    private void checkCollisions() {
        // Collision between player missile and enemy / block
        for (Missile missile : player.getMissiles()) {
            Rectangle playerMissileBounds = missile.getBounds();

            for (Enemy enemy : enemyList.getEnemies()) {
                Rectangle enemyBounds = enemy.getBounds();

                if (playerMissileBounds.intersects(enemyBounds)) {
                    enemy.setVisible(false);
                    missile.setVisible(false);
                }
            }

            if(checkBlockCollisions(playerMissileBounds)) {
                missile.setVisible(false);
            }
        }

        // Collision between enemy missile and player / block
        for (Enemy enemy : enemyList.getEnemies()) {
            for (Missile missile : enemy.getMissiles()) {
                Rectangle enemyMissileBounds = missile.getBounds();

                Rectangle playerBounds = player.getBounds();

                if (enemyMissileBounds.intersects(playerBounds)) {
                    missile.setVisible(false);
                    updatePlayerLives();
                }

                if(checkBlockCollisions(enemyMissileBounds)) {
                    missile.setVisible(false);
                }
            }
        }
    }

    /*
    Given a Rectangle object containing the missile bounds, check the collisions with each block
     */
    private boolean checkBlockCollisions(Rectangle missileBounds) {
        for (Block block : blockList.getBlocks()) {
            Rectangle blockBounds = block.getBounds();
            if (blockBounds.intersects(missileBounds)) {
                block.setVisible(false);
                return true;
            }
        }
        return false;
    }

    /*
    Decrements the players lives, freezes the player object and shoes the explosion image
     */
    private void updatePlayerLives() {
        playerLives -= 1;


        player.loadImage("/player_explosion.png");

        if (!isGameOver()) {
            movementEnabled = false;
            player.stopPlayerMovement();


            java.util.Timer t = new java.util.Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    player.loadImage("/player.png");
                    player.setX(START_X);
                    movementEnabled = true;
                    t.cancel();
                }
            }, 2000, 1);
        }



        player.getMissiles().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSprites(g);

        if (isGameOver()) {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    /*
    Draw all of the objects onto the board / update the positions of the current objects
     */
    private void drawSprites(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.drawImage(player.getImage(), player.getX(), player.getY(), this);

        for (Missile m : player.getMissiles()) {
            g.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }

        for (Block b : blockList.getBlocks()) {
            g.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }

        for (Enemy e : enemyList.getEnemies()) {
            g.drawImage(e.getImage(), e.getX(), e.getY(), this);

            for (Missile m : e.getMissiles()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }
        drawLives(g);
    }

    /*
    Draw the number of lives a player has in the top left
     */
    private void drawLives(Graphics g) {
        Font livesFont = new Font("Tahoma", Font.BOLD, 15);
        g.setFont(livesFont);
        g.setColor(Color.CYAN);
        g.drawString("Aliens left: " + enemyList.getEnemies().size(), 5, 15);
        g.drawString("Lives left: " + playerLives, 5, 30);
    }

    /*
    If the game is over, draw game over in the centre of the screen
     */
    private void drawGameOver(Graphics g) {
        String gameOver = "Game over!";
        String restart = "Hit space to restart";
        Font gameOverFont = new Font("Tahoma", Font.BOLD, 20);

        g.setColor(Color.red);
        g.setFont(gameOverFont);
        g.drawString(gameOver, (BOARD_WIDTH/2)- getFontMetrics(gameOverFont).stringWidth(gameOver)/2,
                BOARD_HEIGHT/2-getFontMetrics(gameOverFont).getHeight()/2);
        g.drawString(restart, (BOARD_WIDTH/2)- getFontMetrics(gameOverFont).stringWidth(gameOver)+20,
                BOARD_HEIGHT/2+getFontMetrics(gameOverFont).getHeight()/2);
    }

    /**
     * Updates all the element of the game.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            updateMissiles();
            updateEnemies();
            updateBlocks();

            checkCollisions();
            isGameOver();
            enemyShooter();
            updatePlayerOutOfBounds();
        }
        repaint();
    }

    /*
    Uses a basic counter to make a random enemy shoot
     */
    private void enemyShooter() {
        if (count == 50) {
            count = 0;
            // Pick a random enemy to shoot
            int randomNum = ThreadLocalRandom.current().nextInt(0, enemyList.getEnemies().size());
            enemyList.get(randomNum).fire();
        } else {
            count++;
        }
    }

    /*
    Make sure the player can't move off the board
     */
    private void updatePlayerOutOfBounds() {
        if (player.isOutOfBoundsRight() && player.isLeftPressed()) {
            player.setAllowMoveRight(true);
            updatePlayer();
        } else if (player.isOutOfBoundsRight()) {
            player.setAllowMoveRight(false);
            player.setAllowMoveLeft(true);
        } else if (player.isOutOfBoundsLeft() && player.isRightPressed()) {
            player.setAllowMoveLeft(true);
            updatePlayer();
        } else if (player.isOutOfBoundsLeft()) {
            player.setAllowMoveLeft(false);
            player.setAllowMoveRight(true);
        } else {
            updatePlayer();
        }
    }

    /*
    Remove missiles that have collided / aren't on the screen anymore
     */
    private void updateMissiles() {
        for (int i = 0; i < player.getMissiles().size(); i++) {
            Missile m = player.getMissiles().get(i);

            if (m.isVisible()) {
                m.move(true);
            } else {
                player.getMissiles().remove(i);
            }
        }

        for (Enemy e : enemyList.getEnemies()) {
            for (int i = 0; i < e.getMissiles().size(); i++) {
                Missile m = e.getMissiles().get(i);

                if (m.isVisible()) {
                    m.move(false);
                } else {
                    e.getMissiles().remove(i);
                }
            }
        }
    }

    /*
    Update the player position
     */
    private void updatePlayer() {
        player.move();
    }

    /*
    Remove any ememies that have been killed and the move the others
     */
    private void updateEnemies() {
        for (int i = 0; i < enemyList.getEnemies().size(); i++) {
            Enemy e = enemyList.get(i);

            if (!e.isVisible()) {
                enemyList.remove(e);
            }
        }
        if (enemyList.getEnemies().size() > 0) {
            enemyList.move();
        }

    }

    /*
    Remove any blocks that have been destroyed
     */
    private void updateBlocks() {
        for (int i = 0; i < blockList.getBlocks().size(); i++) {
            Block b = blockList.get(i);

            if (!b.isVisible()) {
                blockList.remove(b);
            }
        }
    }

    /*
    Check if the game is over
     */
    private boolean isGameOver() {
        if (enemyList.getEnemies().size() == 0 || isGameOver || playerLives == 0 ||
                enemyList.getBounds().getY()+enemyList.getBounds().getHeight() > 160) {
            isGameOver = true;
            return true;
        }
        return false;
    }

    /*
    Reset the game back to how it was at the start, called after game is over
     */
    private void resetGame() {
        enemyList.getEnemies().clear();
        player.getMissiles().clear();
        blockList.getBlocks().clear();
        player = null;
        player = new Player();
        initEnemies();
        initBlocks();
        isGameOver = false;
        playerLives = 3;
    }

    /*
    Detect key-presses & react to them
     */
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (movementEnabled) {
                player.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (movementEnabled) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_SPACE) {
                    if (isGameOver) {
                        resetGame();
                    } else {
                        player.keyPressed(e);
                    }
                } else {
                    player.keyPressed(e);
                }
            }

        }
    }
}
