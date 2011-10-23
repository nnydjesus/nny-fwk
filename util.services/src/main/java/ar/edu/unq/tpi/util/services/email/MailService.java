package ar.edu.unq.tpi.util.services.email;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;

import ar.edu.unq.tpi.commons.configuration.jfig.InjectFromConfig;
import ar.edu.unq.tpi.util.commons.exeption.UserException;
import ar.edu.unq.tpi.util.services.services.Service;

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

    public MailService() {
    }

    public void send(final Mail mail) {
        mail.setSession(MailSessionProvider.getSession(mailingProperties, mailingUser, mailingPassword));
        try {
            Transport.send(mail.buildMail(this));
        } catch (MessagingException e) {
            throw new UserException("Unable to send an email", e);
        }
    }

    public Mail buildMail() {
        Mail mail = new Mail();
        mail.setFrom(defaultSender, defaultAddress);
        return mail;
    }

    public void send(final String templateName, final String subject, final String... destinatations) {
        TemplateSource source = new TemplateSource(new StringBuilder().append("mail/").append(templateName)
                .append(".vm").toString());
        this.send(source, subject, destinatations);
    }

    public void send(final TextSource source, final String subject, final String... destinatations) {
        Mail mail = this.buildMail();
        for (String destinatation : destinatations) {
            this.addDestinatation(mail, destinatation, MailDestinationType.TO);
        }
        mail.setSubject(new StringSource(subject));
        mail.setMailMode(MailMode.HTML);
        mail.setBody(source);
        this.send(mail);
    }

    protected void addDestinatation(final Mail mail, final String destinatation, final MailDestinationType type) {
        mail.addDestination(destinatation, type);
    }

    protected void addDestinatation(final Mail mail, final List<String> destinatations, final MailDestinationType type) {
        for (String destinatation : destinatations) {
            this.addDestinatation(mail, destinatation, type);
        }

    }

    public String getDefaultSender() {
        return defaultSender;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public boolean isMailingActivated() {
        return mailingActivated;
    }

    public String getDisabledAccount() {
        return disabledAccount;
    }

    public Properties getMailingProperties() {
        return mailingProperties;
    }

}
