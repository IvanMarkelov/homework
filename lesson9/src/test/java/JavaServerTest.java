import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class JavaServerTest {

    @Test
    void listDirectoryFiles() {
        JavaServer newServer = new JavaServer(new Socket());
        List<File> list1 = newServer.getListOfDirectoryFiles(".");
        List<File> list2 = newServer.getListOfDirectoryFiles(".\\dodo\\pizza");
        List<File> list3 = newServer.getListOfDirectoryFiles(".\\pom.xml");

        assertEquals("pom.xml", list1.get(0).getName());
        assertNull(list2);
        assertNull(list3);
    }

    @Test
    public void convertDirectoryPath() {
        String getRes = "/lesson9/src/main/resources/404.html";
        String validRes = ".\\lesson9\\src\\main\\resources\\404.html";
        assertEquals(validRes, JavaServer.convertDirectoryPath(getRes));
        assertEquals(".\\java", JavaServer.convertDirectoryPath("/java"));
    }

    @Test
    public void generateHtmlPage() throws IOException {
        JavaServer newServer = new JavaServer(new Socket());
        List<File> list = newServer.getListOfDirectoryFiles(".");
        File file = new File(".\\src\\main\\resources\\test.html");
        newServer.generateHtmlContent(list, file);

        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        br.readLine();
        br.readLine();
        String firstLink = br.readLine();
        br.close();

        assertEquals("<h1><a href=\"http://localhost:8080/pom.xml\">/pom.xml</a></h1>", firstLink);
    }
}