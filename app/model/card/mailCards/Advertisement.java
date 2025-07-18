package app.model.card.mailCards;

import app.model.card.MailCards;

/**
 * Mail cards of the type Advertisement.
 *
 * The player can sell the Advertisement card for the amount of money said on the card.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Advertisement extends MailCards {

    final private int toGet;


    /**
     * <b>Constructor:</b> Constructs a new instance of the class Advertisement
     *
     * <b>Postcondition:</b> Constructs a new Advertisement and initializes the message and the money the player is going to get
     * @param message the message on the card
     * @param toGet the amount of money the player gets from selling the Advertisement
     * @param path the image path
     *
     */
    public Advertisement(String message, int toGet, String path, String choice, String type) {
        super(message, path, type, choice);
        this.toGet = toGet;
    }

    /**
     * <b>Accessor(selector):</b> Returns the amount of money the player gets when they sell the Advertisement
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the amount of money
     */
    public int getToGet() {
        return this.toGet;
    }

}
