import javax.swing.*;

/**
 * Created by Wojciech on 26/01/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public interface Player {
    void shootAt(int x, int y, Player player);
    int getLives();
    void setHit();
    boolean getHit();
    void decrementLives();
    JTextArea getTextArea();

}
