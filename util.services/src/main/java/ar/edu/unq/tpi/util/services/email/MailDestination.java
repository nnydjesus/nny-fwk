package ar.edu.unq.tpi.util.services.email;


import javax.mail.Message;

import ar.edu.unq.tpi.util.commons.exeption.UserException;

public class MailDestination {

    private final String name;
    private final String address;
    private final MailDestinationType type;


    public MailDestination(final String name, final String address, final MailDestinationType type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }


    protected void addTo(final Message mail) {
        try {
            if (name == null) {
                this.type.addTo(address, mail);
            } else {
                this.type.addTo(name, address, mail);
            }
        } catch (final Exception e) {
            throw new UserException(e);
        }
    }


}
