package ar.com.nny.base.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import ar.com.nny.base.dao.DAOResolver;
import ar.com.nny.base.persistence.PersistentObject;

public final class RandomUtils {

    private static SecureRandom random;


    public static Random getRandom() {
        if ( random == null ) {
            random = new SecureRandom();
        }
        return random;
    }


    public static <T extends PersistentObject> T getRandomPersistentObject(Class<T> poClass) {
        List<T> all = DAOResolver.getDAOFor(poClass).getAll();
        return getRandomElement(all);
    }


    public static <T> T getRandomElement(Collection<T> collection) {
        if ( collection == null || collection.isEmpty() ) {
            return null;
        }

        List<T> collectionAsList;
        if ( collection instanceof List ) {
            collectionAsList = (List<T>) collection;
        }
        else {
            collectionAsList = new ArrayList<T>(collection);
        }

        return collectionAsList.get(getRandomInt(collection.size()));
    }


    public static <T> T getRandomElement(T[] array) {
        if ( array == null || array.length == 0 ) {
            return null;
        }
        return array[getRandomInt(array.length)];
    }


    public static int getRandomInt(int inclusiveMin, int exclusiveMax) {
        return inclusiveMin + getRandom().nextInt(exclusiveMax - inclusiveMin);
    }


    public static String getRandomSafeString() {
        return getRandomSafeString(10);
    }


    private final static char[] SAFE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();


    public static String getRandomSafeString(final int size) {
        final StringBuffer _buffer = new StringBuffer(size);

        while ( _buffer.length() < size ) {
            _buffer.append(SAFE_STRING[getRandomInt(0, SAFE_STRING.length - 1)]);
        }
        return _buffer.toString();
    }


    public static int getRandomInt(int exclusiveMax) {
        return getRandom().nextInt(exclusiveMax);
    }


    public static <T extends Enum> T getRandomEnumElement(Class<T> enumClass) {
        return getRandomElement(enumClass.getEnumConstants());
    }
}
