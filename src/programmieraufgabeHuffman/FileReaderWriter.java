package programmieraufgabeHuffman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderWriter {

    // * from geeksforgeeks.org * //
    public static String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get("src/programmieraufgabeHuffman/" + fileName)));
        return data;
    }

    // * code from baeldung.com *
    public static void writeFile(String fileName, String str)
        throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/programmieraufgabeHuffman/" + fileName));
        writer.write(str);

        writer.close();
    }

}
