/**
 * Created by Wojciech on 27/01/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public enum Ships {
    BATTLESHIP("Battleship",1,4), CRUISER("Cruiser",2,3), SUBMARINE("Submarine",3,2),
    DESTROYER("Destroyer",4,1);

    String name;
    private int quantity;
    private int fields;
    private int lives;

    Ships(String name, int quantity, int fields ){
        this.name = name;
        this.quantity = quantity;
        this.fields = fields;
        this.lives = fields * quantity * 2;
    }


    public int getQuantity () {
        return quantity;
    }

    public int getFields () {
        return fields;
    }

    public void decreasLives() {
        this.lives = lives - 1;
    }

    public int getLives(){
        return this.lives;
    }

    @Override
    public String toString(){
        return name + "(" + fields + ")";
    }

}
