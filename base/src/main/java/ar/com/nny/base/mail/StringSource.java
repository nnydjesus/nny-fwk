package ar.com.nny.base.mail;


public class StringSource implements TextSource {

    private String text;


    public StringSource(String text) {
        this.text = text;
    }


    public String toString() {
        return this.text;
    }

}
