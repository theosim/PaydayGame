package app.model.position;
/**
 * The player that lands on this position gets 2 mail cards.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class Mail2 implements MailPosition{


    private final String url;

    /**
     * <b>Constructor:</b> Creates a new instance of Mail2 with a corresponding image.
     */
    public Mail2(){
        url = "images/mc2.png";
    }

    @Override
    public String getURL() {
        return url;
    }
}
