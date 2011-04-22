package ar.com.nny.base.persistence.injector;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.dao.DAOResolver;
import ar.com.nny.base.dao.GenericDao;
import ar.com.nny.base.utils.ReflectionUtils;

public class DAOInjector extends Injector {

    private static Log LOGGER = LogFactory.getLog(DAOInjector.class);


    public boolean applies(Field field) {
        Class fieldType = field.getType();
        return GenericDao.class.isAssignableFrom(fieldType);
    }


    public void doInject(Field field, Object object) {
        GenericDao dao = null;

        if ( GenericDao.class.equals(field.getType()) ) {
            Class businessType = ReflectionUtils.readGeneric(field, 0);
            LOGGER.debug("Injecting dao " + businessType.getSimpleName() + " on object " + object);
            dao = DAOResolver.getDAOFor(businessType);
        }
        else {
            dao = DAOResolver.getCustomDAO(field.getType());
        }

        field.setAccessible(true);
        ReflectionUtils.writeField(object, field, dao);
    }

}
