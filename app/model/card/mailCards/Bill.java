package app.model.card.mailCards;

import app.model.card.MailCards;

/**
 * Mail Cards of the type Bill.
 *
 * Bills that the player pays to the bank.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Bill extends MailCards {

    final private int amountToPay;
    /**
     * <b>Constructor:</b> Constructs an instance of the class Bill
     *
     * <b>Postcondition:</b> Constructs a new Bill and initializes the message and the amount to be paid
     * @param message the message on the card
     * @param amountToPay the amount to be paid for the bank for the bill
     * @param choice the choice the player has
     */
    public Bill(String message, int amountToPay, String path, String choice, String type) {
        super(message, path, type, choice);
        this.amountToPay = amountToPay;
    }

    /**
     * <b>Accessor(selector):</b> Returns the amount to be paid to the bank for the bill
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the amount to be paid
     */
    @Override
    public int getAmountToPay() {
        return this.amountToPay;
    }
}
