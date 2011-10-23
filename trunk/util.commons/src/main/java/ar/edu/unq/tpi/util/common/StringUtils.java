package ar.edu.unq.tpi.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * Separates a camelcased string into a space separated one.
     * By example:
     * intoTheHollow -> (uncamelization) -> into The Hollow
     */
    public static String unCamelize(String inputString) {
        return unCamelize(inputString, false);
    }


    /**
     * Separates a camelcased string into a space separated one.
     * By example:
     * intoTheHollow -> (uncamelization) -> into The Hollow
     */
    public static String unCamelize(String inputString, boolean uncapitalizeWords) {
        Pattern p = Pattern.compile("\\p{Lu}");
        Matcher m = p.matcher(inputString);
        StringBuffer sb = new StringBuffer();

        int last = 0;
        while ( m.find() ) {
            if ( m.start() != last + 1 ) {
                String word = m.group();
                if ( uncapitalizeWords ) {
                    word = org.apache.commons.lang.StringUtils.uncapitalize(word);
                }
                m.appendReplacement(sb, " " + word);
            }
            last = m.start();
        }
        m.appendTail(sb);
        return sb.toString().trim();
    }
}
