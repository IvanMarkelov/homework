import java.lang.reflect.InvocationTargetException;

public interface Worker {
    void doWork() throws InvocationTargetException, IllegalAccessException;
}
