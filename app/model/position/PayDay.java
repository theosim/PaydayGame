package app.model.position;

/**
 * When a player lands on the Payday position they get their monthly salary and pay their loans and bills.
 *
 * @version 1.0
 * @author Theodora Symeonidou (csd4748)
 */
public class PayDay implements Position{
    final public int salary = 3500;
    final public double fee = 0.1;
    final private String url;

    /**
     * <b>Constructor:</b> Creates an instance of the payday position.
     */
    public PayDay(){
        url = "images/pay.png";
    }

    @Override
    public String getURL() {
        return url;
    }

}
