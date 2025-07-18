package app.model.position;

/**
 * When a player lands on the yard sale position they pay their new roll number x 100 to the bank and get the top card of the deal stack for themselves without paying any more money.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class YardSale implements Position{

    static private String url;
    final public int multiplier = 100;

    /**
     * <b>Constructor:</b> Constructs a new instance of the Yard Sale position.
     */
    public YardSale(){
        url = "images/yard.png";
    }

    @Override
    public String getURL() {
        return url;
    }

}
