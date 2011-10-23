package ar.edu.unq.tpi.util.services.email;


public class StringSource implements TextSource {

    private String text;


    public StringSource(String text) {
        this.text = text;
    }


    public String toString() {
        return this.text;
    }

}
