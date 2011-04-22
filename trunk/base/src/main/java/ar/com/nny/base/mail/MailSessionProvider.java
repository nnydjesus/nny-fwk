package ar.com.nny.base.mail;

import java.security.Security;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.lang.StringUtils;

import com.sun.net.ssl.internal.ssl.Provider;

/**
 * This is an Application level provider of mail sessions. This avoids multiple instanciation
 * of the Session
 *
 */
public class MailSessionProvider {

    private static Session SESSION;


    public static Session getSession(final Properties mailingProperties, final String user, final String password) {
        if ( SESSION == null ) {
            SESSION = buildSession(mailingProperties, user, password);
        }
        return SESSION;
    }


    private static Session buildSession(final Properties mailingProperties, final String user, final String password) {
        Security.addProvider(new Provider());
        final Session session = Session.getDefaultInstance(mailingProperties, getAuthenticator(user, password));
        return session;
    }


    /**
     * Este metodo evalua si el usuario seteo usuario
     * y password, si no la seteo significa que
     * el servidor no necesita autenticacion
     *
     * @return
     */
    protected static Authenticator getAuthenticator(final String user, final String password) {
        final String _user = user;
        final String _password = password;
        Authenticator _authenticator = null;
        if ( StringUtils.isNotBlank(_user) && StringUtils.isNotBlank(_password) ) {
            _authenticator = new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(_user, _password);
                }
            };
        }
        return _authenticator;
    }

}
