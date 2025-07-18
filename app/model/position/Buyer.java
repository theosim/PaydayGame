package app.model.position;

/**
 * The Buyer position on the board where a player can sell their deal cards.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Buyer implements BuyDeal{

    final private String url;

    /**
     * <b>Constructor:</b> Constructs an instance of the class Buyer
     */
    public Buyer(){
        url = "images/buyer.png";
    }

    @Override
    public String getURL() {
        return url;
    }
}
