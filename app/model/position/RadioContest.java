package app.model.position;

/**
 * When a player lands on the Radio Contest position the bank offers 1000$ to the player that rolls the highest number.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class RadioContest implements Position{

    static private String url;
    final public int bank = 1000;

    /**
     * <b>Constructor:</b> Constructs a new instance of Radio Contest.
     */
    public RadioContest(){
        url = "images/radio.png";
    }

    @Override
    public String getURL() {
        return url;
    }

}
