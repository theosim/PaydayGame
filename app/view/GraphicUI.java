package app.view;

import app.controller.Controller;
import app.model.card.mailCards.Bill;
import app.model.card.mailCards.MoveToDealBuyer;
import app.model.position.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

public class GraphicUI extends JFrame{

    private Controller control;
    public ClassLoader cldr;
    private JLayeredPane mainPanel;
    private Image image;
    private JPanel infoPanel, gamePanel;
    private JLabel jackpotLabel, player1Label, player2Label;
    private JPanel[] positionsPanels;
    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem menuItem;
    public Rectangle windowSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    private final int winThird = windowSize.width / 3 * 2;
    private boolean start = true, drawn2 = false;
    private int rolled1 = 1, rolled2 =1;
    private JButton mailButton, dealButton, rollDice1, rollDice2, myDealCards1,myDealCards2, getLoan1 , getLoan2, endTurn1, endTurn2;

    /**
     * <b>Constructor:</b> Creates a window where buttons, panels and text fields are added
     *
     * <b>Postcondition:</b> Creates a window where buttons, panels and text fields are added and starts a new game
     */
    public GraphicUI(){
        super();

        this.setTitle("PayDay");

        int monthsint = 0;
        JFrame frame = new JFrame();
        Object[] possibilities = {"1","2","3"};
        String months = (String)JOptionPane.showInputDialog(
                frame ,
                "Months the game with be played for:\n",
                "Set the months",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(this.getResource("images/logo.png")),
                possibilities,
                "1");

        if (!months.equals("1") && !months.equals("2") && !months.equals("3")) {
            monthsint = 1;
        }
        else if(months.equals("1")){
            monthsint = 1;
        }
        else if(months.equals("2")){
            monthsint = 2;
        }
        else{
            monthsint =3;
        }

        String name1 = (String)JOptionPane.showInputDialog(
                frame ,
                "Player1's name:\n",
                "Player1",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");

        if(name1==null || name1.length() <= 0){
            name1 = "Player 1";
        }

        String name2 = (String)JOptionPane.showInputDialog(
                frame ,
                "Player2's name:\n",
                "Player2",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");

        if(name2==null || name2.length() <= 0){
            name2 = "Player 2";
        }

        control = new Controller(name1, name2, monthsint);

        if(control.getCurrentPlayer() == 2){
            JOptionPane.showMessageDialog(frame, "Current player:"+ control.gameboard.getP2().getName());
        }
        else{
            JOptionPane.showMessageDialog(frame, "Current player:"+ control.gameboard.getP1().getName());
        }

        this.setTitle("PayDay");

        this.refreshWindowLayout();

        this.setJMenuBar(this.menuBar());

        this.setResizable(false);
        this.setPreferredSize(new Dimension(windowSize.width, windowSize.height));
        this.setMinimumSize(new Dimension(windowSize.width, windowSize.height));
        //this.addWindowListener(new WindowEventsListener());
        this.setIconImage(new ImageIcon(this.getResource("images/logo.png")).getImage());




        this.pack();
        this.setVisible(true);
    }

    /**
     * This is the menu bar of the Game.
     *
     * @return a JMenuBar that can be added in a JFrame
     */
    protected JMenuBar menuBar() {
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Game Menu");
        menu.setMnemonic(KeyEvent.VK_T);
        menu.getAccessibleContext().setAccessibleDescription("This is the main menu of the game");

        menuItem = new JMenuItem("New Game", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_DOWN_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Creates a new Game");
        menuItem.addActionListener(e -> {
            destroy();
            new GraphicUI();
        });
        menu.add(menuItem);


        menuItem = new JMenuItem("Exit", KeyEvent.VK_T);
        menuItem.addActionListener(e -> this.destroy());
        menu.add(menuItem);

        menuBar.add(menu);

        return menuBar;
    }

    public void destroy() {
        this.setVisible(false);
        this.dispose();
    }

    private void refreshWindowLayout() {

        this.getContentPane().removeAll();

        GridBagConstraints c = new GridBagConstraints();

        this.mainPanel = new JLayeredPane();

        mailButton = new JButton(resizeImg(new ImageIcon(this.getResource("images/mailCard.png")),150,90));
        mailButton.setBounds(winThird + 150 , (windowSize.height / 3)+ (windowSize.height / 3)/2 , 150 , 90 );
        mailButton.setName("Mail");

        dealButton = new JButton(resizeImg(new ImageIcon(this.getResource("images/dealCard.png")),150,90));
        dealButton.setBounds(winThird + 350 , (windowSize.height / 3)+ (windowSize.height / 3)/2 , 150 , 90 );
        dealButton.setName("Deal");

        int mailCardCount = 0, dealCardCount = 0;
        String[][] mailCards = new String[48][4];
        String[][] dealCards = new String[20][8];


        cldr = this.getClass().getClassLoader();



        mailButton.addActionListener(new CardListener());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dealButton.addActionListener(new CardListener());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        this.mainPanel.add(this.boardPanel(), JLayeredPane.DEFAULT_LAYER);
        this.mainPanel.add(dealButton, JLayeredPane.DEFAULT_LAYER);

        this.mainPanel.add(mailButton, JLayeredPane.DEFAULT_LAYER);

        mailButton.setEnabled(false);
        dealButton.setEnabled(false);
        this.mainPanel.add(this.player1Panel(), JLayeredPane.DEFAULT_LAYER);
        this.mainPanel.add(this.player2Panel(), JLayeredPane.DEFAULT_LAYER);
        this.mainPanel.add(this.infoPanel(), JLayeredPane.DEFAULT_LAYER);


        JLabel backgroundLabel = new JLabel(resizeImg(new ImageIcon(this.getResource("images/bg_green.png")), windowSize.width, windowSize.height));
        backgroundLabel.setPreferredSize(new Dimension((windowSize.width - 200), windowSize.height));
        backgroundLabel.setBounds(0, 0, windowSize.width, windowSize.height);
        this.mainPanel.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        this.add(mainPanel);

        this.revalidate();
        this.repaint();
    }

    private class Loan1Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame frame = new JFrame();
            Object[] possibilities = {"1000","2000","3000","4000","5000","6000","7000", "8000", "9000", "10000"};
            String loan = (String)JOptionPane.showInputDialog(
                    frame ,
                    "Get loan:\n",
                    "Get loan",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "1000 ");

            control.getLoan(Integer.parseInt(loan));

            refreshWindowLayout();
        }

    }

    private class Loan2Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame frame = new JFrame();
            Object[] possibilities = {"1000","2000","3000","4000","5000","6000","7000", "8000", "9000", "10000"};
            String loan = (String)JOptionPane.showInputDialog(
                    frame ,
                    "Get loan:\n",
                    "Get loan",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "1000");

            control.getLoan(Integer.parseInt(loan));

            refreshWindowLayout();
        }

    }

    private class Dice1Listener implements ActionListener{

        public void actionPerformed(ActionEvent e){

            rolled1 = control.roll();
            refreshWindowLayout();

            if (control.gameboard.getDays()[control.gameboard.getP1Pos()] == "Sunday") {
                disableAllButtons();
                JFrame frame = new JFrame();
                Object[] choices = { "Barcelona Wins", "Draw", "Real Wins", "No prediction" };
                String choice = (String) JOptionPane.showInputDialog(
                        frame,
                        "Bet 500 euro for Barcelona - Real game",
                        "Sunday's football game",
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(getResource("images/Barcelona_Real.jpg")),
                        choices,
                        "Barcelona Wins"
                        );
                if (!choice.equals("No prediction")) {
                    int roll = (int) (Math.random() * (7 - 1) + 1);
                    control.setDice1(roll);

                    if (control.gameboard.getP1().getMoney() < 500) {
                        control.getLoan(1000);
                    }
                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() - 500);

                    if ((choice.equals("Barcelona Wins") && (roll == 1 || roll == 2))
                            || (choice.equals("Draw") && (roll == 3 || roll == 4))
                            || (choice.equals("Real Wins") && (roll == 5 || roll == 6))) {
                        control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 500);
                        control.setInfoBoxMessage("You won, your bet was right!");
                    }
                    else {
                        control.setInfoBoxMessage("You lost, your bet was wrong.");
                    }
                }
            }

            if (control.gameboard.getDays()[control.gameboard.getP1Pos()] == "Thursday") {
                disableAllButtons();
                JFrame frame = new JFrame();
                Object[] choices = { "Drop", "Stable", "Increase", "Cancel" };
                String choice = (String) JOptionPane.showInputDialog(
                        frame,
                        "Crypto Thursday bet to win 300 euro",
                        "Crypto Thursday",
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(getResource("images/crypto.jpeg")),
                        choices,
                        "Drop"
                );
                if (choice != "Cancel") {
                    int roll = (int)(Math.random() * (7-1) + 1);
                    control.setDice1(roll);

                    if (roll == 1 || roll == 2) {
                        if (control.gameboard.getP1().getMoney() < 300) {
                            control.getLoan(1000);
                        }
                        control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() - 300);
                        control.setInfoBoxMessage("You lost.");
                    } else if (roll == 3 || roll == 4){
                        control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 300);
                        control.setInfoBoxMessage("You won.");
                    }
                }
            }

            if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof MailPosition) {
                disableAllButtons();
                mailButton.setEnabled(true);
                drawn2 = false;
            }
            else if((control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Deal)){
                disableAllButtons();
                dealButton.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Lottery){
                JFrame frame = new JFrame();
                Object[] possibilities = {1,2,3,4,5,6};
                int num = (int)JOptionPane.showInputDialog(
                        frame ,
                        control.gameboard.getP1().getName()+" choose a number:\n",
                        "Lottery",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        1);

                control.setDice1(num);
                control.setDice2(num);

                while(control.getDice2() == control.getDice1()) {
                    num = (int) JOptionPane.showInputDialog(
                            frame,
                            control.gameboard.getP2().getName() + " choose a number:\n",
                            "Lottery",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "1");
                    control.setDice2(num);
                }


                boolean won = false;
                while(!won){
                    int roll = (int)(Math.random() * (7-1) + 1);
                    if(roll == control.getDice1()){
                        control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 1000);
                        won = true;
                    }
                    else if(roll == control.getDice2()){
                        control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 1000);
                        won = true;
                    }
                }
                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);
            }
            else if((control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Buyer)){

                int num = control.gameboard.getP1().getMyDeals().size();

                if(num!=0) {
                    JFrame frame = new JFrame();


                    Object[] possibilities = new Object[num];

                    for (int i = 0; i < num; i++) {
                        possibilities[i] = control.gameboard.getP1().getMyDeals().elementAt(i).getSellPrice();
                    }
                    int p = (int) JOptionPane.showInputDialog(
                            frame,
                            "Sell item with price:\n",
                            "Buyer found",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            control.gameboard.getP1().getMyDeals().elementAt(0).getSellPrice());

                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + p);
                    boolean found = false;
                    int i = -1;
                    while (!found) {
                        if (control.gameboard.getP1().getMyDeals().elementAt(i).getSellPrice() == p) {
                            found = true;
                        }
                        i++;
                    }

                    control.gameboard.getDiscardedDeals().add(control.gameboard.getP1().getMyDeals().elementAt(i));
                    control.gameboard.getP1().getMyDeals().remove(i);
                }
                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);

            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof CasinoNight){
                if(rolled1 == 1 || rolled1 == 3|| rolled1 ==5){
                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() - 500);
                    if(control.gameboard.getP1().getMoney()<0){
                        control.gameboard.getP1().setLoan(control.gameboard.getP1().getLoan() + 1000);
                        control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 1000);
                    }
                    control.jackpot.setMoney(control.jackpot.getMoney() + 500);
                }
                else{
                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 500);
                }

                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof YardSale){
                int roll = (int)(Math.random() * (7-1) + 1);

                control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() - 100*roll);
                if(control.gameboard.getP1().getMoney()<0){
                    control.gameboard.getP1().setLoan(control.gameboard.getP1().getLoan() + 1000);
                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 1000);
                }
                control.gameboard.getP1().addMyDeals(control.gameboard.getDeals().elementAt(0));
                control.gameboard.getDeals().remove(0);

                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof PayDay){
                if(control.gameboard.getCurrentPlayer()==1){
                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 3500);

                    int bill = 0;
                    for(int j = 0; j < control.gameboard.getP1().getMyBills().size();j++){
                        bill += control.gameboard.getP1().getMyBills().elementAt(j).getAmountToPay();
                        control.gameboard.addDiscardedMails(control.gameboard.getP1().getMyBills().elementAt(j));
                        control.gameboard.getP1().getMyBills().remove(j);
                    }
                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() - bill );

                    control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() - control.gameboard.getP1().getLoan() / 10 );
                    int m = control.gameboard.getP1().getMoney();
                    if(m<0){
                        int loan = m/1000;

                        if(m%1000!=0){
                            loan++;
                        }
                        control.gameboard.getP1().setLoan(control.gameboard.getP1().getLoan() + loan * 1000);
                        control.gameboard.getP1().setMoney(loan * 1000 + control.gameboard.getP1().getMoney());

                    }
                    if(control.gameboard.getP1().getCurrentMonth() == control.gameboard.getGameMonths()) {
                        control.returnCards();
                    }
                    refreshWindowLayout();
                    disableAllButtons();
                    getLoan1.setEnabled(true);
                    endTurn1.setEnabled(true);

                }
            }
            else {
                control.positionAction(control.gameboard.getP1Pos());
                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);
            }

            if(control.isP2Done()&&control.isP1Done()){
                JFrame frame = new JFrame();
                String message;
                int p1Money = control.gameboard.getP1().getMoney() - control.gameboard.getP1().getLoan();
                int p2Money = control.gameboard.getP2().getMoney() - control.gameboard.getP2().getLoan();
                if (p1Money > p2Money) {
                    message = "Player 1 won";
                } else if (p2Money > p1Money) {
                    message = "Player 2 won";
                } else {
                    message = "It's a tie";
                }

                Object[] options = {"New Game",
                        "Exit"};
                int n =  JOptionPane.showOptionDialog(frame,
                        message,
                        "Game Over",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (n == 0) {
                    destroy();
                    new GraphicUI();
                }

                destroy();
            }
        }

    }

    private class Dice2Listener implements ActionListener{

        public void actionPerformed(ActionEvent e){

            rolled2 = control.roll();
            refreshWindowLayout();

            if (control.gameboard.getDays()[control.gameboard.getP2Pos()] == "Sunday") {
                disableAllButtons();
                JFrame frame = new JFrame();
                Object[] choices = { "Barcelona Wins", "Draw", "Real Wins", "No prediction" };
                String choice = (String) JOptionPane.showInputDialog(
                        frame,
                        "Bet 500 euro for Barcelona - Real game",
                        "Sunday's football game",
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(getResource("images/Barcelona_Real.jpg")),
                        choices,
                        "Barcelona Wins"
                );
                if (! choice.equals("No prediction")) {
                    int roll = (int)(Math.random() * (7-1) + 1);
                    control.setDice2(roll);

                    if (control.gameboard.getP2().getMoney() < 500) {
                        control.getLoan(1000);
                    }
                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() - 500);

                    if ((choice.equals("Barcelona Wins") && (roll == 1 || roll == 2))
                            || (choice.equals("Draw") && (roll == 3 || roll == 4))
                            || (choice.equals("Real Wins") && (roll == 5 || roll == 6))) {
                        control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 500);
                        control.setInfoBoxMessage("You won, your bet was right!");
                    } else {
                        control.setInfoBoxMessage("You lost, your bet was wrong.");
                    }
                }
            }

            if (control.gameboard.getDays()[control.gameboard.getP2Pos()] == "Thursday") {
                disableAllButtons();
                JFrame frame = new JFrame();
                Object[] choices = { "Drop", "Stable", "Increase", "Cancel" };
                String choice = (String) JOptionPane.showInputDialog(
                        frame,
                        "Crypto Thursday bet to win 300 euro",
                        "Crypto Thursday",
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(getResource("images/crypto.jpeg")),
                        choices,
                        "Drop"
                );
                if (choice != "Cancel") {
                    int roll = (int)(Math.random() * (7-1) + 1);
                    control.setDice2(roll);

                    if (roll == 1 || roll == 2) {
                        if (control.gameboard.getP2().getMoney() < 300) {
                            control.getLoan(1000);
                        }
                        control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() - 300);
                        control.setInfoBoxMessage("You lost.");
                    } else if (roll == 3 || roll == 4){
                        control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 300);
                        control.setInfoBoxMessage("You won.");
                    }
                }
            }

            if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof MailPosition) {
                disableAllButtons();
                mailButton.setEnabled(true);
                drawn2 = false;
            }
            else if((control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Deal)){
                disableAllButtons();
                dealButton.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Lottery){
                JFrame frame = new JFrame();
                Object[] possibilities = {1,2,3,4,5,6};
                int num = (int)JOptionPane.showInputDialog(
                        frame ,
                        control.gameboard.getP2().getName()+" choose a number:\n",
                        "Lottery",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        1);

                control.setDice1(num);
                control.setDice2(num);

                while(control.getDice2() == control.getDice1()) {
                    num = (int) JOptionPane.showInputDialog(
                            frame,
                            control.gameboard.getP1().getName() + " choose a number:\n",
                            "Lottery",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "1");
                    control.setDice1(num);
                }


                boolean won = false;
                while(!won){
                    int roll = (int)(Math.random() * (7-1) + 1);
                    if(roll == control.getDice1()){
                        control.gameboard.getP1().setMoney(control.gameboard.getP1().getMoney() + 1000);
                        won = true;
                    }
                    else if(roll == control.getDice2()){
                        control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 1000);
                        won = true;
                    }
                }
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                endTurn2.setEnabled(true);
            }
            else if((control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Buyer)){
                int num = control.gameboard.getP2().getMyDeals().size();

                if(num!=0) {
                    JFrame frame = new JFrame();


                    Object[] possibilities = new Object[num];

                    for (int i = 0; i < num; i++) {
                        possibilities[i] = control.gameboard.getP2().getMyDeals().elementAt(i).getSellPrice();
                    }
                    int p = (int) JOptionPane.showInputDialog(
                            frame,
                            "Sell item with price:\n",
                            "Buyer found",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            control.gameboard.getP2().getMyDeals().elementAt(0).getSellPrice());

                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + p);
                    boolean found = false;
                    int i = 0;
                    while (!found) {
                        if (control.gameboard.getP2().getMyDeals().elementAt(i).getSellPrice() == p) {
                            found = true;
                        }
                        i++;
                    }

                    control.gameboard.getDiscardedDeals().add(control.gameboard.getP2().getMyDeals().elementAt(i));
                    control.gameboard.getP2().getMyDeals().remove(i);
                }
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                endTurn2.setEnabled(true);

            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof CasinoNight){
                if(rolled1 == 1 || rolled1 == 3|| rolled1 ==5){
                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() - 500);
                    if(control.gameboard.getP2().getMoney()<0){
                        control.gameboard.getP2().setLoan(control.gameboard.getP2().getLoan() + 1000);
                        control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 1000);
                    }
                    control.jackpot.setMoney(control.jackpot.getMoney() + 500);
                }
                else{
                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 500);
                }
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                endTurn2.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof YardSale){
                int roll = (int)(Math.random() * (7-1) + 1);

                control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() - 100*roll);
                if(control.gameboard.getP2().getMoney()<0){
                    control.gameboard.getP2().setLoan(control.gameboard.getP2().getLoan() + 1000);
                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 1000);
                }
                control.gameboard.getP2().addMyDeals(control.gameboard.getDeals().elementAt(0));
                control.gameboard.getDeals().remove(0);
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                endTurn2.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof PayDay){
                if(control.gameboard.getCurrentPlayer()==2){
                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() + 3500);

                    int bill = 0;
                    for(int j = 0; j < control.gameboard.getP2().getMyBills().size();j++){
                        bill += control.gameboard.getP2().getMyBills().elementAt(j).getAmountToPay();
                        control.gameboard.addDiscardedMails(control.gameboard.getP2().getMyBills().elementAt(j));
                        control.gameboard.getP2().getMyBills().remove(j);
                    }
                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() - bill );

                    control.gameboard.getP2().setMoney(control.gameboard.getP2().getMoney() - control.gameboard.getP2().getLoan() / 10 );
                    int m = control.gameboard.getP2().getMoney();
                    if(m<0){
                        int loan = m/1000;

                        if(m%1000!=0){
                            loan++;
                        }
                        control.gameboard.getP2().setLoan(control.gameboard.getP2().getLoan() + loan * 1000);
                        control.gameboard.getP2().setMoney(loan * 1000 + control.gameboard.getP2().getMoney());
                    }
                    if(control.gameboard.getP2().getCurrentMonth() == control.gameboard.getGameMonths()) {
                        control.returnCards();
                    }
                    refreshWindowLayout();
                    disableAllButtons();
                    getLoan2.setEnabled(true);
                    endTurn2.setEnabled(true);
                }
            }
            else {
                control.positionAction(control.gameboard.getP2Pos());
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                endTurn2.setEnabled(true);
            }

            if(control.isP2Done()&&control.isP1Done()){
                JFrame frame = new JFrame();
                String message;
                int p1Money = control.gameboard.getP1().getMoney() - control.gameboard.getP1().getLoan();
                int p2Money = control.gameboard.getP2().getMoney() - control.gameboard.getP2().getLoan();
                if (p1Money > p2Money) {
                    message = "Player 1 won";
                } else if (p2Money > p1Money) {
                    message = "Player 2 won";
                } else {
                    message = "It's a tie";
                }

                Object[] options = {"New Game",
                        "Exit"};
                int n =  JOptionPane.showOptionDialog(frame,
                        message,
                        "Game Over",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (n == 0) {
                    destroy();
                    new GraphicUI();
                }

                destroy();
            }
        }

    }

    private class EndTurnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            disableAllButtons();
            control.setCurrentPlayer();
            refreshWindowLayout();
        }
    }

    private class CardListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button.getName().equals("Mail")) {
                showMailCard(0);
            } else if (button.getName().equals("Deal")) {
                showDealCard(0);
                dealButton.setEnabled(false);
            }
        }
    }

    public void disableAllButtons(){

        rollDice1.setEnabled(false);
        rollDice2.setEnabled(false);
        getLoan1.setEnabled(false);
        getLoan2.setEnabled(false);
        myDealCards1.setEnabled(false);
        myDealCards2.setEnabled(false);
        endTurn1.setEnabled(false);
        endTurn2.setEnabled(false);
        dealButton.setEnabled(false);
        mailButton.setEnabled(false);

    }

    public void showMailCard(int i) {

        Object[] options = {control.mails.elementAt(i).getChoice()};

        URL imageURL = cldr.getResource("resources/resources/images/" + control.mails.elementAt(i).getPath()); //image
        Image image = new ImageIcon(imageURL).getImage();

        image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);

        JOptionPane p = new JOptionPane();
        int n = p.showOptionDialog(this,
                control.mails.elementAt(i).message,
                control.mails.elementAt(i).type,
                JOptionPane.OK_OPTION,
                0,
                new ImageIcon(image),
                options,
                options[0]);
        disableAllButtons();
        if(control.gameboard.getCurrentPlayer() == 1) {

            if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Mail2 && drawn2 == false && control.mails.elementAt(i).type.equals("MoveToDealBuyer")){
                drawn2 = true;
                showMailCard(1);
                control.mailAction(0);
                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);
            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Mail2 && drawn2 == false){
                drawn2 = true;
                control.mailAction(i);
                refreshWindowLayout();
                disableAllButtons();
                mailButton.setEnabled(true);
                getLoan1.setEnabled(true);
            }
            else{
                control.mailAction(i);
                refreshWindowLayout();
                disableAllButtons();
                getLoan1.setEnabled(true);
                endTurn1.setEnabled(true);

            }
            if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Deal){
                dealButton.setEnabled(true);
            }
        }
        else{
            if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Mail2 && drawn2 == false && control.mails.elementAt(i).type.equals("MoveToDealBuyer")){
                drawn2 = true;
                showMailCard(1);
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);

            }
            else if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Mail2){
                drawn2 = true;
                control.mailAction(i);
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                mailButton.setEnabled(true);
            }
            else {
                control.mailAction(i);
                refreshWindowLayout();
                disableAllButtons();
                getLoan2.setEnabled(true);
                endTurn2.setEnabled(true);

            }
            if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Deal){
                dealButton.setEnabled(true);
            }
        }


    }

    public void showDealCard(int i) {
        Object[] options = {control.deals.elementAt(i).choice1, control.deals.elementAt(i).choice2};
        URL imageURL = cldr.getResource("resources/resources/images/" + control.deals.elementAt(i).getPath()); //image
        Image image = new ImageIcon(imageURL).getImage();
        image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        JOptionPane p = new JOptionPane();

        int n = p.showOptionDialog(this,
                control.deals.elementAt(i).message + "\nΤιμή Αγοράς: " + control.deals.elementAt(i).getAmountToPay() + " Ευρώ \nΤιμή Πώλησης: " + control.deals.elementAt(i).getSellPrice() + " Ευρώ \n",
                "Deal",
                JOptionPane.OK_OPTION,
                0,
                new ImageIcon(image),
                options,
                options[0]);

        control.getDeal(n,i);
        refreshWindowLayout();

        if(control.gameboard.getCurrentPlayer() == 1){
            disableAllButtons();
            endTurn1.setEnabled(true);
            getLoan1.setEnabled(true);
        }
        else{
            disableAllButtons();
            endTurn2.setEnabled(true);
            getLoan2.setEnabled(true);
        }

    }

    private JPanel gamePanel(){

        gamePanel = new JPanel();

        gamePanel.setLayout(new GridBagLayout());

        gamePanel.setBounds(0, 151, winThird, windowSize.height -200);
        GridBagConstraints c = new GridBagConstraints();
        gamePanel.setOpaque(false);

        int j = 0;

        for(int i = 0; i < 32; i++){

            JLabel day;

            if(i==0){
                day = new JLabel(control.gameboard.getDays()[i]);
            }else {
                day = new JLabel(control.gameboard.getDays()[i] + " " + i);
            }

            day.setBackground(Color.pink);
            day.setOpaque(true);
            day.setPreferredSize(new Dimension(winThird/7,20));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.gridx = i%7;
            c.weighty = 0.0;
            c.insets = new Insets(0,0,0,0);

            if(i % 7 == 0 && i != 0){
                j+=2;
            }
            c.gridy = j;
            gamePanel.add(day,c);
            JLabel dayLabel = new JLabel(resizeImg(new ImageIcon(this.getResource(this.control.gameboard.getPositions().elementAt(i).getURL())), winThird/7, (windowSize.height-350)/5));
            c.gridy = j+1;
            if(start) {
                JLabel pawn1 = new JLabel(resizeImg(new ImageIcon(this.getResource("images/pawn_blue.png")), 60, 80));
                pawn1.setSize(60,80);
                dayLabel.add(pawn1);

                JLabel pawn2 = new JLabel(resizeImg(new ImageIcon(this.getResource("images/pawn_yellow.png")), 60, 80));
                pawn2.setSize(170,130);
                dayLabel.add(pawn2);
                start = false;
            }
            else{
                if(control.gameboard.getP1Pos() == i) {
                    JLabel pawn1 = new JLabel(resizeImg(new ImageIcon(this.getResource("images/pawn_blue.png")), 60, 80));
                    pawn1.setSize(60, 80);
                    dayLabel.add(pawn1);
                }

                if(control.gameboard.getP2Pos() == i) {
                    JLabel pawn2 = new JLabel(resizeImg(new ImageIcon(this.getResource("images/pawn_yellow.png")), 60, 80));
                    pawn2.setSize(170, 130);
                    dayLabel.add(pawn2);
                }
            }

            gamePanel.add(dayLabel,c);
        }

        JLabel jackpotLabel = new JLabel(resizeImg(new ImageIcon(this.getResource("images/jackpot.png")),winThird/7 * 2 - winThird/7 / 2, (windowSize.height-350)/5 -10));

        c.gridx = 5;
        c.gridy = j+1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        gamePanel.add(jackpotLabel,c);

        JLabel jackpotAmount = new JLabel("<html><p style='font-size: 15px; color: white;'>Jackpot:"+ control.jackpot.getMoney()+" Euros</html>");

        jackpotAmount.setPreferredSize(new Dimension(winThird/7,20));
        c.gridx = 5;
        c.gridy = j;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        gamePanel.add(jackpotAmount, c);

        return gamePanel;
    }

    private JPanel infoPanel(){

        infoPanel = new JPanel();

        infoPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        infoPanel.setBounds(winThird + 50 , (windowSize.height / 3) +20 , (windowSize.width - winThird) - 100 , (windowSize.height / 3)/2 -50 );
        infoPanel.setBackground(Color.white);

        JLabel info = new JLabel();

        infoPanel.setLayout(new GridBagLayout());

        info.setText("<html><p style='font-size: 15px; color: red;'>Info Box<br/><p style='font-size: 13px;'><span style=\"font-weight:normal;\">Months left: <br/>Turn: Player "+ this.control.gameboard.getCurrentPlayer() +"  <br/>--> " + this.control.getInfoBoxMessage() + "<br/></span></html>");

        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.weightx = 0.1;
        constr.insets =  new Insets(5,10,5,10);

        infoPanel.add(info, constr);

        return infoPanel;
    }

    private JPanel player1Panel() {

        JPanel player1Panel = new JPanel();

        player1Panel.setBounds(winThird + 50 , 10 , (windowSize.width - winThird) - 100 , (windowSize.height / 3) );
        player1Panel.setBackground(Color.white);
        JLabel player1Text = new JLabel();
        player1Text.setText("<html><p style='font-size: 18px; color: blue;'>"+ control.gameboard.getP1().getName()+"<br/><br/><p style='font-size: 16px;'><span style=\"font-weight:normal;\">Money:"+control.gameboard.getP1().getMoney()+" Euros <br/>Loan:"+control.gameboard.getP1().getLoan() +" Euros<br/>Bills:"+control.gameboard.getP1().getBills()+" Euros<br/></span></html>");
        player1Panel.setLayout(new GridBagLayout());

        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.weightx = 0.5;
        constr.insets =  new Insets(10,10,10,10);


        player1Panel.add(player1Text, constr );

        rollDice1 = new JButton();
        rollDice1.setText("Roll Dice");
        rollDice1.addActionListener(new Dice1Listener());
        rollDice1.setEnabled(this.control.gameboard.getCurrentPlayer() == 1);

        rollDice1.setPreferredSize(new Dimension(300,27));

        constr.fill = GridBagConstraints.NONE;
        constr.anchor = GridBagConstraints.FIRST_LINE_START;
        constr.gridx = 0;
        constr.gridy = 1;

        player1Panel.add(rollDice1, constr);

        myDealCards1 = new JButton();
        myDealCards1.setText("My Deal Cards");
        myDealCards1.setEnabled(this.control.gameboard.getCurrentPlayer() == 1);
        myDealCards1.setPreferredSize(new Dimension(300,27));

        constr.gridx = 0;
        constr.gridy = 2;

        player1Panel.add(myDealCards1, constr);

        getLoan1 = new JButton();
        getLoan1.setText("Get Loan");
        getLoan1.setEnabled(this.control.gameboard.getCurrentPlayer() == 1);
        getLoan1.setPreferredSize(new Dimension(300,27));

        getLoan1.addActionListener(new Loan1Listener());

        constr.gridx = 0;
        constr.gridy = 3;

        player1Panel.add(getLoan1, constr);

        endTurn1 = new JButton();
        endTurn1.setText("End Turn");
        endTurn1.setEnabled(this.control.gameboard.getCurrentPlayer() == 1);
        endTurn1.setPreferredSize(new Dimension(300,27));
        endTurn1.addActionListener(new EndTurnListener());

        constr.gridx = 0;
        constr.gridy = 4;

        player1Panel.add(endTurn1, constr);

        JLabel diceImg = new JLabel(resizeImg(new ImageIcon(this.getResource("images/dice-"+rolled1+".jpg")), 75, 75));

        constr.gridx = 1;
        constr.gridy = 2;
        constr.gridheight =2;

        player1Panel.add(diceImg, constr);

        if(control.gameboard.getPositions().elementAt(control.gameboard.getP1Pos()) instanceof Mail2 && drawn2 == true){
            mailButton.setEnabled(true);
        }

        return player1Panel;
    }

    private JPanel player2Panel() {

        JPanel player2Panel = new JPanel();

        player2Panel.setBounds(winThird + 50 , (windowSize.height / 3)*2 - 60 , (windowSize.width - winThird) - 100 , (windowSize.height / 3) );
        player2Panel.setBackground(Color.white);
        JLabel player2Text = new JLabel();
        player2Text.setText("<html><p style='font-size: 18px; color: yellow;'>"+control.gameboard.getP2().getName()+"<br/><br/><p style='font-size: 16px;'><span style=\"font-weight:normal;\">Money: "+control.gameboard.getP2().getMoney()+" Euros <br/>Loan:"+control.gameboard.getP2().getLoan()+" Euros<br/>Bills:"+control.gameboard.getP2().getBills()+" Euros<br/></span></html>");
        player2Panel.setLayout(new GridBagLayout());

        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.weightx = 0.5;
        constr.insets =  new Insets(10,10,10,10);


        player2Panel.add(player2Text, constr );

        rollDice2 = new JButton();
        rollDice2.setText("Roll Dice");
        rollDice2.setEnabled(this.control.gameboard.getCurrentPlayer() == 2);
        rollDice2.addActionListener(new Dice2Listener());

        rollDice2.setPreferredSize(new Dimension(300,27));

        constr.fill = GridBagConstraints.NONE;
        constr.anchor = GridBagConstraints.FIRST_LINE_START;
        constr.gridx = 0;
        constr.gridy = 1;

        player2Panel.add(rollDice2, constr);

        myDealCards2 = new JButton();
        myDealCards2.setText("My Deal Cards");
        myDealCards2.setEnabled(this.control.gameboard.getCurrentPlayer() == 2);
        myDealCards2.setPreferredSize(new Dimension(300,27));

        constr.gridx = 0;
        constr.gridy = 2;

        player2Panel.add(myDealCards2, constr);

        getLoan2 = new JButton();
        getLoan2.setText("Get Loan");
        getLoan2.setEnabled(this.control.gameboard.getCurrentPlayer() == 2);
        getLoan2.setPreferredSize(new Dimension(300,27));

        getLoan2.addActionListener(new Loan2Listener());

        constr.gridx = 0;
        constr.gridy = 3;

        player2Panel.add(getLoan2, constr);

        endTurn2 = new JButton();
        endTurn2.setText("End Turn");
        endTurn2.setEnabled(this.control.gameboard.getCurrentPlayer() == 2);
        endTurn2.setPreferredSize(new Dimension(300,27));
        endTurn2.addActionListener(new EndTurnListener());

        constr.gridx = 0;
        constr.gridy = 4;

        player2Panel.add(endTurn2, constr);

        JLabel diceImg = new JLabel(resizeImg(new ImageIcon(this.getResource("images/dice-"+rolled2+".jpg")), 75, 75));

        constr.gridx = 1;
        constr.gridy = 2;
        constr.gridheight =2;

        player2Panel.add(diceImg, constr);

        if(control.gameboard.getPositions().elementAt(control.gameboard.getP2Pos()) instanceof Mail2 && drawn2 == true){
            mailButton.setEnabled(true);
        }

        return player2Panel;
    }

    private JLayeredPane boardPanel() {

        JLayeredPane boardPanel = new JLayeredPane();

        JLabel paydayLabel = new JLabel(resizeImg(new ImageIcon(this.getResource("images/logo.png")), winThird, 150));
        paydayLabel.setBounds(0, 0, winThird, 150);
        boardPanel.add(paydayLabel, JLayeredPane.DEFAULT_LAYER);

        boardPanel.setPreferredSize(new Dimension(winThird, windowSize.height));
        boardPanel.setBounds(0, 0, winThird, windowSize.height);

        boardPanel.add(this.gamePanel());

        return boardPanel;

    }


    /**
     * Resizes an ImageIcon to the given width and height.
     *
     * @param image  The image you want to resize
     * @param width  The width you want to resize the image to.
     * @param height The height you want to resize the image to.
     * @return a resized ImageIcon based on width and height
     */
    protected ImageIcon resizeImg(ImageIcon image, int width, int height) {
        Image resizedImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }

    /**
     * Creates a path to get a file from resources.
     *
     * @param filename The file you want to get.
     * @return a URL to get a file from resources
     */
    protected URL getResource(String filename) {
        return this.getClass().getClassLoader().getResource("resources/" + filename);
    }

}
