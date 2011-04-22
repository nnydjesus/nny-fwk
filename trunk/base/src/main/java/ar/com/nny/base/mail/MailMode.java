package ar.com.nny.base.mail;


import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import ar.com.nny.base.exception.NonBusinessException;

public enum MailMode {

    HTML {
        protected Email buildMail(String body) throws EmailException {
            HtmlEmail email = new HtmlEmail();
            email.setHtmlMsg(body);
            return email;
        }

        protected void attach(Email mail, EmailAttachment attachment) throws EmailException {
            ((HtmlEmail) mail).attach(attachment);
        }
    },
    PLAIN {
        protected Email buildMail(String body) throws EmailException {
            SimpleEmail email = new SimpleEmail();
            email.setMsg(body);
            return email;
        }

        protected void attach(Email mail, EmailAttachment attachment) throws EmailException {
            throw new NonBusinessException("Cannot add attachments to an non-multipart email. Try changing the mail mode to HTML");
        }
    };


    protected abstract Email buildMail(String body) throws EmailException;


    protected abstract void attach(Email mail, EmailAttachment attachment) throws EmailException;

}
