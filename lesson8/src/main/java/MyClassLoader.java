import java.io.*;

public class MyClassLoader extends ClassLoader {
    public Class<?> loadMyClass(String name) {
        try {
            return findClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File(name);
        if (!f.exists()) throw new IllegalArgumentException("Invalid path name to the class!");

        byte[] bytes = new byte[(int) f.length()];

        try (InputStream is = new FileInputStream(f)) {
            is.read(bytes);
            Class c = defineClass(null, bytes, 0, bytes.length);
            return c;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
