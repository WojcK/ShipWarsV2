import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Wojciech on 26/01/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public class PlayerBoard {

    private Fieldss[][] fields;

    private Ships shipsEnumAlone;
    private Placement placementEnumAlone;

    private int destroyerNo;
    private int battleshipNo;
    private int cruiserNo;
    private int submarineNo;
    private ArrayList<Ships> shipsArrayList;
    private Placement[] placements;

    //-----SETUP----
    private JComboBox positon;
    private JComboBox ships;
    private JFrame setupStage;
    private boolean finished;

    private JLabel destroyerLabel;
    private JLabel submarineLabel;
    private JLabel cruiserLabel;
    private JLabel battleshipLabel;

    PlayerBoard(){
        shipsArrayList = new ArrayList<>();
        addToArrayList(shipsArrayList);
       // printArrayList(shipsArrayList);

        placements = Placement.values();

        this.destroyerNo = 0;
        this.submarineNo = 0;
        this.battleshipNo = 0;
        this.cruiserNo = 0;
        this.finished = false;
        this.fields = new Fieldss[10][10];
        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {
                fields[i][j] = new WaterField();
                fields[i][j].setxyPos(j,i);
                fields[i][j].addMouseListener(getPosition);
            }
        }
    }

    PlayerBoard(PlayerBoard playerBoard) {
        this.fields = playerBoard.fields;
        this.destroyerNo = playerBoard.destroyerNo;
        this.submarineNo = playerBoard.submarineNo;
        this.battleshipNo = playerBoard.battleshipNo;
        this.cruiserNo = playerBoard.cruiserNo;
        this.finished = playerBoard.finished;
    }

    //------------------------------------SETUP STAGE-----------------------------------------------
    //----------------------------------------------------------------------------------------------


    public void setupStage(){

        positon = new JComboBox(Placement.values());
        positon.setBounds(50,50,90,20);

        ships = new JComboBox(Ships.values());


        setupStage = new JFrame("Setup Stage");
        //setupStage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupStage.setSize(new Dimension(800,400));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));

        container.add(boardSetupArea());
        container.add(middleArea());
        container.add(rightRightArea());

        setupStage.add(container);
        setupStage.setVisible(true);
    }

    private void finishSetup() {
        setupStage.setVisible(false);
    }

    private JPanel boardSetupArea() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(10,10));
        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {
                jPanel.add(fields[i][j]);
            }
        }

//        for(int i = 0; i < fields.length; i++) {
//            for(int j = 0; j < fields[0].length; j++) {
//                fields[i][j].addMouseListener(getPosition);
//            }
//        }
        return jPanel;
    }

    private JPanel middleArea() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
        //jPanel.add(placeShip4());
        jPanel.add(positon);
        jPanel.add(ships);

        return jPanel;
    }

    private JPanel rightRightArea() {

        JPanel jPanel = new JPanel();
        jPanel.setLayout((new BoxLayout(jPanel,BoxLayout.Y_AXIS)));

        destroyerLabel = new JLabel();
        destroyerLabel.setText("Destroyers placed: " + destroyerNo);

        cruiserLabel = new JLabel();
        cruiserLabel.setText("Cruiser placed: " + cruiserNo);

        submarineLabel = new JLabel();
        submarineLabel.setText("Submarine placed: " + submarineNo);

        battleshipLabel = new JLabel();
        battleshipLabel.setText("Battleship placed: " + battleshipNo);

        JButton finishB = new JButton("Finish");
        finishB.addMouseListener(finishButton);
//        finishB.setOpaque(false);
//        finishB.setBorderPainted(false);
//        finishB.setContentAreaFilled(false);

        JButton clear = new JButton("Clear");
        clear.addMouseListener(clearButton);
//        clear.setOpaque(false);
//        clear.setContentAreaFilled(false);
//        clear.setBorderPainted(false);

        JButton auto = new JButton("Auto");
        auto.addMouseListener(autoHandler);


        jPanel.add(destroyerLabel);
        jPanel.add(submarineLabel);
        jPanel.add(cruiserLabel);
        jPanel.add(battleshipLabel);
        jPanel.add(finishB);
        jPanel.add(clear);
        jPanel.add(auto);

        return jPanel;
    }





    private void clearBoard() {
        for(int i =0; i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {
                fields[i][j] = new WaterField();
                fields[i][j].setxyPos(j,i);
                fields[i][j].addMouseListener(getPosition);
            }
        }

        destroyerNo = 0;
        submarineNo = 0;
        cruiserNo = 0;
        battleshipNo = 0;

        //setupStage.dispatchEvent(new WindowEvent(setupStage, WindowEvent.WINDOW_CLOSING));

    }

    public boolean isFinished () {
        return finished;
    }

    public void setFinished (boolean finished) {
        this.finished = finished;
    }

    public void repaintBoard() {
        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {
                if(fields[i][j].isShip()) {
                    fields[i][j].setBackground(Color.GREEN);
                } else {
                    fields[i][j].setBackground(Color.BLUE);
                }
                fields[i][j].repaint();
            }
        }

    }


//-------------------------------------END SETUP-STAGE --------------------------------------------
//--------------------------------------------------------------------------------------------------


    public void showHelp(){
        for(int i = 0;  i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {
                if(fields[i][j].isShip()) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    public Fieldss[][] getBoard() {
        return fields;
    }

    public void removeListener() {
        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {
                fields[i][j].removeMouseListener(getPosition);
            }
        }
    }

    public void paint(){
        for(int i = 0; i < fields.length; i++) {
            for(int j = 0; j < fields[0].length; j++) {

                if(fields[i][j].isShip() && fields[i][j].isShoot()) {
                    fields[i][j].setBackground(Color.RED);
                }

                if(fields[i][j].isShip() && !fields[i][j].isShoot()) {
                    fields[i][j].setBackground(Color.GREEN);
                }

                if(!fields[i][j].isShip() && fields[i][j].isShoot()) {
                    fields[i][j].setBackground(Color.BLACK);
                }

                if(!fields[i][j].isShip() && !fields[i][j].isShoot()) {
                    fields[i][j].setBackground(Color.BLUE);
                }

            }
        }
    }

    private void addToArrayList(ArrayList<Ships> arrayList) {
        for (Ships ships: Ships.values()) {
            for(int i = 0; i < ships.getQuantity(); i++) {
                arrayList.add(ships);
            }
        }
    }

    private void printArrayList(ArrayList<Ships> arrayList) {
        //arrayList.forEach((n) -> addShipRandom(n));
    }


    private void addShipRandom(ArrayList<Ships> arrayList) {

        //TODO

        arrayList.forEach(ships -> {


            int random = ThreadLocalRandom.current().nextInt(0,2);
            Placement placement = placements[random];

            int randX = ThreadLocalRandom.current().nextInt(0,10);
            int randY = ThreadLocalRandom.current().nextInt(0,10);

            while (!inBoundaries(randX,randY,ships,placement)
                    || checkShipsNearby(randX,randY,ships,placement)
                    || addAndCheckQuantity(ships)) {

                randX = ThreadLocalRandom.current().nextInt(0,10);
                randY = ThreadLocalRandom.current().nextInt(0,10);
                random = ThreadLocalRandom.current().nextInt(0,2);
                placement = placements[random];

                System.out.println(ships + ""+placement + " X:" + randX + " Y:" + randY);

            }

                if (placement == Placement.HORIZONTAL) {
                    for (int i = 0; i < ships.getFields(); i++) {
                        fields[randY][randX + i] = new ShipField(fields[randY][randX + i], ships);
                    }
                }

                if (placement == Placement.VERTICAL) {
                    for (int i = 0; i < ships.getFields(); i++) {
                        fields[randY + i][randX] = new ShipField(fields[randY + i][randX], ships);
                    }

                }

            System.out.println(ships + ""+placement + " X:" + randX + " Y:" + randY + " PLACED!");

            paint();
        });
    }

    private boolean inBoundaries(int xPos, int yPos, Ships ships, Placement placement) {
        if(placement == Placement.HORIZONTAL) {
            return (xPos + ships.getFields()) <=10;
        }
        if(placement == Placement.VERTICAL) {
            return (yPos + ships.getFields()) <= 10;
        }
        return true;
    }

    private boolean checkShipsNearby(int xPos, int yPos, Ships ships, Placement placement) {
        if(placement == Placement.HORIZONTAL) {
            for (int i = 0; i < ships.getFields(); i++) {
                for (int ii = -1; ii <= 1; ii++) {
                    for (int jj = -1; jj <= 1; jj++) {

                        if((yPos + ii > 9) || (xPos +i+ jj > 9) || (yPos + ii < 0) || (xPos + jj < 0) )
                            continue;

                        if (fields[yPos + ii][xPos +i+ jj].isShip()) {
                            //System.out.println("SHIPS NEARBY!");
                            return true;
                        }
                    }
                }
            }
        }

        if(placement == Placement.VERTICAL) {
            for(int i = 0; i < ships.getFields();i++) {

                for(int ii = -1; ii <= 1; ii++) {
                    for(int jj = -1; jj<= 1; jj++) {

                        if((yPos +i+ ii > 9) || (xPos + jj > 9) || (yPos + ii < 0) || (xPos + jj < 0) )
                            continue;

                        if(fields[yPos + i + ii][xPos + jj].isShip()) {
                            return true;
                        }
                    }
                }
            }
        }
        return  false;
    }

    private boolean addAndCheckQuantity(Ships ships) {
        switch (ships) {
            case CRUISER:
                if (cruiserNo >= Ships.CRUISER.getQuantity()) {
                    return false;
                }
                cruiserNo++;
                cruiserLabel.setText("Cruiser placed: " + cruiserNo);
                break;

            case DESTROYER:
                if (destroyerNo >= Ships.DESTROYER.getQuantity()) {
                    return false;
                }
                destroyerNo++;
                destroyerLabel.setText("Destroyer placed: " + destroyerNo);
                break;

            case SUBMARINE:
                if (submarineNo >= Ships.SUBMARINE.getQuantity()) {
                    return false;
                }
                submarineNo++;
                submarineLabel.setText("Submarine placed: " + submarineNo);
                break;

            case BATTLESHIP:
                if (battleshipNo >= Ships.BATTLESHIP.getQuantity()) {
                    return false;
                }
                battleshipNo++;
                battleshipLabel.setText("Battleship placed: " + battleshipNo);
                break;
        }
        return true;
    }

    public int getDestroyerNo () {
        return destroyerNo;
    }

    public int getBattleshipNo () {
        return battleshipNo;
    }

    public int getCruiserNo () {
        return cruiserNo;
    }

    public int getSubmarineNo () {
        return submarineNo;
    }

    public void setDestroyerNo () {
        this.destroyerNo = destroyerNo - 1;
    }

    public void setBattleshipNo () {
        this.battleshipNo = battleshipNo - 1;
    }

    public void setCruiserNo () {
        this.cruiserNo = cruiserNo - 1;
    }

    public void setSubmarineNo () {
        this.submarineNo = submarineNo - 1;
    }


    //-------------------------------------------------------------------------------------------------------
//-----------------------------------------BUTTONS!!-----------------------------------------------------
//-------------------------------------------------------------------------------------------------------


    private MouseListener finishButton = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
            int num = 0;

            for(int i = 0; i < fields.length; i++) {
                for(int j = 0; j < fields[0].length; j++) {
                    if(fields[i][j].isShip()) {
                        num++;
                    }
                }
            }

            if(num == 20) {
                finishSetup();
                showHelp();
                setFinished(true);
                removeListener();
            }
        }
    };

    private MouseListener clearButton = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
            clearBoard();
        }
    };


    private MouseListener getPosition = new MouseAdapter() {
        @Override
        public void mouseEntered (MouseEvent e) {
            Fieldss jButton = (Fieldss) e.getSource();
            int xpos = jButton.getxPos();
            int ypos = jButton.getyPos();

            // System.out.println("x = " + xpos);
            //System.out.println("y = " + ypos);

            shipsEnumAlone =  (Ships) ships.getItemAt(ships.getSelectedIndex());
            placementEnumAlone = (Placement) positon.getItemAt(positon.getSelectedIndex());

            //System.out.println(shipsEnumAlone);
            //System.out.println();


            if(fields[ypos][xpos].isShip()) {
                System.out.println("SHIP!");
            }

            if(placementEnumAlone == Placement.HORIZONTAL
                    && inBoundaries(xpos,ypos,shipsEnumAlone,placementEnumAlone)
                    //&& !fields[xpos][ypos].isShip()
                    && !checkShipsNearby(xpos,ypos,shipsEnumAlone,placementEnumAlone)
            ) {
                for (int i = 0; i < ((Ships) ships.getItemAt(ships.getSelectedIndex())).getFields(); i++) {
                    fields[ypos][xpos + i].setBackground(Color.GREEN);
                }
            }

            if(placementEnumAlone == Placement.VERTICAL
                    && inBoundaries(xpos,ypos,shipsEnumAlone,placementEnumAlone)
                    //&& !fields[xpos][ypos].isShip()
                    && !checkShipsNearby(xpos,ypos,shipsEnumAlone,placementEnumAlone)) {
                for (int i = 0; i < ((Ships) ships.getItemAt(ships.getSelectedIndex())).getFields(); i++) {
                    fields[ypos + i][xpos].setBackground(Color.GREEN);

                }
            }
        }

        @Override
        public void mouseExited (MouseEvent e) {

            Fieldss jButton = (Fieldss) e.getSource();
            int xpos = jButton.getxPos();
            int ypos = jButton.getyPos();

            //System.out.println("x = " + xpos);
            //System.out.println("y = " + ypos);

            shipsEnumAlone = (Ships) ships.getItemAt(ships.getSelectedIndex());
            placementEnumAlone = (Placement) positon.getItemAt(positon.getSelectedIndex());

            if (placementEnumAlone == Placement.HORIZONTAL) {
                for (int i = 0; i < ((Ships) ships.getItemAt(ships.getSelectedIndex())).getFields(); i++) {
                    if (inBoundaries(xpos, ypos, shipsEnumAlone, placementEnumAlone)) {
                        fields[ypos][xpos + i].setBackground(Color.BLUE);
                    }
                }
            }

            if (placementEnumAlone == Placement.VERTICAL) {
                for (int i = 0; i < ((Ships) ships.getItemAt(ships.getSelectedIndex())).getFields(); i++) {
                    if (inBoundaries(xpos, ypos, shipsEnumAlone, placementEnumAlone)) {
                        fields[ypos + i][xpos].setBackground(Color.BLUE);
                    }
                }
            }
        }

        @Override
        public void mouseClicked (MouseEvent e){
            Fieldss jButton = (Fieldss) e.getSource();
            int xpos = jButton.getxPos();
            int ypos = jButton.getyPos();

            //System.out.println("x = " + xpos);
            //System.out.println("y = " + ypos);

            shipsEnumAlone =  (Ships) ships.getItemAt(ships.getSelectedIndex());
            placementEnumAlone = (Placement) positon.getItemAt(positon.getSelectedIndex());

            if (placementEnumAlone == Placement.HORIZONTAL
                    && inBoundaries(xpos,ypos,shipsEnumAlone,placementEnumAlone)
                    //&& !fields[xpos][ypos].isShip()
                    && !checkShipsNearby(xpos,ypos,shipsEnumAlone,placementEnumAlone)
                    && addAndCheckQuantity(shipsEnumAlone) ) {
                for (int i = 0; i < ((Ships) ships.getItemAt(ships.getSelectedIndex())).getFields(); i++) {
                    fields[ypos][xpos + i] = new ShipField(fields[ypos][xpos + i],shipsEnumAlone);
                }
            }

            if (placementEnumAlone == Placement.VERTICAL
                    && inBoundaries(xpos,ypos,shipsEnumAlone,placementEnumAlone)
                    && !checkShipsNearby(xpos,ypos,shipsEnumAlone,placementEnumAlone)
                    && addAndCheckQuantity(shipsEnumAlone)) {

                for (int i = 0; i < ((Ships) ships.getItemAt(ships.getSelectedIndex())).getFields(); i++) {
                    fields[ypos + i][xpos] = new ShipField(fields[ypos + i][xpos],shipsEnumAlone);
                }
            }

            //setupStage.repaint();


        }
    };

    private MouseListener autoHandler = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
            clearBoard();
            addShipRandom(shipsArrayList);

        }
    };
}




