package app.model.card.mailCards;

import app.model.card.MailCards;

/**
 * Move To Deal/Buyer.
 *
 * The player moves to the closest tile that is a deal or a buyer unless there aren't any left.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class MoveToDealBuyer extends MailCards {

    /**
     * <b>Constructor:</b> Constructs a new instance of the class MoveToDealBuyer
     *
     * <b>Postcondition:</b> Constructs a new MailCards and initializes the message
     *
     * @param message the message on the card
     * @param choice
     */
    public MoveToDealBuyer(String message, String path, String choice, String type) {
        super(message, path, type, choice);
    }
}
