package ar.com.nny.base.configuration.jfig;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class BaseConfiguration {
    //This class must not reference LOGGER. Just because, trust me.
    //Okey okey, FlexyDailyRollingFileAppender use this and we should avoid circular dependencies.

    public static void setValue(final String section, final String parameter, final String value) {
        JFigConfiguration.getInstance().setValue(section, parameter, value);
    }

    public static String getIndexUrl() {
        return JFigConfiguration.getInstance().getString("enviroment", "index.url");

    }

    public static String getServerMAC() {
        return JFigConfiguration.getInstance().getString("enviroment", "mac.address");
    }


    public static boolean isInitializerAllowed() {
        return JFigConfiguration.getInstance().getBoolean("enviroment", "initializer.on");
    }


    public static File getWorkingDirectoryFile() {
        final File _file = new File(JFigConfiguration.getInstance().getString("enviroment", "working.directory"));
        _file.mkdirs();
        return _file;
    }


    public static File getWebappDirectoryFile() {
        final File _file = new File(JFigConfiguration.getInstance().getString("enviroment", "webapp.directory"));
        _file.mkdirs();
        return _file;
    }


    public static File getAttachmentsDirectoryFile() {
    	final File _file = new File(JFigConfiguration.getInstance().getString("attachments", "directory"));
    	_file.mkdirs();
    	return _file;
    }
    
    public static String getAttachmentsServletName() {
    	return JFigConfiguration.getInstance().getString("attachments", "servletName");
    }
    
    
    @Deprecated
    public static String getWorkingDirectory() {
        return getWorkingDirectoryFile().getAbsolutePath();
    }


    public static Properties getHibernateProperties() {
        return JFigConfiguration.getInstance().getSectionAsProperties("hibernate");
    }


    public static Map<String, Properties> getAlternateHibernateProperties() {
        final Map<String, Properties> result = new HashMap();

        final String string = JFigConfiguration.getInstance().getString("hibernate", "alternateSessionFactories");
        final String[] alternateSessionFactories = string.split(",");
        for (final String alternate : alternateSessionFactories) {
            if (!alternate.equals("")) {
                final Properties properties = JFigConfiguration.getInstance().getSectionAsProperties("hibernate." + alternate);
                result.put(alternate, properties);
            }
        }
        return result;
    }


    public static List<String> getModules() {
        final String list = JFigConfiguration.getInstance().getString("modules", "list");
        final String[] splittedList = list.split(",");
        return Arrays.asList(splittedList);
    }


    public static String getPackagePrefix() {
        return JFigConfiguration.getInstance().getString("modules", "package.prefix");
    }


    public static boolean retainStackTraceOnFlexExceptions() {
        return JFigConfiguration.getInstance().getBoolean("flex", "retainExceptionStackTrace");
    }


    public static Properties getMailingProperties() {
        return JFigConfiguration.getInstance().getSectionAsProperties("mailing");
    }

    public static String getDisabledAccount() {
        return JFigConfiguration.getInstance().getString("mailing", "disabledAccount");
    }


    public static boolean isMailingDisabled() {
        return !JFigConfiguration.getInstance().getBoolean("mailing", "activated");
    }


    public static String getMailingUser() {
        return JFigConfiguration.getInstance().getString("mailing", "user");
    }


    public static String getMailingPassword() {
        return JFigConfiguration.getInstance().getString("mailing", "password");
    }


    public static String getMailingDefaultFromSender() {
        return JFigConfiguration.getInstance().getString("mailing", "from.defaultSender");
    }


    public static String getMailingDefaultFromAddress() {
        return JFigConfiguration.getInstance().getString("mailing", "from.defaultAddress");
    }


    public static String getWSNamespace() {
        return JFigConfiguration.getInstance().getString("webservice", "defaultNamespace");
    }


    public static String getWSTypeNamespace() {
        return JFigConfiguration.getInstance().getString("webservice", "defaultTypeNamespace");
    }


    public static File getWSTemporalAttachmentFile() {
        final File _file = new File(getWorkingDirectoryFile().getAbsolutePath() + File.separator + JFigConfiguration.getInstance().getString("webservice", "attachment.directory"));
        _file.mkdirs();
        return _file;
    }


    public static int getWSAttachmentOutboundThreshold() {
        return JFigConfiguration.getInstance().getInt("webservice", "attachment.outboundThreshold");
    }


    public static int getWSAttachmentInboundThreshold() {
        return JFigConfiguration.getInstance().getInt("webservice", "attachment.inboundThreshold");
    }


    public static boolean getExceptionSyserrLogging() {
        return JFigConfiguration.getInstance().getBoolean("enviroment", "exception.syserr.logging");
    }

    public static boolean isDenyRemoteServices() {
    	return JFigConfiguration.getInstance().getBoolean("enviroment", "deny.remote.services");
    }
    

    public static boolean recreateSchemaOnGeneration() {
        return JFigConfiguration.getInstance().getBoolean("hibernate", "ddl.recreateSchema");
    }
    

}
