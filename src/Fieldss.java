import javax.swing.*;

/**
 * Created by Wojciech on 08/02/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public abstract class Fieldss extends JButton {

   abstract void setxyPos(int x, int y);
   abstract int getxPos();
   abstract int getyPos();
   abstract boolean isShip();
//   abstract void setDestroyed();
//   abstract boolean isDestroyed();
   abstract void setShoot();
   abstract boolean isShoot();
   abstract void setShipType(Ships ship);
   abstract Ships getShipType();
}
