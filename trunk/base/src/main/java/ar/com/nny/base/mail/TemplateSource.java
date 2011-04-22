package ar.com.nny.base.mail;


import java.io.StringWriter;
import java.util.Locale;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class TemplateSource implements TextSource {

    private Template template;
    private VelocityContext context;


    public TemplateSource(final String templateName, final Locale locale) {
        this.template = VelocityFacade.getInstance().getTemplate(locale, templateName);
        this.context = VelocityFacade.getInstance().getContext();
    }

    public TemplateSource(final String templateName) {
    	this.template = VelocityFacade.getInstance().getTemplate(templateName);
    	this.context = VelocityFacade.getInstance().getContext();
    }
    

    public void addObject(final String name, final Object obj) {
        this.context.put(name, obj);
    }
    
    public Object getObject(final String name) {
    	return this.context.get(name);
    }


    @Override
	public String toString() {
        try {
            final StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        }
        catch ( final Exception e ) {
            throw new RuntimeException("Exception while rendering template", e);
        }
    }


    public String toTemplateName() {
        return this.template.getName();
    }

}
