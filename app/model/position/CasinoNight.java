package app.model.position;

/**
 * When a player lands on the casino night position they either win or lose money depending on the roll that brought them to the position.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class CasinoNight implements Position{
    final private String url;
    final public int bet = 500;

    /**
     * Constructs a new instance of Casino Night.
     */
    public CasinoNight(){
        url ="images/casino.png";
    }


    @Override
    public String getURL() {
        return url;
    }

}
