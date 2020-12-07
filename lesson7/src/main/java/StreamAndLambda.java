import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class StreamAndLambda {
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

            fieldsToCleanup.forEach((x) -> {
                try {
                    if ((boolean) containsMapKey.invoke(objectClone, x)) {
                        removeByKey.invoke(objectClone, x);
                    } else {
                        throw new IllegalArgumentException("The object doesn't contain this field/key: " + x);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

            fieldsToOutput.forEach((x) -> {
                try {
                    if ((boolean) containsMapKey.invoke(objectClone, x)) {
                        Object fieldToAdd = getByKey.invoke(objectClone, x);
                        sb.append(fieldToAdd);
                        sb.append("\n");
                    } else {
                        throw new IllegalArgumentException("The object doesn't contain this field/key: " + x);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

        } else {
            Field[] declaredFields = class_.getDeclaredFields();
            List<String> fieldNames = Arrays.stream(declaredFields)
                    .map(Field::getName)
                    .collect(Collectors.toList());

            Stream.concat(fieldsToCleanup.stream(), fieldsToOutput.stream())
                    .distinct()
                    .forEach(x -> {
                        if (!fieldNames.contains(x)) {
                            throw new IllegalArgumentException("The object doesn't contain this field/key: " + x);
                        }
                    });

            Arrays.stream(declaredFields).forEach(f -> {
                try {
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
                        Object fieldToAdd = f.get(objectClone);
                        sb.append(fieldToAdd);
                        sb.append("\n");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        object = objectClone;
        System.out.println(sb.toString());
    }
}
