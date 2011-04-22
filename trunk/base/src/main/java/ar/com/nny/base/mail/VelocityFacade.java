package ar.com.nny.base.mail;
import java.util.Locale;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityFacade {

    private static VelocityFacade INSTANCE;


    public static VelocityFacade getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new VelocityFacade();
        }
        return INSTANCE;
    }


    private VelocityFacade() {
        try {
//            Properties props = new Properties();
//            props.load(this.getClass().getClassLoader().getResourceAsStream("velocity.properties"));
//            Velocity.init(props);
        }
        catch ( Exception e ) {
            throw new RuntimeException("Problem during velocity initialization", e);
        }
    }


    public Template getTemplate(Locale locale, String templateName)
    {
        try
        {
            return Velocity.getTemplate((new StringBuilder()).append("./src/main/resources/template/").append(locale).append("/").append(templateName).append(".vm").toString());
        }
        catch(Exception e)
        {
            throw new RuntimeException((new StringBuilder()).append("Problem while creating template ").append(templateName).append(" for locale ").append(locale).toString(), e);
        }
    }

    public Template getTemplate(String templateName)
    {
        try
        {
            return Velocity.getTemplate((new StringBuilder()).append("./src/main/resources/template/").append(templateName).toString());
        }
        catch(Exception e)
        {
            throw new RuntimeException((new StringBuilder()).append("Problem while creating template ").append(templateName).toString(), e);
        }
    }
    
    
    public VelocityContext getContext() {
        return new VelocityContext();
    }

}
