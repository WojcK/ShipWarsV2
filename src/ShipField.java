import java.awt.*;

/**
 * Created by Wojciech on 08/02/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public class ShipField extends Fieldss {
    private boolean isShip;
    //private boolean destroyed;
    private boolean shoot;
    private int xPos;
    private int yPos;
    private Ships ship;

    ShipField(Fieldss fieldss, Ships ship){
        this.ship = ship;
        this.isShip = true;
        this.shoot = false;
        this.xPos = fieldss.getxPos();
        this.yPos = fieldss.getyPos();
        //this.destroyed = false;
        this.setPreferredSize(new Dimension(40,40));
        this.setSize(new Dimension(40,40));
        this.setBackground(Color.GREEN);
        this.setOpaque(true);
    }


    @Override
    void setxyPos (int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    @Override
    int getxPos () {
        return xPos;
    }

    @Override
    int getyPos () {
        return yPos;
    }

    @Override
    public void setShipType(Ships ship){
        this.ship = ship;
    }

    @Override
    public Ships getShipType(){
        return  ship;
    }



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


}
