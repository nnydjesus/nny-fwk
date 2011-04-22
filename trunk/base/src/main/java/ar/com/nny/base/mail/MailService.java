package ar.com.nny.base.mail;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;

import ar.com.nny.base.configuration.jfig.InjectFromConfig;
import ar.com.nny.base.configuration.jfig.Service;
import ar.com.nny.base.exception.NonBusinessException;

@Service(denyable = true)
public class MailService {

    @InjectFromConfig(section = "mailing", key = "from.defaultSender")
    private String defaultSender;

    @InjectFromConfig(section = "mailing", key = "from.defaultAddress")
    private String defaultAddress;

    @InjectFromConfig(section = "mailing", key = "activated")
    private boolean mailingActivated;

    @InjectFromConfig(section = "mailing", key = "disabledAccount")
    private String disabledAccount;

    @InjectFromConfig(section = "mailing")
    private Properties mailingProperties;

    @InjectFromConfig(section = "mailing", key = "user")
    private String mailingUser;

    @InjectFromConfig(section = "mailing", key = "password")
    private String mailingPassword;

	  public MailService(){
        }

	    public void send(Mail mail)
	    {
			mail.setSession(MailSessionProvider.getSession(mailingProperties, mailingUser, mailingPassword));
	        try
	        {
	            Transport.send(mail.buildMail(this));
	        }
	        catch(MessagingException e)
	        {
	            throw new NonBusinessException("Unable to send an email", e);
	        }
	    }

	    public Mail buildMail()
	    {
	        Mail mail = new Mail();
	        mail.setFrom(defaultSender, defaultAddress);
	        return mail;
	    }

	    public static void send(String templateName, String destinatation, String subject)
	    {
	        List<String> destinatations = new ArrayList<String>();
	        destinatations.add(destinatation);
	        send(templateName, destinatations, subject);
	    }

	    public static void send(String templateName, List<String> destinatations, String subject)
	    {
	        TemplateSource source = new TemplateSource((new StringBuilder()).append("mail/").append(templateName).append(".vm").toString());
	        send(source, destinatations, subject);
	    }

	    public static void send(TemplateSource source, List<String> destinatations, String subject)
	    {
	        MailService mailService = new MailService();
	        Mail mail = mailService.buildMail();
	        mailService.addDestinatation(mail, destinatations, MailDestinationType.TO);
	        mail.setSubject(new StringSource(subject));
	        mail.setMailMode(MailMode.HTML);
	        mail.setBody(source);
	        mailService.send(mail);
	    }

	    protected void addDestinatation(Mail mail, String destinatation, MailDestinationType type)
	    {
	        mail.addDestination(destinatation, type);
	    }

	    protected void addDestinatation(Mail mail, List<String> destinatations, MailDestinationType type){
	    	for (String destinatation : destinatations) {
	    		addDestinatation(mail, destinatation, type)	;			
			}

	    }

	    public String getDefaultSender()
	    {
	        return defaultSender;
	    }

	    public String getDefaultAddress()
	    {
	        return defaultAddress;
	    }

	    public boolean isMailingActivated()
	    {
	        return mailingActivated;
	    }

	    public String getDisabledAccount()
	    {
	        return disabledAccount;
	    }

	    public Properties getMailingProperties()
	    {
	        return mailingProperties;
	    }

}
