package com.example.familymapclient;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import android.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Model.Event;
import Model.Person;
import nhanks10.byu.edu.cs240.familymapclient.DataCache;

public class DataCacheTest {

    @Test
    public void relatedPass() {
        DataCache dataCache = DataCache.getInstance();
        DataCache.getInstance();
        Person user = new Person("noah_hanks", "nhanks10", "Noah",
                "Hanks", "m", "dennis_hanks", "yunme_hanks", "marissa_bradley");
        Person spouse = new Person("marissa_bradley", "nhanks10", "Marissa",
                "Bradley", "f", null, null, "noah_hanks");
        Person  child = new Person("mini_hanks", "nhanks10", "Mini",
                "Hanks", "f", "noah_hanks", "marissa_bradley", null);
        Person father = new Person("dennis_hanks", "nhanks10", "Dennis",
                "Hanks", "m", null, null, "yunme_hanks");
        Person mother = new Person("yunme_hanks", "nhanks10", "Yunme",
                "Hanks", "f", null, null, "dennis_hanks");

        Person[] related = new Person[5];

        related[0] = user;
        related[1] = spouse;
        related[2] = mother;
        related[3] = father;
        related[4] = child;
        List<Pair<String, String>> expected = new ArrayList<>();
        expected.add(new Pair<>(mother.getPersonID(), "Mother"));
        expected.add(new Pair<>(father.getPersonID(), "Father"));
        expected.add(new Pair<>(spouse.getPersonID(), "Spouse"));
        expected.add(new Pair<>(child.getPersonID(), "Daughter"));

        List<Pair<String, String>> relationships = (List<Pair<String, String>>) dataCache.getPersonByID("noah_hanks");
        for (int i = 0; i <= 3; i++) {
            assertEquals(expected.get(i).first, relationships.get(i).first);
            assertEquals(expected.get(i).second, relationships.get(i).second);
        }
    }

    @Test
    public void relatedFail() {
        DataCache dataCache = DataCache.getInstance();
        Person[] related = new Person[2];
        Person unrelated1 = new Person("noah_hanks", "nhanks10", "Noah",
                "Hanks", "m", null, null, null);
        Person unrelated2 = new Person("marissa_bradley", "nhanks10", "Marissa",
                "Bradley", "f", null, null, null);
        related[0] = unrelated1;
        related[1] = unrelated2;

        List<Pair<String, String>> relationships = (List<Pair<String, String>>) dataCache.getPersonByID("noah_hanks");
        assertEquals(0, relationships.size());
    }

    public static class FilteringEventsTest {
        DataCache dataCache = DataCache.getInstance();
        Person[] peopleArray = new Person[5];
        Event[] eventArray = new Event[5];
        Person user = new Person("mini_hanks", "nhanks10", "Mini",
                "Hanks", "f", "noah_hanks", "marissa_bradley", null);
        Person mother = new Person("marissa_bradley", "nhanks10", "Marissa",
                "Bradley", "f", null, null, "noah_hanks");
        Person father = new Person("noah_hanks", "nhanks10", "Noah",
                "Hanks", "m", "dennis_hanks", "yunme_hanks", "marissa_bradley");
        Person grandMother = new Person("yunme_hanks", "nhanks10", "Yunme",
                "Hanks", "f", null, null, "dennis_hanks");
        Person grandFather = new Person("dennis_hanks", "nhanks10", "Dennis",
                "Hanks", "m", null, null, "yunme_hanks");

        Event noahBirth = new Event("birth_Noah", "nhanks10",
                "noah_hanks", 1, 1, "US", "Diamond Bar",
                "birth", 1999);
        Event dennisBirth = new Event("birth_Dennis", "nhanks10",
                "dennis_hanks", 300, 300, "US", "Arcadia",
                "birth", 1972);
        Event yunmeBirth = new Event("birth_Yunme", "nhanks10",
                "yunme_hanks", 9999, 9999, "Korea", "Seoul",
                "birth", 1969);
        Event miniBirth = new Event("birth_Mini", "nhanks10", "mini_hanks",
                50, 50, "US", "Salt Lake City", "birth", 2022);
        Event marissaBirth = new Event("birth_marissa", "nhanks10", "marissa_bradley",
                500, 500, "US", "Provo", "birth", 1997);

        @Before
        public void init() {
            dataCache.buildFamilyTree();

            peopleArray[0] = user;
            peopleArray[1] = mother;
            peopleArray[2] = father;
            peopleArray[3] = grandMother;
            peopleArray[4] = grandFather;

            eventArray[0] = miniBirth;
            eventArray[1] = noahBirth;
            eventArray[2] = marissaBirth;
            eventArray[3] = yunmeBirth;
            eventArray[4] = dennisBirth;

        }

        @Test
        public void filterOutMalesPass() {
            dataCache.getPaternalAncestors().add("Female");
            Set<Event> actual = dataCache.getSetOfFilteredEvents();
            Set<Event> expected = new HashSet<>();
            dataCache.setSetOfFilteredEvents(expected);
            expected.add(noahBirth);
            expected.add(dennisBirth);
            assertEquals(expected, actual);
        }

        @Test
        public void filterOutFemalesPass() {
            dataCache.getPaternalAncestors().add("Male");
            Set<Event> actual = dataCache.getSetOfFilteredEvents();
            Set<Event> expected = new HashSet<>();
            dataCache.setSetOfFilteredEvents(expected);
            expected.add(miniBirth);
            expected.add(marissaBirth);
            expected.add(yunmeBirth);
            assertEquals(expected, actual);
        }

        @Test
        public void filterOutMaternalPass() {
            dataCache.getPaternalAncestors().add("Mothers");
            Set<Event> actual = dataCache.getSetOfFilteredEvents();
            Set<Event> expected = new HashSet<>();
            dataCache.setSetOfFilteredEvents(expected);
            expected.add(miniBirth);
            expected.add(dennisBirth);
            expected.add(yunmeBirth);
            expected.add(noahBirth);
            assertEquals(expected, actual);

        }

        @Test
        public void filterOutPaternalPass() {
            dataCache.getPaternalAncestors().add("Fathers");
            Set<Event> actual = dataCache.getSetOfFilteredEvents();
            Set<Event> expected = new HashSet<>();
            dataCache.setSetOfFilteredEvents(expected);
            expected.add(marissaBirth);
            expected.add(miniBirth);
            assertEquals(expected, actual);
        }
    }

    public static class SearchTest {
        Person sheila = new Person("sheila", "sheila", "Sheila",
                "Parker", "f", null, null, null);
        Event sheilaBirth = new Event("Sheila_Birth", "sheila",
                "sheila", -36, 144, "Australia", "Melbourne",
                "birth", 1970);
        List<Event> eventList = new ArrayList<>();
        Person[] peopleList = new Person[1];
        Set<Event> expectedEvent;
        Set<Person> expectedPeople;

        @Before
        public void init() {
            eventList.add(sheilaBirth);
            peopleList[0] = sheila;
            expectedEvent = new HashSet<>();
            expectedPeople = new HashSet<>();
        }

        @Test
        public void searchFoundPass() {
            expectedEvent.add(sheilaBirth);
            expectedPeople.add(sheila);
            Set<Event> actualEvents = DataCache.getInstance().getSetOfFilteredEvents();
            assertEquals(expectedEvent, actualEvents);
            Set<String> actualPeople = DataCache.getInstance().getFilteredPersonIDs();
            assertEquals(expectedPeople, actualPeople);
        }

        @Test
        public void searchNothingFoundPass() {
            Set<Event> actual = DataCache.getInstance().getSetOfFilteredEvents();
            assertEquals(expectedEvent, actual);
            Set<String> actualPeople = DataCache.getInstance().getFilteredPersonIDs();
            assertEquals(expectedPeople, actualPeople);
        }

    }

    public static class SortByDateTest {
        Event noahBirth = new Event("birth_Noah", "nhanks10",
                "noah_hanks", 1, 1, "US", "Diamond Bar",
                "birth", 1999);
        Event dennisBirth = new Event("birth_Dennis", "nhanks10",
                "dennis_hanks", 300, 300, "US", "Arcadia",
                "birth", 1972);
        Event yunmeBirth = new Event("birth_Yunme", "nhanks10",
                "yunme_hanks", 9999, 9999, "Korea", "Seoul",
                "birth", 1969);
        Event[] eventArray = new Event[3];
        Event[] expectedArray = new Event[3];

        @Before
        public void init() {
            expectedArray[0] = yunmeBirth;
            expectedArray[1] = dennisBirth;
            expectedArray[2] = noahBirth;
        }

        @Test
        public void unorderedPass() {
            eventArray[0] = noahBirth;
            eventArray[1] = dennisBirth;
            eventArray[2] = yunmeBirth;
            Event[] actual = DataCache.getInstance().getSortedEvents().toArray(eventArray);
            assertArrayEquals(expectedArray, actual);
        }

        @Test
        public void orderedPass() {
            eventArray[0] = yunmeBirth;
            eventArray[1] = dennisBirth;
            eventArray[2] = noahBirth;
            Event[] actual = DataCache.getInstance().getSortedEvents().toArray(eventArray);
            assertArrayEquals(expectedArray, actual);
        }
    }
}
