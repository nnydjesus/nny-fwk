package ar.edu.unq.tpi.base.initialData;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.hibernate.HibernateException;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import ar.edu.unq.tpi.base.persistence.PersistenceManager;
import ar.edu.unq.tpi.commons.configuration.jfig.FrameworkConfiguration;
import ar.edu.unq.tpi.util.common.ReflectionUtils;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

public class EnhacedSchemaExport extends SchemaExport {

    private final Configuration cfg;


    public EnhacedSchemaExport(Configuration cfg) throws HibernateException {
        super(cfg);
        this.cfg = cfg;
    }


    @Override
    public void create(boolean script, boolean export) {
        Class dialect = ReflectionUtils.classForName(this.cfg.getProperty("hibernate.dialect"));
        String url = this.cfg.getProperty("hibernate.connection.url");

        if ( FrameworkConfiguration.recreateSchemaOnGeneration() && MySQLDialect.class.isAssignableFrom(dialect) && url != null ) {
            this.recreateSchema(url);
        }

        super.create(script, export);
    }


    private void recreateSchema(String url) {
        PreparedStatement dropStatement = null;
        PreparedStatement executeStatement = null;
        StatelessSession session = null;

        try {
            session = PersistenceManager.getInstance().getDefaultSessionFactory().openStatelessSession();
            Connection connection = session.connection();
            String schema = url.substring(url.lastIndexOf('/') + 1);

            dropStatement = connection.prepareStatement("drop database if exists " + schema);
            dropStatement.execute();
            executeStatement = connection.prepareStatement("create database " + schema);
            executeStatement.execute();

            PersistenceManager.getInstance().destroy();
        }
        catch ( Exception e ) {
            throw new UserException(e);
        }
        finally {
            try {
                dropStatement.close();
                executeStatement.close();
                session.close();
            }
            catch ( Exception e ) {
            }
        }
    }

}
