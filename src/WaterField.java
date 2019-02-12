import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Wojciech on 26/01/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public class WaterField extends Fieldss{
    private boolean isShip;
    private boolean shoot;
    private int xPos;
    private int yPos;
    private Ships ships;


    WaterField() {
        this.isShip = false;
        this.shoot = false;
        this.ships = null;
        this.setPreferredSize(new Dimension(40,40));
        this.setSize(new Dimension(40,40));
        this.setBackground(Color.BLUE);
        this.setOpaque(true);
    }

    public int getxPos () {
        return xPos;
    }

    public void setxyPos (int xPos,int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getyPos () {
        return yPos;
    }

    public void setyPos (int yPos) {
        this.yPos = yPos;
    }

    @Override
    public boolean isShip () {
        return isShip;
    }

    @Override
    void setShoot () {
        this.shoot = true;
    }

    @Override
    boolean isShoot () {
        return shoot;
    }

    @Override
    void setShipType (Ships ship) {

    }

    @Override
    Ships getShipType () {
        return this.ships;
    }

}
