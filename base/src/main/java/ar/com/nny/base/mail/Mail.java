package ar.com.nny.base.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ar.com.nny.base.exception.NonBusinessException;

public class Mail {
    private MailDestination from;
    private MailDestination replyTo = null;
    private List<MailDestination> destinations = new ArrayList<MailDestination>();

    //    private List<EmailAttachment> attachments = new ArrayList();
    protected String subject;

    private MailMode mode = MailMode.PLAIN;
    private String body;
    private Session session;

    public void addDestination(final String name, final String address, final MailDestinationType type) {
        this.destinations.add(new MailDestination(name, address, type));
    }


    public void addDestination(final String address, final MailDestinationType type) {
        this.addDestination(null, address, type);
    }


    public void setSubject(final TextSource subject) {
        this.subject = subject.toString();
    }


    public void setBody(final TextSource body) {
        this.body = body.toString();
    }


    public void setFrom(final String sender, final String address) {
        this.from = new MailDestination(sender, address, MailDestinationType.FROM);
    }

    public void setReplyTo(final String address) {
    	this.replyTo = new MailDestination(null, address, MailDestinationType.REPLY_TO);
    }
    

    public void setMailMode(final MailMode mode) {
        this.mode = mode;
    }

    public Message buildMail(final MailService mailService) {
        try {
            final MimeMultipart multipart = new MimeMultipart();
            final MimeMessage message = new MimeMessage(session);
            message.setSubject(subject);

            from.addTo(message);

            if(!mailService.isMailingActivated()) {
                new MailDestination(mailService.getDisabledAccount(), mailService.getDisabledAccount(), MailDestinationType.TO).addTo(message);
            }
            else {
                for (final MailDestination destination : destinations) {
                    destination.addTo(message);
                }
            }

            if(replyTo != null) {
            	replyTo.addTo(message);
            }
            
            from.addTo(message);

            final MimeBodyPart mbp = new MimeBodyPart();
            if (mode == MailMode.HTML) {
                mbp.setContent(body, "text/html");
            } else {
                mbp.setText(body);
            }
            multipart.addBodyPart(mbp);
            message.setContent(multipart);

            return message;
        }
        catch (final Exception e) {
            throw new NonBusinessException("Exception during mail construction", e);
        }
    }

    public void setSession(final Session session) {
        this.session = session;
    }
}
