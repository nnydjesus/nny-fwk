package ar.com.nny.base.mail;


import java.io.UnsupportedEncodingException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public enum MailDestinationType {

    TO {
        @Override
		protected void addTo(final String address, final Message mail) throws MessagingException {
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
        }
        @Override
		protected void addTo(final String name, final String address, final Message mail) throws UnsupportedEncodingException, MessagingException {
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(address, name));
        }
    },
    CC {
        @Override
		protected void addTo(final String address, final Message mail) throws MessagingException {
            mail.addRecipient(Message.RecipientType.CC, new InternetAddress(address));
        }
        @Override
		protected void addTo(final String name, final String address, final Message mail) throws UnsupportedEncodingException, MessagingException {
            mail.addRecipient(Message.RecipientType.CC, new InternetAddress(address, name));
        }
    },
    BCC {
        @Override
		protected void addTo(final String address, final Message mail) throws MessagingException {
            mail.addRecipient(Message.RecipientType.BCC, new InternetAddress(address));
        }
        @Override
		protected void addTo(final String name, final String address, final Message mail) throws UnsupportedEncodingException, MessagingException {
            mail.addRecipient(Message.RecipientType.BCC, new InternetAddress(address, name));
        }
    },
    REPLY_TO {
        @Override
		protected void addTo(final String address, final Message mail) throws MessagingException {
            mail.setReplyTo(new Address[] {new InternetAddress(address)});
        }
        @Override
		protected void addTo(final String name, final String address, final Message mail) throws UnsupportedEncodingException, MessagingException {
        	throw new UnsupportedOperationException("Can't add reply-to with contact name");
        }
    },
    FROM {
        @Override
		protected void addTo(final String address, final Message mail) throws MessagingException {
            mail.setFrom(new InternetAddress(address));
        }
        @Override
		protected void addTo(final String name, final String address, final Message mail) throws UnsupportedEncodingException, MessagingException {
            mail.setFrom(new InternetAddress(address, name));
        }
    };


    protected abstract void addTo(String address, Message mail) throws MessagingException;


    protected abstract void addTo(String name, String address, Message mail) throws UnsupportedEncodingException, MessagingException;

}
