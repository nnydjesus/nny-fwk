package ar.edu.unq.tpi.base.persistence.injector;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.edu.unq.tpi.base.repository.GenericRepository;
import ar.edu.unq.tpi.base.repository.RepositoryResolver;
import ar.edu.unq.tpi.commons.configuration.injector.Injector;
import ar.edu.unq.tpi.util.common.ReflectionUtils;

public class RepositoryInjector extends Injector {

    private static Log LOGGER = LogFactory.getLog(RepositoryInjector.class);


    public boolean applies(Field field) {
        Class fieldType = field.getType();
        return GenericRepository.class.isAssignableFrom(fieldType);
    }


    public void doInject(Field field, Object object) {
        GenericRepository repository = null;

        if ( GenericRepository.class.equals(field.getType()) ) {
            Class businessType = ReflectionUtils.readGeneric(field, 0);
            LOGGER.debug("Injecting repository " + businessType.getSimpleName() + " on object " + object);
            repository = RepositoryResolver.getRepositoryFor(businessType);
        }
        else {
            repository = RepositoryResolver.getCustomRepository(field.getType());
        }

        field.setAccessible(true);
        ReflectionUtils.writeField(object, field, repository);
    }

}
