package ar.com.nny.base.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.exception.NonBusinessException;


/**
 * Se encarga de hacer busquedas de clases segun expresiones regulares en paquetes y predicates particulares.
 * Se intencion es para ser usada en momento de inicialiacion de la aplicacion.
 * <p/>
 * Se brinda la posibilidad de incluir directories adicionales a las busquedas ya que classLoader.getResources("")
 * solo devuelve entradas al classpath que son directorios. No logre encontrar la forma para que nos devuelva jars
 * <p/>
 * Al momento de construir la instancia, realiza las operaciones de acceso a disco y almacena una lista de classes
 * existentes en todos los jars y directorios que encontro por recursividad.
 * <p/>
 * This implementation is NOT thread safe.
 * This implementation does not work for inner classes.
 *
 */
public final class TypeInferencer {

    private static final Log LOGGER = LogFactory.getLog(TypeInferencer.class);

    private final Set<String> resolvedClasses = new HashSet<String>(500);
    private ClassLoader classLoader = null;


    /**
     * Classloader default y sin directorios adicionales
     */
    public TypeInferencer() {
        this(TypeInferencer.class.getClassLoader(), resolveExtraDirectories());
    }


	private static Set<String> resolveExtraDirectories() {
//		String workingPath = FlexyConfiguration.getWorkingDirectoryFile().getAbsolutePath();
		String workingPath ="src/dev/local";
		File webInfDir = new File(workingPath + File.separator + "WEB-INF");
		if(webInfDir.exists()) {
			LOGGER.debug("WEB-INF dir (" + webInfDir.getAbsolutePath() + ") found in working.directory, sounds like tomcat server.");
			return Collections.singleton(webInfDir.getAbsolutePath());
		} else {
			LOGGER.debug("no WEB-INF dir (" + webInfDir.getAbsolutePath() + ") found in working.directory, sounds like dev environment.");
			return null;
		}
	}


    /**
     * @param _extraDirectories lista de directorios adicionales donde buscar. posiblemente un sistema productivo necesite
     *                          pasar ciertos directorios dados en la configuracion de la aplicacion.
     */
    public TypeInferencer(final Collection<String> _extraDirectories) {
        this(TypeInferencer.class.getClassLoader(), _extraDirectories);
    }


    protected sun.misc.URLClassPath getURLClassPath(final URLClassLoader loader) throws Exception{
        return (sun.misc.URLClassPath)getUcpField().get(loader);
    }
    
    private Field ucpField;
    
    private Field getUcpField() throws Exception {
        if (ucpField == null) {
            // Add them to the URLClassLoader's classpath
            ucpField = AccessController.doPrivileged(
                new PrivilegedAction<Field>(){
                    public Field run() { 
                        java.lang.reflect.Field ucp = null;
                        try{
                        ucp = URLClassLoader.class.getDeclaredField("ucp");
                        ucp.setAccessible(true);
                        } catch (final Exception e2){
                            e2.printStackTrace();
                        }
                        return ucp;
                    }
                }
            );
        }
        
        return ucpField;
    }
    
    /**
     * @param _classLoader      en el caso de utilizar otro al default
     * @param _extraDirectories lista de directorios adicionales donde buscar. posiblemente un sistema productivo necesite
     *                          pasar ciertos directorios dados en la configuracion de la aplicacion.
     */
    public TypeInferencer(final ClassLoader _classLoader, final Collection<String> _extraDirectories) {
        final long _currentTime = System.currentTimeMillis();
        this.classLoader = _classLoader;
        inferenceFromClassLoader(_classLoader);
        
        // working with extradirectories
        inferenceFromExtraDirectories(_extraDirectories);
        LOGGER.info("TypeInferencer found [" + resolvedClasses.size() + "] classes in [" + (System.currentTimeMillis() - _currentTime) + "] msecs");
    }


    private void inferenceFromClassLoader(final ClassLoader _classLoader) {
    	LOGGER.debug("Inferencing from classloader: " + _classLoader.toString());
    	Enumeration<URL> _enumeration;
    	
    	try {
    		// getting the default directories
    		_enumeration = _classLoader.getResources("");
    	}
    	catch ( final IOException _ioException ) {
    		throw new NonBusinessException("Error while getting resources on classLoader.getResources(): " + _ioException.getMessage(), _ioException);
    	}
    	
    	while ( _enumeration.hasMoreElements() ) {
    		try {
    			URL _url = _enumeration.nextElement();
    			final String urlString = _url.toExternalForm().replaceAll(" ", "%20");
    			_url = new URL(urlString);
    			workRecursivelyOn(new File(_url.toURI()));
    		}
    		catch ( final MalformedURLException e ) {
    			throw new NonBusinessException(e);
    		}
    		catch ( final URISyntaxException e ) {
    			throw new NonBusinessException(e);
    		}
    	}
    	
    	try {
    		URL[] urLs = getURLClassPath((URLClassLoader)_classLoader).getURLs();
    		for (URL _url : urLs) {
    			final String urlString = _url.toExternalForm().replaceAll(" ", "%20");
    			_url = new URL(urlString);
    			workRecursivelyOn(new File(_url.toURI()));
    		}
    	} catch (final Exception e) {
    		throw new NonBusinessException(e);
    	}
    }
    
    
	private void inferenceFromExtraDirectories(final Collection<String> _extraDirectories) {
		if ( _extraDirectories != null ) {
            for ( final String _extraDirectory : _extraDirectories ) {
                workRecursivelyOn(new File(_extraDirectory));
            }
        }
	}


    private void workRecursivelyOn(final File _file) {
        LOGGER.info("inferencing for location: [" + _file.getAbsolutePath() + "]...");
        workRecursivelyOn(_file, _file);
    }


    private void workRecursivelyOn(final File _file, final File _directory) {
        if ( _file.isDirectory() ) {
            LOGGER.debug("working on directory: [" + _file.getAbsolutePath() + "]...");

            for ( final Object _o : FileUtils.listFiles(_file, new SuffixFileFilter(new String[]{"jar", "class"}), TrueFileFilter.INSTANCE) ) {
                workRecursivelyOn((File) _o, _directory);
            }
        }
        else if ( _file.isFile() ) {
            if ( _file.getName().endsWith(".jar") ) {
                handleJarFile(_file);
            }
            else {
                handleClassFile(_file.getAbsolutePath(), _directory);
            }
        }
    }


    private void handleJarFile(final File _file) {
        LOGGER.debug("adding classes for jar: [" + _file.getAbsolutePath() + "]...");

        try {
            handleJarFileImpl(_file);
        }
        catch ( final IOException _ioException ) {
            throw new NonBusinessException("Error while inferring on jar: [" + _file.getAbsolutePath() + "]: " + _ioException.getMessage(), _ioException);
        }
    }


    private void handleJarFileImpl(final File _file) throws IOException {
        JarFile _jarFile = null;

        try {
            _jarFile = new JarFile(_file);
            final Enumeration<JarEntry> _entryEnumeration = _jarFile.entries();

            while ( _entryEnumeration.hasMoreElements() ) {
                handleClassFile(_entryEnumeration.nextElement().getName(), null);
            }
        }
        finally {
            if ( _jarFile != null ) {
                _jarFile.close();
            }
        }
    }


    private void handleClassFile(final String _fileName, final File _rootDirectory) {
        if ( !_fileName.endsWith(".class") || _fileName.indexOf("$") != -1 ) {
            return;
        }

        String _className = _fileName;

        if ( _rootDirectory != null ) {
            _className = _className.substring(_rootDirectory.getAbsolutePath().length() + 1);
        }
        _className = _className.substring(0, _className.length() - 6);
        _className = _className.replace('/', '.');
        _className = _className.replace('\\', '.');

        resolvedClasses.add(_className);
        LOGGER.debug("class: [" + _className + "] was added");
    }


    /**
     * @param _packageRegExp expresion regular para filtrar paquetes. por ejemplo: ".*com\\.mindset.*nomenclador.*" - es opcional
     * @param _predicate     para filtrar clases. posiblemente se utilice junto a AnnotationClassPredicate - es opcional
     * @return una lista de classes encontradas segun la busqueda recursiva y los filtros ingresados por parametros
     * @see com.mindset.architecture.typeInferencer.AnnotationPredicate
     */
    public List<Class> getClassesFor(final String _packageRegExp, final Predicate<Class> _predicate) {
    	LOGGER.debug("Called getClassesFor: " + _packageRegExp + ", " + _predicate.toString());
        final List<Class> _result = new ArrayList<Class>();

        for ( final String _resolvedClass : resolvedClasses ) {
            try {
                if ( _packageRegExp != null ) {
                    if ( !_resolvedClass.matches(_packageRegExp) ) {
                        continue;
                    }
                }

                Class _class = null;
                if (classLoader == null) {
                    _class = Class.forName(_resolvedClass);
                } else {
                    _class = classLoader.loadClass(_resolvedClass);
                }

                if ( _predicate != null ) {
                    if ( !_predicate.evaluate(_class) ) {
                        continue;
                    }
                }
                _result.add(_class);
            }
            catch ( final Throwable _exception ) {
                throw new NonBusinessException("Error while checking class [" + _resolvedClass + "]: " + _exception.getMessage(), _exception);
            }
        }
        return _result;
    }
}
