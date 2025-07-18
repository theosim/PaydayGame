package app.model.position;

/**
 * When a player lands on this position, they roll again and win money depending on what they rolled.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Sweepstakes implements Position{

    final private String url;
    final public int multiplier = 1000;

    /**
     * <b>Constructor:</b> Constructs a new instance of sweepstakes.
     */
    public Sweepstakes(){
        url = "images/sweep.png";
    }

    @Override
    public String getURL() {
        return url;
    }

}
