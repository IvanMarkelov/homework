import javax.tools.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class CodeInsertionProgram {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String path = ".\\lesson8\\src\\main\\java\\SomeClass.java";
        String pathToClass = path.substring(0, path.lastIndexOf('.') + 1) + "class";
        List<String> codeToInsert = readCode();

        try {
            CodeInsertionProgram.insertCodeIntoFile(path, codeToInsert, "SomeClass", "doWork");
        } catch (IOException e) {
            e.printStackTrace();
        }

        compileJava(path);

        Worker worker = newInstance(pathToClass);
        worker.doWork();
    }

    public static List<String> readCode() {
        Scanner sc = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        String line = "|";
        while (!line.equals("")) {
            line = sc.nextLine();
            if (!line.equals("")) {
                list.add(line);
            }
        }
        return list;
    }

    public static void insertCodeIntoFile(String path, List<String> codeToInsert, String className, String methodName) throws IOException {
        String someClassOpeningLine = "public class " + className + " implements Worker {\n\tpublic void " + methodName + "() {";
        String someClassClosingLine = "\t}\n}";

        List<String> lines = new ArrayList<>();
        lines.add(someClassOpeningLine);
        for (String l : codeToInsert) {
            lines.add("\t\t" + l);
        }
        lines.add(someClassClosingLine);

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void compileJava(String path) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, path);
    }

    public static Worker newInstance(String path) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> class_ = myClassLoader.loadMyClass(path);
        Constructor<?> constructor = class_.getConstructor();
        Object worker = constructor.newInstance();

        Method doWork = class_.getMethod("doWork");

        return new Worker() {
            @Override
            public void doWork() throws InvocationTargetException, IllegalAccessException {
                doWork.invoke(worker);
            }
        };

    }
}
