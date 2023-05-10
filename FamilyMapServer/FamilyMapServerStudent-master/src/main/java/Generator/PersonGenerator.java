package Generator;

import JsonData.FileReader;
import JsonData.NameData;
import Model.Person;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Generates a person for the family tree.
 */
public class PersonGenerator {

    /**
     * Generates a person for a family tree when given a few parameters. The rest of the
     * info is randomly chosen from the json files.
     *
     * @param associatedUsername String of the user's username.
     * @param gender             String gender
     * @param lastName           String last name unless female then given a random last name.
     * @return random Person with first and last name.
     * @throws IOException Error reading the json files.
     */
    public static Person generatePerson(String associatedUsername, String gender, String lastName) throws IOException {
        try {
            String filePathFirstNames;
            if (gender == "m") filePathFirstNames = "json/mnames.json";
            else if (gender == "f") filePathFirstNames = "json/fnames.json";
            else throw new IOException("Error: Invalid gender.");

            String firstNames = FileReader.readFile(filePathFirstNames);
            String lastNames = FileReader.readFile("json/snames.json");

            NameData firstNameData = new Gson().fromJson(firstNames, NameData.class);
            NameData lastNameData = new Gson().fromJson(lastNames, NameData.class);
            int randomFirst = new Random().nextInt(firstNameData.getData().length);
            int randomLast = new Random().nextInt(lastNameData.getData().length);
            String firstName = firstNameData.getData()[randomFirst];
            if (lastName == null) lastName = lastNameData.getData()[randomLast];

            return new Person(UUID.randomUUID().toString(), associatedUsername, firstName, lastName, gender);
        } catch (IOException e) {
            throw new IOException("Error reading through the json files.");
        }
    }

}
