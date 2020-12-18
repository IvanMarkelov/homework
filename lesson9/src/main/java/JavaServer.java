import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JavaServer implements Runnable {
    final static private int PORT = 8080;
    final static private String INDEX_PAGE = ".\\lesson9\\src\\main\\resources\\index.html";
    final static private String PAGE_404 = ".\\lesson9\\src\\main\\resources\\404.html";

    private Socket clientSocket;

    public JavaServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                JavaServer javaServer = new JavaServer(serverSocket.accept());
                Thread thread = new Thread(javaServer);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader in;
        PrintWriter out;
        BufferedOutputStream dataOut;
        try {
            in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            out = new PrintWriter(clientSocket.getOutputStream());
            dataOut = new BufferedOutputStream(clientSocket.getOutputStream());

            String clientInputLine = in.readLine();

            String method = clientInputLine.split(" ")[0];
            String requestedFileAddress = clientInputLine.split(" ")[1];

            String requestedFile = convertDirectoryPath(requestedFileAddress);
            List<File> files = getListOfDirectoryFiles(requestedFile);

            if (method.equals("GET") && files != null) {
                File file = new File(INDEX_PAGE);
                generateHtmlContent(files, file);
                int fileLength = (int) file.length();
                byte[] fileData = readFileData(file, fileLength);

                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server");
                out.println("Date: " + new Date());
                out.println("Content-type: text/html");
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();

            } else {
                File file = new File(PAGE_404);
                int fileLength = (int) file.length();

                byte[] fileData = readFileData(file, fileLength);

                out.println("HTTP/1.1 404 File Not Found");
                out.println("Server: Java HTTP Server");
                out.println("Date: " + new Date());
                out.println("Content-type: text/html");
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();

                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            }
            in.close();
            out.close();
            dataOut.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the String with the requested by HTTP file and converts it to java.io supported format.
     * @param requestedFilePath String with a filepath from HTTP request.
     * @return String with a filepath, compatible with java.io methods.
     */
    public static String convertDirectoryPath(String requestedFilePath) {
        if (requestedFilePath.equals("/")) {
            return ".";
        }
        StringBuilder sb = new StringBuilder(requestedFilePath);
        sb.insert(0, '.');

        return sb.toString().replace("/", "\\");
    }

    /**
     * Gets the list of files and directories in a folder.
     * @param directoryPath String with a relative path to a folder, the list of files and directories needs
     *                      to be created from.
     * @return ArrayList<File> with the list of files and directories, if the directoryPath exists
     * and is a path to directory, not a file. Returns Null otherwise.
     */
    public List<File> getListOfDirectoryFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.isFile() || !directory.exists()) {
            return null;
        }

        List<File> files = new ArrayList<>();
        File[] arrayOfFiles = directory.listFiles();
        if (arrayOfFiles != null) {
            for (File file : arrayOfFiles) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    files.add(new File(file.getAbsolutePath()));
                }
            }
        }
        return files;
    }

    /**
     * Converts file into a byte array.
     * @param file File to be converted into a byte array.
     * @param fileLength Length of the file.
     * @return Byte array with the content of file.
     * @throws IOException Throws IOException if the file is not found or there is a problem with IO.
     */
    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    /**
     * Generates content of HTML page using a list of files and directories in a folder. The result of the method is
     * an HTML page with a list of links to the files and directories in a folder.
     * @param list List of files and directories in a folder.
     * @param file Default HTML page in which the list of files and directories will be written.
     * @throws IOException Throws IOException if the file is not found or there is a problem with IO.
     */
    public void generateHtmlContent(List<File> list, File file) throws IOException {
        String firstLine = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>";
        String lastLine = "</body>\n" +
                "</html>";
        String hostName = "localhost:" + PORT;

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(firstLine);
        writer.newLine();

        for (File line : list) {
            StringBuilder sb = new StringBuilder();
            String canPath = line.getAbsolutePath();
            int index = canPath.indexOf("\\.\\");
            canPath = canPath.substring(index + 2);
            canPath = canPath.replace("\\", "/");

            sb.append("<h1><a href=\"http://")
                    .append(hostName)
                    .append(canPath)
                    .append("\">")
                    .append(canPath)
                    .append("</a></h1>");

            writer.write(sb.toString());
            writer.newLine();
        }
        writer.write(lastLine);
        writer.close();
    }
}
