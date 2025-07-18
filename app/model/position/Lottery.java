package app.model.position;

/**
 * When a player lands on the lottery position the bank offers 1000$ that the players have a chance to win through lottery.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Lottery implements Position{

    static private String url;
    final public int bank = 1000;

    /**
     * <b>Constructor:</b> Constructs a new instance of the Lottery position.
     */
    public Lottery(){
        url = "images/lottery.png";
    }


    @Override
    public String getURL() {
        return url;
    }

}
