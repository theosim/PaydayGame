package app.model.card;

/**
 * Class for the 48 mail cards
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class MailCards implements Card {
    final public String message;
    public final String path;
    final public String type;
    private final String choice;

    /**
     * <b>Constructor:</b> Constructs a new instance of MailCards
     *
     * <b>Postcondition:</b> Constructs a new MailCards and initializes the message
     * @param message the message on the card
     * @param path
     * @param type
     * @param choice
     */
    public MailCards(String message, String path, String type, String choice){
        this.message = message;
        this.path = path;
        this.type = type;
        this.choice = choice;
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
        return 0;
    }

    public String getChoice() {
        return choice;
    }
}
