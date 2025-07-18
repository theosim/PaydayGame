package app.model.jackpot;

/**
 * Whenever a player rolls 6 on their turn they get the Jackpot money and add money to the Jackpot whenever a card asks them to.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Jackpot {

    private String url;
    private int money;

    /**
     * <b>Constructor:</b> Constructs a new instance of the class Jackpot
     *
     * <b>Postcondition:</b> Constructs a new Jackpot with starting money = 0
     *
     * @param img the image url
     */
    public Jackpot(String img){
        this.url = img;
        this.money = 0;
    }

    /**
     * <b>Accessor(selector):</b> Returns the image url
     *
     * <b>Postcondition:</b> Returns the image url
     *
     * @return the image url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * <b>Transformer(mutative):</b> Sets a new url for the image
     *
     * <b>Postcondition:</b> Sets a new url for the image
     *
     * @param url the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * <b>Accessor(selector):</b> Returns the Jackpot money
     *
     * <b>Postcondition:</b> Returns the Jackpot money
     *
     * @return the money
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * <b>Transformer(mutative):</b> Sets the new value for the money
     *
     * <b>Postcondition:</b> Sets the new value for the money
     *
     * @param money the new value for money
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
