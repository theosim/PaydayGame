package app.model.card.mailCards;

import app.model.card.MailCards;

/**
 * Mail cards of the type get money from the neighbor.
 *
 * The player gets the amount of money said on the card from their opponent.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class GetMoneyFromTheNeighbor extends MailCards {
    final private int toGet;

    /**
     * <b>Constructor:</b> Constructs a new instance of the class Get Money From The Neighbor
     *
     * <b>Postcondition:</b> Constructs a new MailCards and initializes the message and money to be gotten from the opponent
     *  @param toGet amount of money the opponent has to give the player
     * @param message the message on the card
     * @param choice
     */
    public GetMoneyFromTheNeighbor(String message, int toGet, String path, String choice, String type){
        super(message,  path, type, choice);
        this.toGet = toGet;
    }

    /**
     * <b>Accessor(selector):</b> Returns the amount of money the opponent has to give to the player
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the amount of money
     */
    public int getToGet() {
        return this.toGet;
    }
}
