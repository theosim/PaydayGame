package app.model.card.mailCards;

import app.model.card.MailCards;

/**
 * Mail cards of the type Pay the Neighbor.
 *
 * The player pays their opponent the amount of money on the card.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class PayTheNeighbor extends MailCards {
    final private int amountToPay;

    /**
     * <b>Constructor:</b> Constructs a new instance of the class PayTheNeighbor
     *
     * <b>Postcondition:</b> Constructs a new MailCards and initializes the message and amount to be paid to the opponent
     *  @param pay amount to be paid to the opponent
     * @param message the message on the card
     * @param choice
     */
    public PayTheNeighbor( String message, int pay, String path, String choice, String type){
        super(message, path, type, choice);
        this.amountToPay = pay;
    }

    /**
     * <b>Accessor(selector):</b> Returns the amount to be paid to the opponent
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the amount
     */
    @Override
    public int getAmountToPay() {
        return this.amountToPay;
    }
}
