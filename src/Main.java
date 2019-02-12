/**
 * Created by Wojciech on 27/01/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run () {
                Game game = new Game();
                game.start();
            }
        });










    }


}
