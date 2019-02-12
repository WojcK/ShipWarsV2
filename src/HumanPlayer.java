import javax.swing.*;
import java.awt.*;

/**
 * Created by Wojciech on 26/01/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public class HumanPlayer implements Player{

    private PlayerBoard playerBoard;
    private PlayerBoard enemyBoard;
    private JTextArea textArea;
    private String name;
    private boolean hit;
    private int lives;

    HumanPlayer(String name) {
        this.playerBoard = new PlayerBoard();
        this.enemyBoard = new PlayerBoard();
        this.name = name;
        this.hit = true;
        this.lives = 20;
        this.textArea = new JTextArea();
    }

    /**
     *
     * @param x - x position of shot;
     * @param y - y posioton of shot;
     * @param player - players witch is going to be shot;
     */
    @Override
    public void shootAt (int x, int y, Player player) {
        if(enemyBoard.getBoard()[y][x].isShip() && !enemyBoard.getBoard()[y][x].isShoot()) {
            enemyBoard.getBoard()[y][x].setShoot();
            enemyBoard.getBoard()[y][x].setBackground(Color.RED);
            enemyBoard.getBoard()[y][x].setEnabled(false);
            player.decrementLives();
            //System.out.println("HIT! " + player.getLives());

        }

        if(!enemyBoard.getBoard()[y][x].isShip() && !enemyBoard.getBoard()[y][x].isShoot()) {
            enemyBoard.getBoard()[y][x].setShoot();
            enemyBoard.getBoard()[y][x].setBackground(Color.GRAY);
            enemyBoard.getBoard()[y][x].setEnabled(false);
            //System.out.println("MISS! " + player.getLives());
            this.hit = false;
            //System.out.println("END OF THE TURN!");
        }

        System.out.println();
        System.out.println();

    }

    public PlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    public PlayerBoard getEnemyBoard() {
        return this.enemyBoard;
    }

    public void setEnemyBoard (PlayerBoard enemyBoard) {
        this.enemyBoard = enemyBoard;
    }

    public void show() {
        System.out.println("Player Board: ");
        playerBoard.showHelp();
        System.out.println();

        System.out.println("Enemy board: ");
        enemyBoard.showHelp();
    }

    @Override
    public String toString () {
        return name;
    }

    @Override
    public int getLives () {
        return lives;
    }

    @Override
    public void setHit () {
        this.hit = true;
    }

    @Override
    public void decrementLives(){
        this.lives = lives - 1;
    }

    @Override
    public JTextArea getTextArea () {
        return textArea;
    }

    @Override
    public boolean getHit () {
        return hit;
    }


}
