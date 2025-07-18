package app.model.board;

import app.model.card.DealCards;
import app.model.card.MailCards;
import app.model.player.Player;
import app.model.position.Position;

import java.util.Vector;

/**
 * Board is where all the information concerning the board of the game is stored.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Board {
    private Player p1;
    private Player p2;
    private int p1Pos, p2Pos;
    private final int gameMonths;
    private Vector<DealCards> deals = new Vector<DealCards>();
    private Vector<MailCards> mails = new Vector<MailCards>();
    private Vector<DealCards> discardedDeals = new Vector<DealCards>();
    private Vector<MailCards> discardedMails = new Vector<MailCards>();
    private Vector<Position> positions = new Vector<Position>();
    private final String[] days;
    private int currentPlayer;

    /**
     * <b>Constructor:</b> Constructs a new instance of the board class.
     *
     * <b>Precondition:</b> months = 1 or 2 or 3
     * <b>Postcondition:</b> Constructs a new board with 2 players, stacks of deal and mail cards, positions and days and stores the current player and how many months the game will be played for
     *
     * @param months how many months the game will be played for
     * @param deals the stack of deal cards
     * @param mails the stack of mail cards
     * @param positions the vector of the positions that are represented on the board each day
     * @param days the array of days (Monday 1 to Wednesday 31) where the index is the index of the day and the value at that index is the day of the week
     * @param currentPlayer the player who is playing at this moment
     */
    public Board( int months, Vector<DealCards> deals, Vector<MailCards> mails, Vector<Position> positions, String[] days, int currentPlayer){

        this.gameMonths = months;
        this.deals = deals;
        this.mails = mails;
        this.positions = positions;
        this.days = days;
        this.currentPlayer = currentPlayer;

    }

    public void addPlayer1(String name){
        this.p1 = new Player(name);
        this.setP1Pos(0);
    }

    public void addPlayer2(String name){
        this.p2 = new Player(name);
        this.setP2Pos(0);
    }

    /**
     * <b>Accessor(selector):</b> Returns player 1
     *
     * <b>Postcondition:</b> Returns player 1
     *
     * @return the player
     */
    public Player getP1() {
        return this.p1;
    }

    /**
     * <b>Accessor(selector):</b> Return player 2
     *
     * <b>Postcondition:</b> Returns player 2
     *
     * @return the player
     */
    public Player getP2() {
        return this.p2;
    }

    /**
     * <b>Accessor(selector):</b> Returns the position of player 1
     *
     * <b>Postcondition:</b> Returns the position of player 1
     *
     * @return the position
     */
    public int getP1Pos() {
        return this.p1Pos;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the new position of player 1 on the board
     *
     * <b>Postcondition:</b> Sets the new position of player 1 on the board
     *
     * @param p1Pos the new position of the player
     */
    public void setP1Pos(int p1Pos) {
        this.p1Pos = p1Pos;
    }

    /**
     * <b>Accessor(selector):</b> Returns the position of player 2
     *
     * <b>Postcondition:</b> Returns the position of player 2
     * @return the position
     */
    public int getP2Pos() {
        return this.p2Pos;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the new position of player 2 on the board
     *
     * <b>Postcondition:</b> Sets the new position of player 2 on the board
     *
     * @param p2Pos the new position of the player
     */
    public void setP2Pos(int p2Pos) {
        this.p2Pos = p2Pos;
    }

    /**
     * <b>Accessor(selector):</b> Returns the months the game will be played for
     *
     * <b>Postcondition:</b> Returns the months the game will be played for
     *
     * @return the number of months
     */
    public int getGameMonths() {
        return this.gameMonths;
    }

    /**
     * <b>Accessor(selector):</b> Returns the stack of deal cards
     *
     * <b>Postcondition:</b> Returns the stack of deal cards
     *
     * @return the stack
     */
    public Vector<DealCards> getDeals() {
        return this.deals;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the stack of deal cards
     *
     * <b>Postcondition:</b> Sets the stack of deal cards
     *
     * @param deals the stack of deal cards
     */
    public void setDeals(Vector<DealCards> deals) {
        this.deals = deals;
    }

    /**
     * <b>Transformer(mutative):</b> Removes the deal card that was just drawn from the stack
     *
     * <b>Postcondition:</b> Removes the deal card that was just drawn from the stack
     *
     * @param index the index of the drawn card
     */
    public void removeDeal(int index){
        deals.remove(index);
    }

    /**
     * <b>Accessor(selector):</b> Returns the stack of mail cards
     *
     * <b>Postcondition:</b> Returns the stack of mail cards
     *
     * @return the stack
     */
    public Vector<MailCards> getMails() {
        return this.mails;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the stack of mail cards
     *
     * <b>Postcondition:</b> Sets the stack of mail cards
     *
     * @param mails the stack of mail cards
     */
    public void setMails(Vector<MailCards> mails) {
        this.mails = mails;
    }

    /**
     * <b>Transformer(mutative):</b> Removes the mail card that was just drawn from the stack
     *
     * <b>Postcondition:</b> Removes the mail card that was just drawn from the stack
     *
     * @param index the index of the drawn card
     */
    public void removeMail(int index){
        mails.remove(index);
    }

    /**
     * <b>Accessor(selector):</b> Returns the pile of discarded deal cards
     *
     * <b>Postcondition:</b> Returns the pile of discarded deal cards
     *
     * @return the pile
     */
    public Vector<DealCards> getDiscardedDeals() {
        return this.discardedDeals;
    }

    /**
     * <b>Transformer(mutative):</b> Adds a deal card to the discarded pile
     *
     * <b>Postcondition:</b> Adds a deal card to the discarded pile
     *
     * @param c the card to be added to the discarded
     */
    public void addDiscardedDeals(DealCards c){
        discardedDeals.add(c);
    }

    /**
     * <b>Accessor(selector):</b> Return the pile of discarded mail cards
     *
     * <b>Postcondition:</b> Return the pile of discarded mail cards
     *
     * @return the pile
     */
    public Vector<MailCards> getDiscardedMails() {
        return this.discardedMails;
    }

    /**
     * <b>Transformer(mutative):</b> Adds a mail card to the discarded pile
     *
     * <b>Postcondition:</b> Adds a mail card to the discarded pile
     *
     * @param c the card to be added to the discarded
     */
    public void addDiscardedMails(MailCards c){
        discardedMails.add(c);
    }

    /**
     * <b>Accessor(selector):</b> Returns the positions vector
     *
     * <b>Postcondition:</b> Returns the positions vector
     *
     * @return the position vector
     */
    public Vector<Position> getPositions() {
        return this.positions;
    }

    /**
     * <b>Accessor(selector):</b> Returns the array of days (Monday 1 to Wednesday 31 where the number is the array index)
     *
     * <b>Postcondition:</b> Returns the array of days (Monday 1 to Wednesday 31 where the number is the array index)
     *
     * @return the array of days
     */
    public String[] getDays() {
        return this.days;
    }

    /**
     * <b>Accessor(selector):</b> Returns which player is currently playing
     *
     * <b>Postcondition:</b> Returns which player is currently playing
     *
     * @return 1 for player 1 and 2 for player 2
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * <b>Transformer(mutative):</b> Sets which player is currently playing
     *
     * <b>Postcondition:</b> Sets which player is currently playing
     *
     * @param currentPlayer 1 for player 1 and 2 for player 2
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
