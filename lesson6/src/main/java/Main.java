import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
    }

    /**
     * This method takes an object and two sets of Strings with names of fields that should be changed. Set<String>
     * fieldsToCleanup contains names of fields that will be nullified or set to their default values, depending
     * on the type. Set<String> fieldsToPrint will be gathered together in one String and printed in console.
     * If object is of type "Map", it's instead of fields, the work will be done on nodes of the Map. Values of keys
     * in fieldsToCleanUp will be deleted, values of keys in fieldsToPrint will be printed in console.
     *
     * @param object          any Object or Map
     * @param fieldsToCleanup Set of field/key names of the object to perform cleaning on them (set them to default
     *                        or null)
     * @param fieldsToOutput  Set of field/key names of the object to print in console.
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void cleanup(Object object,
                               Set<String> fieldsToCleanup,
                               Set<String> fieldsToOutput) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        Class<?> class_ = object.getClass();

        Object objectClone = object;

        if (Map.class.isAssignableFrom(class_)) {
            Class[] cArg = new Class[1];
            cArg[0] = Object.class;

            Method containsMapKey = class_.getMethod("containsKey", cArg);
            Method removeByKey = class_.getMethod("remove", cArg);
            Method getByKey = class_.getMethod("get", cArg);

            for (String s : fieldsToCleanup) {
                if ((boolean) containsMapKey.invoke(objectClone, s)) {
                    removeByKey.invoke(objectClone, s);
                } else {
                    throw new IllegalArgumentException("The object doesn't contain this field/key: " + s);
                }
            }
            for (String s : fieldsToOutput) {
                if ((boolean) containsMapKey.invoke(objectClone, s)) {
                    sb.append(getByKey.invoke(objectClone, s));
                    sb.append("\n");
                } else {
                    throw new IllegalArgumentException("The object doesn't contain this field/key: " + s);
                }
            }
        } else {
            Field[] declaredFields = class_.getDeclaredFields();
            Set<String> fieldNames = new HashSet<>();
            for (Field f : declaredFields) {
                fieldNames.add(f.getName());
            }

            Set<String> bothFields = new HashSet<>();
            bothFields.addAll(fieldsToCleanup);
            bothFields.addAll(fieldsToOutput);

            for (String s : bothFields) {
                if (!fieldNames.contains(s)) {
                    throw new IllegalArgumentException("The object doesn't contain this field/key: " + s);
                }
            }

            for (Field f : declaredFields) {
                if (fieldsToCleanup.contains(f.getName())) {
                    if (f.getType().isPrimitive()) {
                        if (f.getType() == Boolean.TYPE) {
                            f.set(objectClone, false);
                        } else {
                            f.set(objectClone, 0);
                        }
                    } else {
                        f.set(objectClone, null);
                    }
                } else if (fieldsToOutput.contains(f.getName())) {
                    sb.append(f.get(objectClone));
                    sb.append("\n");
                }
            }
        }
        object = objectClone;
        System.out.println(sb.toString());
    }
}
