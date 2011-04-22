package ar.com.nny.base.mail;

import java.util.Properties;

public class PropertyMail extends Properties{

	private static final long serialVersionUID = 2881513999640984484L;

	public PropertyMail(){
//        setProperty("from.defaultAddress", "nny.fwk@gmail.com");
//        setProperty("from.defaultSender", "NNY");
        setProperty("mail.smtp.host", "smtp.gmail.com");
        setProperty("mail.smtp.auth", "true");
        setProperty("mail.debug", "true");
        setProperty("mail.smtp.port", "465");
        setProperty("mail.smtp.socketFactory.port", "465");
        setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        setProperty("mail.smtp.socketFactory.fallback", "false");
    }
}