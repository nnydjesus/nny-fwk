package ar.com.nny.base.generator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import ar.com.nny.base.exception.NonBusinessException;
import au.com.bytecode.opencsv.CSVReader;

public class CSVParser {

    private final Object object;
    private final CVSLineCallback callback;


    public CSVParser(Object object, CVSLineCallback callback) {
        this.object = object;
        this.callback = callback;
    }


    public void parseStream(InputStream csv) {
        this.parse(new CSVReader(new InputStreamReader(csv)));
    }


    public void parseString(String csv) {
        this.parse(new CSVReader(new StringReader(csv)));
    }


    private void parse(CSVReader reader) {
        try {
            String[] nextLine;
            while ( (nextLine = reader.readNext()) != null ) {
                Object[] parameters = this.processLine(nextLine, this.callback.getParameterTypes());
                this.callback.invoke(parameters);
            }
        }
        catch ( IOException e ) {
            throw new NonBusinessException(e);
        }
        finally {
            try {
                reader.close();
            }
            catch ( Exception e ) {
            }
        }
    }


    private Object[] processLine(String[] splittedLine, Class[] parameterTypes) {
        List result = new ArrayList();

        for ( int i = 0; i < parameterTypes.length; i++ ) {
            Class parameterType = parameterTypes[i];

            //TODO: Refactorizar + Agregar conversion automatica a mas tipos.
            if ( String[].class.equals(parameterType) ) {
                if ( parameterTypes.length > i + 1 ) {
                    throw new NonBusinessException("String[] should be last parameter");
                }

                result.add(splittedLine);

            }
            else if ( String.class.equals(parameterType) ) {
                result.add(splittedLine[i]);
            }
            else if ( parameterType.isEnum() ) {
                try {
                    result.add(Enum.valueOf(parameterType, splittedLine[i]));
                }
                catch ( IllegalArgumentException e ) {
                    result.add(Enum.valueOf(parameterType, splittedLine[i].toUpperCase()));
                }
            }
        }

        return result.toArray();
    }

}
