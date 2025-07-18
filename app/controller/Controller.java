package app.controller;

import app.model.board.Board;
import app.model.card.DealCards;
import app.model.card.MailCards;
import app.model.card.mailCards.*;
import app.model.jackpot.Jackpot;
import app.model.player.Player;
import app.model.position.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * The app.controller is basically the game master and its job is to control and execute all the operations.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Controller {

    public Board gameboard;
    ClassLoader cldr;
    private final int months;
    public Vector<DealCards> deals = new Vector<DealCards>();
    public Vector<MailCards> mails = new Vector<MailCards>();
    private String[][] mailCards = new String[48][4];
    private String[][] dealCards = new String[20][8];
    private final Vector<Position> positions = new Vector<Position>();
    private final String[] days = new String[32];
    private int currentPlayer, dice1, dice2;
    private boolean p1Done,p2Done,finished;
    public String won;
    public Jackpot jackpot;
    private String infoBoxMessage;
    private boolean isMailButtonEnabled = false;


    /**
     * <b>Constructor:</b> Constructs a new instance of Controller and initializes the game
     *
     * <b>Precondition:</b> number of months is 1,2 or 3
     * <b>Postcondition:</b> Constructs a new app.controller with 2 new players and a new board, sets how many months of the game will be played initializes the card stacks and chooses who will play first and who will play next
     *
     * @param name1 name of player 1
     * @param name2 name of player 2
     * @param months the number of months(1,2 or 3)
     */
    public Controller(String name1,String  name2, int months){
        this.months = months;
        currentPlayer = (int)((Math.random() * (3-1)) + 1);

        initDeals();
        initMails();
        initPositions();
        initDays();

        infoBoxMessage = "Game started!";
        jackpot = new Jackpot("");
        jackpot.setMoney(0);
        gameboard = new Board(months, deals, mails, positions, days, currentPlayer);
        gameboard.addPlayer1(name1);
        gameboard.addPlayer2(name2);

    }

    public String getInfoBoxMessage() {
        return this.infoBoxMessage;
    }

    public void setInfoBoxMessage(String message) {
        this.infoBoxMessage = message;
    }

    public void mailAction(int i){

        if(this.gameboard.getMails().elementAt(i) instanceof PayTheNeighbor){

            if(this.gameboard.getCurrentPlayer() == 1){

                if(this.gameboard.getP1().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay()<0){
                   int m =  this.gameboard.getMails().elementAt(i).getAmountToPay() - this.gameboard.getP1().getMoney();
                   int loan = m/1000;

                   if(m%1000!=0){
                       loan++;
                   }
                   this.gameboard.getP1().setLoan(this.gameboard.getP1().getLoan() + loan * 1000);
                   this.gameboard.getP1().setMoney(loan * 1000 + this.gameboard.getP1().getMoney());
                }
                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay());

                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() + this.gameboard.getMails().elementAt(i).getAmountToPay());
            }
            else{

                if(this.gameboard.getP2().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay()<0){
                    int m =  this.gameboard.getMails().elementAt(i).getAmountToPay() - this.gameboard.getP2().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP2().setLoan(this.gameboard.getP2().getLoan() + loan * 1000);
                    this.gameboard.getP2().setMoney(loan * 1000 + this.gameboard.getP2().getMoney());
                }

                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() + this.gameboard.getMails().elementAt(i).getAmountToPay());
                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay());
            }

        }
        else if(this.gameboard.getMails().elementAt(i) instanceof GetMoneyFromTheNeighbor){

            if(this.gameboard.getCurrentPlayer() == 1){
                if(this.gameboard.getP2().getMoney() - ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet()<0){
                    int m =  ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet() - this.gameboard.getP2().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP2().setLoan(this.gameboard.getP2().getLoan() + loan * 1000);
                    this.gameboard.getP2().setMoney(loan * 1000 + this.gameboard.getP2().getMoney());
                }

                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() + ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet());
                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() - ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet());

            }
            else{
                if(this.gameboard.getP1().getMoney() - ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet()<0){
                    int m =  ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet() - this.gameboard.getP1().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP1().setLoan(this.gameboard.getP1().getLoan() + loan * 1000);
                    this.gameboard.getP1().setMoney(loan * 1000 + this.gameboard.getP1().getMoney());
                }
                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() - ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet());

                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() + ((GetMoneyFromTheNeighbor) this.gameboard.getMails().elementAt(i)).getToGet());
            }

        }
        else if(this.gameboard.getMails().elementAt(i) instanceof Charity){

            if(this.gameboard.getCurrentPlayer() == 1){

                if(this.gameboard.getP1().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay()<0){
                    int m =  this.gameboard.getMails().elementAt(i).getAmountToPay() - this.gameboard.getP1().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP1().setLoan(this.gameboard.getP1().getLoan() + loan * 1000);
                    this.gameboard.getP1().setMoney(loan * 1000 + this.gameboard.getP1().getMoney());
                }
                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay());

                this.jackpot.setMoney(this.jackpot.getMoney() + this.gameboard.getMails().elementAt(i).getAmountToPay());
            }
            else{

                if(this.gameboard.getP2().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay()<0){
                    int m =  this.gameboard.getMails().elementAt(i).getAmountToPay() - this.gameboard.getP2().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP2().setLoan(this.gameboard.getP2().getLoan() + loan * 1000);
                    this.gameboard.getP2().setMoney(loan * 1000 + this.gameboard.getP2().getMoney());
                }

                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() - this.gameboard.getMails().elementAt(i).getAmountToPay());
                this.jackpot.setMoney(this.jackpot.getMoney() + this.gameboard.getMails().elementAt(i).getAmountToPay());
            }

        }
        else if(this.gameboard.getMails().elementAt(i) instanceof MoveToDealBuyer){

            if(this.gameboard.getCurrentPlayer() == 1){

                int j = this.gameboard.getP1Pos();
                int dealBuy = j;
                while(dealBuy < 32 && !(this.positions.elementAt(dealBuy) instanceof BuyDeal)){
                    dealBuy++;
                }

                if(dealBuy!=32){
                    this.gameboard.setP1Pos(dealBuy);
                }
            }
            else{
                int j = this.gameboard.getP2Pos();
                int dealBuy = j;
                while(dealBuy < 32 && !(this.positions.elementAt(dealBuy) instanceof BuyDeal)){
                    dealBuy++;
                }

                if(dealBuy!=32){
                    this.gameboard.setP2Pos(dealBuy);
                }
            }

        }
        else if(this.gameboard.getMails().elementAt(i) instanceof Advertisement){
            if(this.gameboard.getCurrentPlayer() == 1){
                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() + ((Advertisement) this.gameboard.getMails().elementAt(i)).getToGet());
            }
            else{
                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() + ((Advertisement) this.gameboard.getMails().elementAt(i)).getToGet());
            }
        }
        else{
            if(this.gameboard.getCurrentPlayer() == 1){
                this.gameboard.getP1().setBills(this.gameboard.getP1().getBills() + (this.gameboard.getMails().elementAt(i)).getAmountToPay());
            }
            else{
                this.gameboard.getP2().setBills(this.gameboard.getP2().getBills() + (this.gameboard.getMails().elementAt(i)).getAmountToPay());
            }
        }

        getMail(i);

    }

    public void positionAction(int pos){

        if(this.gameboard.getPositions().elementAt(pos) instanceof Sweepstakes){

            if(this.gameboard.getCurrentPlayer()==1){
                this.gameboard.getP1().setMoney( (int)(Math.random() * (7-1) + 1) * 1000 + this.gameboard.getP1().getMoney());
            }
            else{
                this.gameboard.getP2().setMoney( (int)(Math.random() * (7-1) + 1) * 1000 + this.gameboard.getP2().getMoney());
            }

        }
        else if(this.gameboard.getPositions().elementAt(pos) instanceof RadioContest) {

            do {
                setDice1((int) (Math.random() * (7 - 1) + 1));
                setDice2((int) (Math.random() * (7 - 1) + 1));
            }while(getDice1() == getDice2());

            if(getDice1() > getDice2()) {
                this.gameboard.getP1().setMoney(1000 + this.gameboard.getP1().getMoney());
            }
            else{
                this.gameboard.getP2().setMoney(1000 + this.gameboard.getP2().getMoney());
            }
        }

    }

    public void getMail(int i){
        if(this.gameboard.getMails().elementAt(i) instanceof Bill){
            if(this.gameboard.getCurrentPlayer() == 1){
                this.gameboard.getP1().addMyBills((Bill) this.gameboard.getMails().elementAt(i));
            }
            else{
                this.gameboard.getP2().addMyBills((Bill) this.gameboard.getMails().elementAt(i));
            }
        }
        else {
            this.gameboard.addDiscardedMails(this.gameboard.getMails().elementAt(i));
        }
        this.gameboard.getMails().remove(i);
        if (this.gameboard.getMails().size() == 1) {
            Collections.shuffle(this.gameboard.getDiscardedMails());
            this.gameboard.getDiscardedMails().forEach(mailCard -> {
                this.gameboard.getDiscardedMails().remove(mailCard);
                this.gameboard.getMails().add(mailCard);
            });
        }
    }

    public void getDeal(int n, int i){
        if(n == 0){
            if(this.gameboard.getCurrentPlayer() == 1){
                this.gameboard.getP1().addMyDeals(this.gameboard.getDeals().elementAt(i));
                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() - this.gameboard.getDeals().elementAt(i).getAmountToPay());

                if(this.gameboard.getP1().getMoney() - this.gameboard.getDeals().elementAt(i).getAmountToPay()<0){
                    int m =  this.gameboard.getDeals().elementAt(i).getAmountToPay() - this.gameboard.getP1().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP1().setLoan(this.gameboard.getP1().getLoan() + loan * 1000);
                    this.gameboard.getP1().setMoney(loan * 1000 + this.gameboard.getP1().getMoney());
                }

                this.gameboard.getDeals().remove(i);
            }
            else{

                this.gameboard.getP2().addMyDeals(this.gameboard.getDeals().elementAt(i));
                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() - this.gameboard.getDeals().elementAt(i).getAmountToPay());
                if(this.gameboard.getP2().getMoney() - this.gameboard.getDeals().elementAt(i).getAmountToPay()<0){

                    int m =  this.gameboard.getDeals().elementAt(i).getAmountToPay() - this.gameboard.getP2().getMoney();
                    int loan = m/1000;

                    if(m%1000!=0){
                        loan++;
                    }
                    this.gameboard.getP2().setLoan(this.gameboard.getP2().getLoan() + loan * 1000);
                    this.gameboard.getP2().setMoney(loan * 1000 + this.gameboard.getP2().getMoney());
                }

                this.gameboard.getDeals().remove(i);
            }
        }
        else{
            gameboard.addDiscardedDeals(this.gameboard.getDeals().elementAt(i));
            this.gameboard.getDeals().remove(i);
            if (this.gameboard.getDeals().size() == 1) {
                Collections.shuffle(this.gameboard.getDiscardedDeals());
                this.gameboard.getDiscardedDeals().forEach(dealCard -> {
                    this.gameboard.getDiscardedDeals().remove(dealCard);
                    this.gameboard.getDeals().add(dealCard);
                });
            }
        }
    }

    public void readFile(String path, String type) {

        BufferedReader br = null;
        String sCurrentLine;
        try {

            String cardPath = "resources/" + path;
            br = new BufferedReader(new FileReader(cardPath));
        } catch (FileNotFoundException ex) {
            System.out.println("Wrong card path");
        }
        int count = 0;
        int splitCount = 0;
        HashMap<Integer, String> domainsMap = new HashMap<>();
        try {
            br.readLine();
            while ((sCurrentLine = br.readLine()) != null) {
                if (type.equals("Mail")) {
                    mailCards[count++] = sCurrentLine.split(",");
                }
                else {
                    dealCards[count++] = sCurrentLine.split(",");
                }
            }
            br.close();
        } catch (IOException ex) {
            System.out.println("Error reading card file");
        }
    }

    public void returnCards() {
        if (gameboard.getCurrentPlayer() == 1) {
            this.gameboard.getP1().getMyDeals().forEach(deal -> {
                this.gameboard.getDiscardedDeals().add(deal);
                this.gameboard.getP1().getMyDeals().remove(deal);
            });
        } else {
            this.gameboard.getP2().getMyDeals().forEach(deal -> {
                this.gameboard.getDiscardedDeals().add(deal);
                this.gameboard.getP2().getMyDeals().remove(deal);
            });
        }
    }


    /**
     * <b>Transformer(mutative):</b> Initializes the stack of deal cards
     *
     * <b>Postcondition:</b> Initializes the stack of the 20 deal cards
     */
    public void initDeals(){
        readFile("resources/dealCards.csv","Deal");

        for (int i = 0; i < 20; i++){
            deals.add(new DealCards(Integer.parseInt(dealCards[i][3]), Integer.parseInt(dealCards[i][4]), dealCards[i][2], dealCards[i][5], dealCards[i][6], dealCards[i][7]));
        }

        Collections.shuffle(deals);

    }

    /**
     * <b>Transformer(mutative):</b> Initializes the stack of mail cards
     *
     * <b>Postcondition:</b> Initializes the stack of the 48 mail cards (8 pay the neighbor, 8 get money from the neighbor, 8 Charity, 8 Bill, 8 move to deal/buyer and 8 Advertisement)
     */
    public void initMails(){

        readFile("resources/mailCards.csv","Mail");

        for (int i = 0; i < 48; i++){

            if(mailCards[i][1].equals("Αdvertisement")){
                mails.add(new Advertisement(mailCards[i][2], Integer.parseInt(mailCards[i][4]), mailCards[i][5], mailCards[i][3], mailCards[i][1]));
            }
            else if(mailCards[i][1].equals("Bill")){
                mails.add(new Bill(mailCards[i][2], Integer.parseInt(mailCards[i][4]), mailCards[i][5], mailCards[i][3], mailCards[i][1]));
            }
            else if(mailCards[i][1].equals("Charity")){
                mails.add(new Charity(mailCards[i][2], Integer.parseInt(mailCards[i][4]), mailCards[i][5], mailCards[i][3], mailCards[i][1]));
            }
            else if(mailCards[i][1].equals("MoveToDealBuyer")){
                mails.add(new MoveToDealBuyer(mailCards[i][2], mailCards[i][5], mailCards[i][3], mailCards[i][1]));
            }
            else if(mailCards[i][1].equals("PayTheNeighbor")){
                mails.add(new PayTheNeighbor(mailCards[i][2], Integer.parseInt(mailCards[i][4]), mailCards[i][5], mailCards[i][3], mailCards[i][1]));
            }
            else{
                mails.add(new GetMoneyFromTheNeighbor(mailCards[i][2], Integer.parseInt(mailCards[i][4]), mailCards[i][5], mailCards[i][3], mailCards[i][1]));
            }

        }

        Collections.shuffle(mails);

    }

    /**
     * <b>Transformer(mutative):</b> Initializes the vector of positions on the board in a random order
     *
     * <b>Postcondition:</b> Initializes the 32 positions randomly( 1 start always position 0 , 8 mail(4 mail1 and 4 mail2), 5 deal, 2 sweepstakes,3 lottery, 2 radio contest, 6 Buyer, 2 casino night, 2 yard sale and 1 pay day always last)
     */
    public void initPositions(){

        for(int i = 0; i<4 ; i++){
            Position pos = new Mail1();
            positions.add(pos);
            pos = new Mail2();
            positions.add(pos);
        }

        for(int i = 0; i<5 ; i++){
            Position pos = new Deal();
            positions.add(pos);
        }

        for(int i = 0; i<2 ; i++){
            Position pos = new Sweepstakes();
            positions.add(pos);
            pos = new RadioContest();
            positions.add(pos);
            pos = new CasinoNight();
            positions.add(pos);
            pos = new YardSale();
            positions.add(pos);
        }

        for(int i = 0; i<6 ; i++){
            Position pos = new Buyer();
            positions.add(pos);
        }

        for(int i = 0; i<3 ; i++){
            Position pos = new Lottery();
            positions.add(pos);
        }

        Collections.shuffle(positions);

        positions.add(0, new Start());
        positions.add(new PayDay());

    }

    /**
     * <b>Transformer(mutative):</b> Initializes the days array
     *
     * <b>Postcondition:</b> Initializes the 31 days from Monday 1 to Wednesday 31 where the day index is the array index and the day of the week is the value on that index
     */
    public void initDays(){
        days[0] = "Start";
        int count = 1;
        for(int i =0; i<4; i++){
            days[count++] = "Monday";
            days[count++] = "Tuesday";
            days[count++] = "Wednesday";
            days[count++] = "Thursday";
            days[count++] = "Friday";
            days[count++] = "Saturday";
            days[count++] = "Sunday";
        }
        days[count++] = "Monday";
        days[count++] = "Tuesday";
        days[count] = "Wednesday";
    }

    /**
     * <b>Transformer(mutative):</b> When a player's turn ends sets the new current player
     *
     * <b>Postcondition:</b> currentPlayer changes to the player that's going to play now
     */
    public void setCurrentPlayer() {

        if(gameboard.getCurrentPlayer() == 1){
            if (!isP2Done()) {
                gameboard.setCurrentPlayer(2);
            }
        }
        else{
            if (!isP1Done()) {
                gameboard.setCurrentPlayer(1);
            }
        }

    }

    public int getCurrentPlayer() {
        return this.gameboard.getCurrentPlayer();
    }

    private void move(int rolled){

        if(this.gameboard.getCurrentPlayer() == 1){

            if(rolled == 6){
                this.gameboard.getP1().setMoney(this.gameboard.getP1().getMoney() + this.jackpot.getMoney());
                this.jackpot.setMoney(0);
            }

            if(this.gameboard.getP1Pos() == 31){
                this.gameboard.setP1Pos(rolled - 1);
            }
            else{
                if(this.gameboard.getP1Pos() + rolled >= 31){
                    this.gameboard.setP1Pos(31);
                    this.gameboard.getP1().setCurrentMonth(this.gameboard.getP1().getCurrentMonth() + 1);
                    this.gameboard.getP1().setCurrentMonth(this.gameboard.getP1().getCurrentMonth() + 1);
                    if (this.gameboard.getP1().getCurrentMonth() == this.gameboard.getGameMonths()) {
                        this.setP1Done(true);
                    }
                }
                else{
                    this.gameboard.setP1Pos(this.gameboard.getP1Pos() + rolled);
                }
            }
        }
        else{

            if(rolled == 6){
                this.gameboard.getP2().setMoney(this.gameboard.getP2().getMoney() + this.jackpot.getMoney());
                this.jackpot.setMoney(0);
            }

            if(this.gameboard.getP2Pos() == 31){
                this.gameboard.setP2Pos(rolled - 1);
            }
            else{
                if(this.gameboard.getP2Pos() + rolled >= 31){
                    this.gameboard.setP2Pos(31);
                    this.gameboard.getP2().setCurrentMonth(this.gameboard.getP2().getCurrentMonth() + 1);
                    if (this.gameboard.getP2().getCurrentMonth() == this.gameboard.getGameMonths()) {
                        this.setP2Done(true);
                    }
                }
                else{
                    this.gameboard.setP2Pos(this.gameboard.getP2Pos() + rolled);
                }
            }
        }

    }

    /**
     * <b>Transformer(mutative):</b> rolls the dice
     *
     * <b>Precondition:</b> a player either presses the roll button or there a position or card drawn that calls for the dice to be rolled
     * <b>Postcondition:</b> returns a number from 1 to 6
     *
     * @return the number on the dice after rolling
     *
     */
    public int roll(){

        int rolled = (int)(Math.random() * (7-1) + 1);

        move(rolled);

        return rolled;

    }

    /**
     * <b>Transformer(mutative):</b> Returns the loan money when a player presses the get loan button and chooses an integer and gets the integer multiplied by 1000 as loan money
     *
     * <b>Precondition:</b> the player has to give the integer of the amount of thousands they want to get
     * <b>Postcondition:</b> returns the amount of loan money
     *
     * @param x the integer
     * @return the amount of loan money
     * */
    public void getLoan(int x){

        if(this.gameboard.getCurrentPlayer() == 1){
            this.gameboard.getP1().setLoan(this.gameboard.getP1().getLoan() + x);
            this.gameboard.getP1().setMoney(x + this.gameboard.getP1().getMoney());
        }
        else{
            this.gameboard.getP2().setLoan(this.gameboard.getP2().getLoan() + x);
            this.gameboard.getP2().setMoney(x + this.gameboard.getP2().getMoney());
        }

    }

    /**
     * <b>Transformer(mutative):</b> Finds out who won the game
     *
     * <b>Precondition:</b> won has to be true else the game is not finished and this will return null
     * <b>Postcondition:</b> if the game is finished it prints who won the game in the info box else the game goes on
     */
    public void whoWon(){

    }

    /**
     * <b>Accessor(selector):</b> Returns the name of the player that won the game
     *
     * <b>Postcondition:</b> If someone won the game it returns the name of that player else it returns null
     *
     * @return the name
     */
    public String getWon(){
        return this.won;
    }

    /**
     * <b>Observer:</b> Returns true if player 1 finished the game, false otherwise
     *
     * <b>Postcondition:</b> Return true if player 1 finished the game, false otherwise
     *
     * @return true if player 1 finished the game, false otherwise
     */
    public boolean isP1Done() {
        return p1Done;
    }

    /**
     * <b>Observer:</b> Returns true if player 2 finished the game, false otherwise
     *
     * <b>Postcondition:</b> Return true if player 2 finished the game, false otherwise
     *
     * @return true if player 2 finished the game, false otherwise
     */
    public boolean isP2Done() {
        return p2Done;
    }

    /**
     * <b>Observer:</b> Returns true if both players finished the game, false otherwise
     *
     * <b>Postcondition:</b> Return true if both players finished the game, false otherwise
     *
     * @return true if both players finished the game, false otherwise
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * <b>Transformer(mutative):</b> Sets finished true if both players finished the game else finished is false
     *
     * <b>Postcondition:</b>  Sets finished true if both players finished the game else finished is false
     *
     * @param finished true if both players finished the game otherwise false
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * <b>Transformer(mutative):</b> Sets p2Done true if player 2 finished the game otherwise false
     *
     * <b>Postcondition:</b>  Sets p2Done true if player 2 finished the game otherwise false
     *
     * @param p2Done true if player 2 finished the game otherwise false
     */
    public void setP2Done(boolean p2Done) {
        this.p2Done = p2Done;
    }

    /**
     * <b>Transformer(mutative):</b> Sets p2Done true if player 1 finished the game otherwise false
     *
     * <b>Postcondition:</b>  Sets p2Done true if player 1 finished the game otherwise false
     *
     * @param p1Done true if player 1 finished the game otherwise false
     */
    public void setP1Done(boolean p1Done) {
        this.p1Done = p1Done;
    }

    /**
     * <b>Accessor(selector):</b> Returns the game months
     *
     * <b>Postcondition:</b> Returns the game months
     *
     * @return the game months
     */
    public int getMonths() {
        return months;
    }

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }
}
