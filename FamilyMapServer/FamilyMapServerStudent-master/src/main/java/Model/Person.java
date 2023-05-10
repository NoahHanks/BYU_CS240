package Model;

import java.util.Objects;

/**
 * Class for the model of Family Map persons.
 */
public class Person {

    /**
     * String, not null, unique.
     */
    private String personID;

    /**
     * String, not null.
     */
    private String associatedUsername;

    /**
     * String, not null.
     */
    private String firstName;

    /**
     * String, not null.
     */
    private String lastName;

    /**
     * String, not null, "m" or "f".
     */
    private String gender;

    /**
     * String, optional.
     */
    private String fatherID;

    /**
     * String, optional.
     */
    private String motherID;

    /**
     * String, optional.
     */
    private String spouseID;

    /**
     * Creates a Person model with a person id, associated username, first name, last name, gender, father id, mother id, and spouse id.
     *
     * @param personID           String related to the ID of the person involved.
     * @param associatedUsername String of the username associated with the event.
     * @param firstName          String of the first name.
     * @param lastName           String of the last name.
     * @param gender             String of the gender.
     * @param fatherID           String of the father's ID.
     * @param motherID           String of the mother's ID.
     * @param spouseID           String of the spouse's ID.
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Creates a person with a Person object when the parents and spouse are null.
     *
     * @param personID           String related to the ID of the person involved.
     * @param associatedUsername String of the username associated with the event.
     * @param firstName          String of the first name.
     * @param lastName           String of the last name.
     * @param gender             String of the gender.
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * Overrides the equals() method.
     *
     * @param o Person Object
     * @return True if objects are equal. False if objects aren't equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) &&
                Objects.equals(associatedUsername, person.associatedUsername) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }
}
