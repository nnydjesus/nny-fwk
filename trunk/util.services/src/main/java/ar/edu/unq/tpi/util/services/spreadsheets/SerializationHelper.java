package ar.edu.unq.tpi.util.services.spreadsheets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class SerializationHelper {




    public static Object fromBytes(final byte[] _b) {
        ObjectInputStream inputStream = null;
        Object result = null;

        try {
            inputStream = new ObjectInputStream(new ByteArrayInputStream(_b));
            result = inputStream.readObject();
            inputStream.close();
            inputStream = null;

        } catch (final Throwable e) {
            throw new RuntimeException("Programing", e); //FIXE
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // nothing to do
                }
            }
        }
        return result;
    }



    public static byte[] getBytes(final Object obj) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();
        return bos.toByteArray();
    }


    public static Serializable loadObjectFromFile(final File file) throws IOException, ClassNotFoundException {
        ObjectInputStream input = null;
        Serializable result = null;

        try {
            input = new ObjectInputStream(new FileInputStream(file));

            result = (Serializable) input.readObject();

            input.close();
            input = null;
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return result;
    }


    public static void saveObjectFile(final Object object, final File file) throws IOException {
        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new FileOutputStream(file));

            output.writeObject(object);

            output.flush();
            output.close();
            output = null;
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
}
