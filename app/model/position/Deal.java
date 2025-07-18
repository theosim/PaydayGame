package app.model.position;

/**
 * The deal position where a player draws a deal card and chooses to get it or not.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Deal implements BuyDeal {

    private final String url;

    /**
     * <b>Constructor:</b> Constructs a new instance of the deal position.
     */
    public Deal(){
        url = "images/deal.png";
    }


    @Override
    public String getURL() {
        return url;
    }
}
