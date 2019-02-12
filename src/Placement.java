/**
 * Created by Wojciech on 07/02/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public enum Placement {
    HORIZONTAL("horizontal"),VERTICAL("vertical");

    String name;

    Placement(String name){
        this.name = name;
    }

    @Override
    public String toString () {
        return name;
    }
}
