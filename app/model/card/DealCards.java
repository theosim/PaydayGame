package app.model.card;

/**
 * Class for the 20 deal cards
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class DealCards implements Card {

    private final int amountToPay;
    private final int sellPrice;
    public final String message;
    private final String path;
    public final String choice1;
    public final String choice2;

    /**
     * <b>Constructor:</b> Constructs a new instance of DealCards
     *
     * <b>Precondition:</b> amount to pay and sell price >0
     * <b>Postcondition:</b> Constructs a new Deal Cards and initializes amount to be paid, sell price and message
     *  @param amountToPay amount to be paid to get the card
     * @param sellPrice selling price for the card
     * @param message the message on the card
     * @param path the image path
     */
    public DealCards(int amountToPay, int sellPrice, String message, String path, String choice1, String choice2){
        this.amountToPay = amountToPay;
        this.sellPrice = sellPrice;
        this.message = message;
        this.path = path;
        this.choice1 = choice1;
        this.choice2 = choice2;
    }


    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * <b>Accessor(selector):</b> Returns the amount to be paid
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the amount
     */
    @Override
    public int getAmountToPay() {
        return this.amountToPay;
    }

    /**
     * <b>Accessor(selector):</b> Returns the selling price for the card
     *
     * <b>Postcondition:</b> return value >= 0
     *
     * @return the selling price
     */
    public int getSellPrice(){
        return this.sellPrice;
    }
}
