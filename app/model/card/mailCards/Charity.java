package app.model.card.mailCards;

import app.model.card.MailCards;

/**
 * Mail cards of the type Charity.
 *
 * The player pays the amount of money on the card to the Jackpot.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Charity extends MailCards {

    final private int amountToPay;
    /**
     * <b>Constructor:</b> Constructs a new instance of the class Charity
     *
     * <b>Postcondition:</b> Constructs a new Charity and initializes the message and amount of money to be paid to the Jackpot
     * @param message the message on the card
     * @param amountToPay the amount to be paid to the Jackpot
     * @param choice the choice the player has
     */
    public Charity(String message, int amountToPay, String path, String choice, String type) {
        super(message,  path, type, choice);
        this.amountToPay = amountToPay;
    }

    /**
     * <b>Accessor(selector):</b> Returns the amount to be paid to the Jackpot
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the amount of money
     */
    @Override
    public int getAmountToPay() {
        return this.amountToPay;
    }
}
