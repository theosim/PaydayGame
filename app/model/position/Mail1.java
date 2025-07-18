package app.model.position;

/**
 * The player that lands on this position gets 1 mail card.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Mail1 implements MailPosition {
    private final String url;

    /**
     * <b>Constructor:</b> Creates a new instance of Mail1 with a corresponding image
     */
    public Mail1(){
        url = "images/mc1.png";
    }

    @Override
    public String getURL() {
        return url;
    }
}
