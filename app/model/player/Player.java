package app.model.player;

import app.model.card.*;
import app.model.card.mailCards.Bill;

import java.util.Vector;

/**
 * The player of the game.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Player {

    private int money;
    private int bills;
    private int loan;
    final String name;
    Vector<Bill> myBills;
    Vector<DealCards> myDeals;
    int currentMonth;

    /**
     * <b>Constructor:</b> Constructs a new instance of the class Player.
     *
     * <b>Postcondition:</b> Constructs a new player with money 3500$ that starts at the start of the board and doesn't owe any money
     *
     * @param name the player's name
     */
    public Player(String name){
        this.name = name;
        this.money = 3500;
        this.bills = 0;
        this.loan = 0;
        this.myBills = new Vector<Bill>();
        this.myDeals = new Vector<DealCards>();
        currentMonth =  0;
    }

    /**
     * <b>Accessor(selector):</b> Returns the player's money
     *
     * <b>Postcondition:</b> Returns the player's money
     *
     * @return the money
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the player's money
     *
     * <b>Postcondition:</b> Sets the player's money
     *
     * @param money the new value for money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * <b>Accessor(selector):</b> Returns the player's bill money
     *
     * <b>Postcondition:</b> Returns the player's bill money
     *
     * @return the bill money
     */
    public int getBills() {
        return this.bills;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the new value for the player's bill money
     *
     * <b>Postcondition:</b> Sets the new value for the player's bill money
     *
     * @param bills the new value for the bill money
     */
    public void setBills(int bills) {
        this.bills = bills;
    }

    /**
     * <b>Accessor(selector):</b> Returns the player's loan money
     *
     * <b>Postcondition:</b> Returns the player's loan money
     *
     * @return the loan money
     */
    public int getLoan() {
        return this.loan;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the new value fot the player's loan money
     *
     * <b>Postcondition:</b> Sets the new value for the player's loan money
     *
     * @param loan the new value of the loan money
     */
    public void setLoan(int loan) {
        this.loan = loan;
    }

    /**
     * <b>Accessor(selector):</b> Returns the player's name
     *
     * <b>Postcondition:</b> Returns the player's name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <b>Transformer(mutative):</b> Adds a new deal card to the vector
     *
     * <b>Postcondition:</b> Adds a new deal card to the vector
     *
     * @param c the deal card to be added
     */
    public void addMyDeals(DealCards c){
        myDeals.add(c);
    }

    /**
     * <b>Accessor(selector):</b> Returns the deal card vector
     *
     * <b>Postcondition:</b> Returns the deal card vector
     *
     * @return the deal card vector
     */
    public Vector<DealCards> getMyDeals() {
        return this.myDeals;
    }

    /**
     * <b>Transformer(mutative):</b> Removes the card at the specified index from the player's deal cards
     *
     * <b>Postcondition:</b> Removes the card at the specified index from the player's deal cards
     *
     * @param index the index of the card to be removed
     */
    public void removeMyDeals(int index){
        myDeals.remove(index);
    }

    /**
     * <b>Transformer(mutative):</b> Adds a new bill card to the vector
     *
     * <b>Postcondition:</b> Adds a new bill card to the vector
     *
     * @param c the bill card to be added
     */
    public void addMyBills(Bill c){
        myBills.add(c);
    }

    /**
     * <b>Accessor(selector):</b> Returns the bill card vector
     *
     * <b>Postcondition:</b> Returns the bill card vector
     *
     * @return the bill card vector
     */
    public Vector<Bill> getMyBills() {
        return this.myBills;
    }

    /**
     * <b>Transformer(mutative):</b> Removes the card at the specified index from the player's bill cards
     *
     * <b>Postcondition:</b> Removes the card at the specified index from the player's bill cards
     *
     * @param index the index of the card to be removed
     */
    public void removeMyBills(int index){
        myBills.remove(index);
    }

    /**
     * <b>Accessor(selector):</b> Returns the month the player is currently on.
     *
     * <b>Postcondition:</b> Returns the month the player is currently on.
     *
     * @return the month
     */
    public int getCurrentMonth() {
        return this.currentMonth;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the month the player is currently on
     *
     * <b>Postcondition:</b> Sets the month the player is currently on
     *
     * @param currentMonth the new month the player is on
     */
    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }
}
