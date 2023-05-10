package nhanks10.byu.edu.cs240.familymapclient;

import android.widget.Switch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import Model.Event;
import Model.Person;
import Model.User;
import Result.PersonResult;

public class DataCache {

    private static final DataCache instance = new DataCache();
    private Set<Event> setOfFilteredEvents;

    public static DataCache getInstance() {
        return instance;
    }

    private DataCache() {
    }

    public final PersonResult user = instance.getUser();
    private final User username = instance.getUsername();

    private final Map<String, Person> people = new HashMap<>();
    private final Map<String, Event> events = new HashMap<>();
    private final Map<String, List<Event>> personEvents = new HashMap<>();
    private final Set<String> paternalAncestors = new HashSet<>();
    private final Set<String> maternalAncestors = new HashSet<>();

    private final Set<String> malePersonIDs = new HashSet<>();
    private final Set<String> femalePersonIDs = new HashSet<>();
    private Set<String> filteredPersonIDs = new HashSet<String>();
    private final Set<String> maleEventIDs = new HashSet<>();
    private final Set<String> femaleEventIDs = new HashSet<>();
    private final Set<String> paternalEventIDs = new HashSet<>();
    private final Set<String> maternalEventIDs = new HashSet<>();
    private final Set<Event> filteredEvents = new HashSet<>();
    private static Float counter = 90f;

    private static boolean lifeStorylines = true;
    private static boolean familyTreeLines = true;
    private static boolean spouseLines = true;
    private static boolean fatherSide = true;
    private static boolean motherSide = true;
    private static boolean maleEvents = true;
    private static boolean femaleEvents = true;


    public void storePeople(Map<String, Person> people) {
        for (Person person : people.values()) {
            this.people.put(person.getPersonID(), person);
        }
    }

    public void storeEvents(Map<String, Event> events) {
        for (Map.Entry<String, Event> entry : events.entrySet()) {
            this.events.put(entry.getKey(), entry.getValue());
        }

    }

    public void buildFamilyTree() {
        buildGenderSets();
        buildEventSets();
        buildAncestorSets();
        buildPersonEvents();
    }

    private void buildGenderSets() {
        for (Person person : people.values()) {
            if (person.getGender().equals("m")) {
                malePersonIDs.add(person.getPersonID());
            } else {
                femalePersonIDs.add(person.getPersonID());
            }
        }
        for (Event event : events.values()) {
            if (malePersonIDs.contains(event.getPersonID())) {
                maleEventIDs.add(event.getEventID());
            } else {
                femaleEventIDs.add(event.getEventID());
            }
        }
    }

    private void buildAncestorSets() {
        recurseParents(String.valueOf(user.getPersons()), paternalAncestors);
        recurseParents(String.valueOf(user.getPersons()), maternalAncestors);
        for (Event event : events.values()) {
            if (paternalAncestors.contains(event.getPersonID()) && fatherSide && spouseLines) {
                paternalEventIDs.add(event.getEventID());
            } else if (maternalAncestors.contains(event.getPersonID()) && motherSide && spouseLines) {
                maternalEventIDs.add(event.getEventID());
            }
        }
    }

    private void buildEventSets() {
        filteredEvents.clear();
        if (!filteredPersonIDs.contains(user.getPersons()) && lifeStorylines && familyTreeLines) {
            filteredPersonIDs.add(String.valueOf(user.getPersons()));
        }
        for (String personID : filteredPersonIDs) {
            filteredEvents.addAll(Objects.requireNonNull(personEvents.get(personID)));
        }
    }

    private void buildPersonEvents() {
        personEvents.clear();
        for (Person person : people.values()) {
            List<Event> thisPersonEvents = new ArrayList<>();
            for (Event event : events.values()) {
                if (event.getPersonID().equals(person.getPersonID()) && maleEvents && femaleEvents) {
                    thisPersonEvents.add(event);
                }
            }
            thisPersonEvents.sort(new EventComparator());
            personEvents.put(person.getPersonID(), thisPersonEvents);
        }
    }

    private void recurseParents(String personID, Set<String> familySideSet) {
        Class<?> person = familySideSet.getClass();
        if (person.getName() == null) {
            familySideSet.add(personID);
        } else {
            familySideSet.add(personID);
            recurseParents(person.getName(), familySideSet);
            recurseParents(person.getName(), familySideSet);
        }
    }


    public void setFilteredPersonIDs(Set<String> filteredPersonIDs) {
        this.filteredPersonIDs = filteredPersonIDs;
    }

    public Set<String> getFilteredPersonIDs() {
        return filteredPersonIDs;
    }

    public void setSetOfFilteredEvents(Set<Event> setOfFilteredEvents) {
        this.setOfFilteredEvents = setOfFilteredEvents;
    }

    public Set<Model.Event> getSetOfFilteredEvents() {
        return setOfFilteredEvents;
    }


    public List<Model.Event> getSortedEvents() {
        List<Model.Event> sortedEvents = new ArrayList<>();
        for (Model.Event event : events.values()) {
            sortedEvents.add(event);
        }

        sortedEvents.sort(new EventComparator());
        return sortedEvents;
    }


    public Person getPersonByID(String personID) {
        return people.get(personID);
    }


    public Model.Event getEventByID(String eventID) {
        return events.get(eventID);
    }


    public List<Model.Event> getEventsByPersonID(String personID) {
        return personEvents.get(personID);
    }


    public PersonResult getUser() {
        return user;
    }

    private User getUsername() { return username; }


    public static int[] generateColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int blue = random.nextInt(256);
        int green = random.nextInt(256);
        int[] colors;
        colors = new int[]{red, green, blue};
        return colors;
    }


    public static Float getCounterColor() {
        if (counter == 360f) {
            counter = 90f;
        }

        counter += 30f;
        return counter;
    }

    public String getCurrentPersonID() {
        return username.getPersonID();
    }


    private class EventComparator implements Comparator<Model.Event> {
        @Override
        public int compare(Model.Event o1, Model.Event o2) {
            return o1.getYear() - o2.getYear();
        }
    }

    public Map<String, Person> getPersons() {
        return people;
    }

    public Map<String, Model.Event> getEvents() {
        return events;
    }

    public Map<String, List<Model.Event>> getPersonEvents() {
        return personEvents;
    }

    public Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public Set<String> getMalePersonIDs() {
        return malePersonIDs;
    }

    public Set<String> getFemalePersonIDs() {
        return femalePersonIDs;
    }

    public Set<String> getMaleEventIDs() {
        return maleEventIDs;
    }

    public Set<String> getFemaleEventIDs() {
        return femaleEventIDs;
    }

    public Set<String> getPaternalEventIDs() {
        return paternalEventIDs;
    }

    public Set<String> getMaternalEventIDs() {
        return maternalEventIDs;
    }

    public static void setLifeStoryLines(Switch s){
        lifeStorylines = Boolean.valueOf(String.valueOf(s));
    }

    public static void setFamilyTreeLines(Switch s){
        familyTreeLines = Boolean.valueOf(String.valueOf(s));
    }

    public static void setSpouseLines(Switch s){
        spouseLines = Boolean.valueOf(String.valueOf(s));
    }

    public static void setFatherSide(Switch s){
        fatherSide = Boolean.valueOf(String.valueOf(s));
    }

    public static void setMotherSide(Switch s){
        motherSide = Boolean.valueOf(String.valueOf(s));
    }

    public static void setMaleEvents(Switch s){
        maleEvents = Boolean.valueOf(String.valueOf(s));
    }

    public static void setFemaleEvents(Switch s){
        femaleEvents = Boolean.valueOf(String.valueOf(s));
    }


}