import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Wojciech on 08/02/2019.
 * It can be changed at Edit | Settings | File and Code Templates.
 */
public class Game {

    private HumanPlayer humanPlayer1;
    private HumanPlayer humanPlayer2;
    private JLabel battleShip;
    private JLabel destroyer;
    private JLabel cruiser;
    private JLabel submarine;

    private JFrame window;
    private JFrame jFrame;
    private JFrame temp;
//    private JButton p1Button;
//    private JButton p2Button;
    private HumanPlayer currentSessionHolder;
    private HumanPlayer nextPlayer;
//    private boolean nextMove;
    private boolean p1Turn;
    private boolean win;



    Game () {
        this.humanPlayer1 = new HumanPlayer("Wojtek");
        this.humanPlayer2 = new HumanPlayer("Rafal");
        this.win = false;
        this.currentSessionHolder = null;
        this.nextPlayer = null;
        p1Turn = true;

    }

    /**
     * Function responsible for starting a game, starting window will appear.
     */
    public void start () {
        window = new JFrame("choose player");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(400, 100));
        window.setLayout(new FlowLayout());

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JButton p1Button = new JButton("Player 1");
        JButton p2Button = new JButton("Player 2");
        JButton play = new JButton("Play!");


        p1Button.addMouseListener(p1Handler);
        p2Button.addMouseListener(p2Handler);
        play.addMouseListener(playHandler);


        window.add(p1Button);
        window.add(p2Button);
        window.add(play);

        window.add(container);
        window.setVisible(true);
    }

    /**
     * Function responsible for initializing player 1 board and removing old MouseListeners.
     */
    private void initializeP1 () {
        humanPlayer1.setEnemyBoard(humanPlayer2.getPlayerBoard());
        humanPlayer1.getPlayerBoard().removeListener();
        humanPlayer1.getEnemyBoard().removeListener();
    }

    /**
     * Function responsible for initializing player 2 board and removing old MouseListeners.
     */
    private void initializeP2 () {
        humanPlayer2.setEnemyBoard(humanPlayer1.getPlayerBoard());
        humanPlayer2.getEnemyBoard().removeListener();
        humanPlayer2.getPlayerBoard().removeListener();
    }

    /**
     * Showing main window of the game for specific player.
     * @param player - get player witch turn is now.
     */
    private void playerBoard (HumanPlayer player) {
        jFrame = new JFrame(player.toString());
        jFrame.setSize(820, 600);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        jPanel.add(eBoard(player));
        jPanel.add(pBoard(player));

        JButton jButton = new JButton("next Turn");
        jButton.addMouseListener(tempWindowHandler);
        jPanel.add(jButton);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel,BoxLayout.X_AXIS));
        lowerPanel.add(combatLogs(player));
        lowerPanel.add(shipsLeft());

        JPanel wholePanel = new JPanel();
        wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
        wholePanel.add(jPanel);
        wholePanel.add(lowerPanel);

        jFrame.add(wholePanel);
        jFrame.setVisible(true);

    }

    /**
     * Initialization current player board.
     * @param player - current player.
     * @return - JPanel witch contain current player board.
     */
    private JPanel pBoard (HumanPlayer player) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(10, 10));
        jPanel.setMaximumSize(new Dimension(400,400));

        player.getPlayerBoard().paint();

        for (int i = 0; i < player.getPlayerBoard().getBoard().length; i++) {
            for (int j = 0; j < player.getPlayerBoard().getBoard()[0].length; j++) {
                //player.getPlayerBoard().getBoard()[i][j].addMouseListener(ifShip);
                player.getPlayerBoard().getBoard()[i][j].setEnabled(false);
                player.getPlayerBoard().getBoard()[i][j].removeMouseListener(shoot);

//                if (player.getPlayerBoard().getBoard()[i][j].isShip()) {
//                    player.getPlayerBoard().getBoard()[i][j].setBackground(Color.GREEN);
//                }

                jPanel.add(player.getPlayerBoard().getBoard()[i][j]);
            }
        }

        return jPanel;
    }


    /**
     * Initialization enemy player board.
     * @param player - enemy player.
     * @return - JPanel witch contain enemy player board.
     */
    private JPanel eBoard (HumanPlayer player) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(10, 10));
        jPanel.setMaximumSize(new Dimension(400,400));

        player.getEnemyBoard().paint();

        for (int i = 0; i < player.getEnemyBoard().getBoard().length; i++) {
            for (int j = 0; j < player.getEnemyBoard().getBoard()[0].length; j++) {
                // player.getEnemyBoard().getBoard()[i][j].addMouseListener(ifShip);
                if(!player.getEnemyBoard().getBoard()[i][j].isShoot()) {
                    player.getEnemyBoard().getBoard()[i][j].setBackground(Color.BLUE);
                }else if(player.getEnemyBoard().getBoard()[i][j].isShip() && player.getEnemyBoard().getBoard()[i][j].isShoot()) {
                    player.getEnemyBoard().getBoard()[i][j].setBackground(Color.RED);
                } else {
                    player.getEnemyBoard().getBoard()[i][j].setBackground(Color.GRAY);
                }

                //player.getEnemyBoard().getBoard()[i][j].setBackground(Color.GRAY);

                player.getEnemyBoard().getBoard()[i][j].addMouseListener(shoot);
                player.getEnemyBoard().getBoard()[i][j].setEnabled(true);
                jPanel.add(player.getEnemyBoard().getBoard()[i][j]);
            }
        }
        return jPanel;
    }

    /**
     * Initialization of the combat logs for specific player.
     * @param player - specific player.
     * @return - JPanel witch contain scrollable text area.
     */
    private JPanel combatLogs(HumanPlayer player) {
        JPanel jPanel = new JPanel();
        JTextArea jTextArea = player.getTextArea();
        jTextArea.setSize(600,200);
        jTextArea.setBorder(BorderFactory.createMatteBorder(-1,-1,-1,-1,Color.BLACK));
        jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jTextArea.setMargin(new Insets(10,10,10,10));
        jTextArea.setEditable(false);
        jTextArea.setFont(jTextArea.getFont().deriveFont(15f));
        //jTextArea.append("WELLCOME: " + player + " AT THE BATTLEFIELD!" + "\r\n");


        JScrollPane scroll = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       scroll.setSize(600,200);
       scroll.setPreferredSize(new Dimension(600,200));
        scroll.setMaximumSize(new Dimension(600,200));

        jPanel.add(scroll);
        return jPanel;
    }

    /**
     * Initialization of the area that shows enemy ships we still needs to destroy.
     * @return - JPanel witch contain labels with info about enemy ship.
     */
    private JPanel shipsLeft() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
        destroyer = new JLabel(Ships.DESTROYER  +" to annihilate: " + Ships.DESTROYER.getQuantity());
        battleShip = new JLabel(Ships.BATTLESHIP + " to annihilate: " + Ships.BATTLESHIP.getQuantity());
        cruiser = new JLabel(Ships.CRUISER + " to annihilate : " + Ships.CRUISER.getQuantity());
        submarine = new JLabel(Ships.SUBMARINE +" to annihilate: " + Ships.SUBMARINE.getQuantity());

        jPanel.add(destroyer);
        jPanel.add(submarine);
        jPanel.add(cruiser);
        jPanel.add(battleShip);
        jPanel.setSize(200,200);
        jPanel.setMinimumSize(new Dimension(200,200));
        jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return jPanel;
    }

    /**
     * Update of specific player labels.
     * @param player - player whose labels should get updated.
     */
    private void updateLabels(HumanPlayer player) {
        destroyer.setText(Ships.DESTROYER  +" to annihilate: " + player.getPlayerBoard().getDestroyerNo());
        battleShip.setText(Ships.BATTLESHIP + " to annihilate: " + player.getPlayerBoard().getBattleshipNo());
        cruiser.setText(Ships.CRUISER + " to annihilate : " + player.getPlayerBoard().getCruiserNo());
        submarine.setText(Ships.SUBMARINE +" to annihilate: " + player.getPlayerBoard().getSubmarineNo());
    }

    /**
     * Win condition.
     */
    private void checkWin() {
        if(nextPlayer.getLives() == 0) {
            System.out.println("WIN - " + currentSessionHolder + "!");
            win = true;
            //jFrame.setVisible(false);
            addText(currentSessionHolder,"YOU HAVE WON!");
            //start();
        }
    }

    /**
     * Add text to the player text area.
     * @param player - player whose text area should be updated of given text.
     * @param string - string witch should be added.
     */
    private void addText(Player player,String string){

        player.getTextArea().append(string + "\r\n");
    }

    /**
     *
     * @param fieldss - field where we shoot.
     * @return - string that depends on field we have shoot.
     */
    private String isShip(Fieldss fieldss){
        if(fieldss.isShip()) {
            return currentSessionHolder.getTextArea().getLineCount() +". SHIP: " + fieldss.getShipType() + "\t" +
                    "YOU HAVE NEXT MOVE! ";
        } else{
            return currentSessionHolder.getTextArea().getLineCount() +". MISS!"+"\t\t" +" END OF YOUR TURN! ";
        }
    }

//    private void checkShip(Object o) {
//        if(o instanceof Ships) {
//           ((Ships) o).setFields();
//        }
//    }

    /**
     * Check if ship we are bombarding got destroyed.
     * @param o - Object we shoot at.
     */
    private void checkIfDestroyed(Object o) {
        if(o instanceof Ships) {
            Ships ships = (Ships)o;
            ((Ships)o).decreasLives();

            switch (ships) {
                case DESTROYER:
                    addText(currentSessionHolder,Ships.DESTROYER+"\t" + " DESTROYED!");
                    currentSessionHolder.getPlayerBoard().setDestroyerNo();
                    break;

                case SUBMARINE:
                    if(ships.getLives()%2 ==0) {
                        addText(currentSessionHolder,Ships.SUBMARINE+"\t" + " DESTROYED!");
                        currentSessionHolder.getPlayerBoard().setSubmarineNo();
                    }
                    break;

                case CRUISER:
                    if(ships.getLives()%3 == 0) {
                        addText(currentSessionHolder, Ships.CRUISER+"\t" + " DESTROYED!");
                        currentSessionHolder.getPlayerBoard().setCruiserNo();
                    }
                    break;

                case BATTLESHIP:
                    if(ships.getLives()%4 == 0) {
                        addText(currentSessionHolder, Ships.BATTLESHIP +"\t" + " DESTROYED!");
                        currentSessionHolder.getPlayerBoard().setBattleshipNo();
                    }
                    break;
            }

        }
    }

    /**
     * Buffer between next player turn.
     */
    private void temporaryWindow() {
        temp = new JFrame();
        temp.setSize(820,600);
        temp.setResizable(false);
        JButton jButton = new JButton("Player: " + nextPlayer + " turn!");
        jButton.addMouseListener(nextTurn);
        jButton.setFont(jButton.getFont().deriveFont(30f).deriveFont(Font.BOLD));

        temp.add(jButton);
        temp.setVisible(true);
    }


    //-------------------------------------------------------------------------------------------------------
    //-----------------------------------------BUTTONS!!-----------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    private MouseListener p1Handler = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
            if (!humanPlayer1.getPlayerBoard().isFinished()) {
                humanPlayer1.getPlayerBoard().setupStage();
                humanPlayer1.getPlayerBoard().repaintBoard();
            }
        }
    };

    private MouseListener p2Handler = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
            if (!humanPlayer2.getPlayerBoard().isFinished()) {
                humanPlayer2.getPlayerBoard().setupStage();
                humanPlayer2.getPlayerBoard().repaintBoard();
            }
        }
    };

    private MouseListener playHandler = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            if (humanPlayer1.getPlayerBoard().isFinished() && humanPlayer2.getPlayerBoard().isFinished() ) {
                super.mouseClicked(e);
                initializeP1();
                initializeP2();

                humanPlayer1.show();
                humanPlayer2.show();
                //humanPlayer1.getPlayerBoard().showHelp();

                playerBoard(humanPlayer1);
                currentSessionHolder = humanPlayer1;
                nextPlayer = humanPlayer2;
                p1Turn = false;

                window.setVisible(false);

            }

        }
    };

    private MouseListener nextTurn = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);



            if(!currentSessionHolder.getHit() && !win) {
                if (p1Turn) {
                    jFrame.setVisible(false);
                    playerBoard(humanPlayer1);
                    currentSessionHolder = humanPlayer1;
                    currentSessionHolder.setHit();
                    nextPlayer = humanPlayer2;
                    p1Turn = !p1Turn;
                    temp.setVisible(false);
                } else {
                    jFrame.setVisible(false);
                    playerBoard(humanPlayer2);
                    currentSessionHolder = humanPlayer2;
                    currentSessionHolder.setHit();
                    nextPlayer = humanPlayer1;
                    p1Turn = !p1Turn;
                    temp.setVisible(false);

                }
            }
        }
    };

    private MouseListener tempWindowHandler = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
            if(!currentSessionHolder.getHit() && !win) {
                if (p1Turn) {
                    jFrame.setVisible(false);
                    temporaryWindow();
                    updateLabels(nextPlayer);
                } else {
                    jFrame.setVisible(false);
                    temporaryWindow();
                    updateLabels(nextPlayer);

                }
            }
        }
    };


    private MouseListener shoot = new MouseAdapter() {
        @Override
        public void mouseClicked (MouseEvent e) {
            super.mouseClicked(e);
                Fieldss fieldss = (Fieldss) e.getSource();
                if (fieldss.isShip()) {
                    System.out.println("SHIP!");
                } else {
                    System.out.println("SEA!");
                }

                int xPos = fieldss.getxPos();
                int yPos = fieldss.getyPos();

                System.out.println(currentSessionHolder + " X: " + xPos + " Y:" + yPos);


                if (currentSessionHolder.getHit() && !currentSessionHolder.getEnemyBoard().getBoard()[yPos][xPos].isShoot() && !win) {
                    currentSessionHolder.shootAt(xPos, yPos, nextPlayer);
                    //checkShip(fieldss.getShipType());
                    addText(currentSessionHolder,currentSessionHolder +": " + isShip(fieldss) );
                    checkIfDestroyed(fieldss.getShipType());

                    checkWin();
                }

            updateLabels(currentSessionHolder);
        }
    };




}
