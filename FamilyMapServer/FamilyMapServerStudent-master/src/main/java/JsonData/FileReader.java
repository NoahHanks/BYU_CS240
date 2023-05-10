package JsonData;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Reads the json files and converts them to readable strings.
 */
public class FileReader {

    /**
     * Reads through a file then takes the data and converts it to a string.
     *
     * @param jsonFile The file in the directory that should be read.
     * @return A string of data dependent on what the input file was.
     * @throws IOException An error when reading the json file.
     */
    public static String readFile(String jsonFile) throws IOException {
        StringBuilder fileData = new StringBuilder();
        try {
            File file = new File(jsonFile);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                fileData.append(scanner.next());
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return fileData.toString();
    }
}
