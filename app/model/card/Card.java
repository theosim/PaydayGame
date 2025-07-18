package app.model.card;

/**
 * Card is a template for the basic functions of all cards
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */

public interface Card {


    String getPath();
    /**
     * <b>Accessor(selector):</b> Returns the amount of money to be paid
     *
     * <b>Postcondition:</b> return value >= 0
     * @return the amount of money
     */
    int getAmountToPay();

}
