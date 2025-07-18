package app.model.position;

public class Start implements Position {

    private final String url;

    public Start(){
        url = "images/start.png";
    }

    @Override
    public String getURL() {
        return url;
    }
}
