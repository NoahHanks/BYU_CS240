package nhanks10.byu.edu.cs240.familymapclient.Activities;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familymapclient.R;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Event;
import Model.Person;
import nhanks10.byu.edu.cs240.familymapclient.DataCache;

public class PersonActivity extends AppCompatActivity {

    public static final String MY_PERSON_KEY = "MyPersonKey";
    DataCache dataCache = DataCache.getInstance();
    List<Event> events;
    ExpandableListView expandableListView;
    List<Person> personsList;
    List<Event> eventsList;
    Person currPersonFamily;
    Person selectedPerson;
    Map<String, Person> person;
    Map<String, Person> event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        getSupportActionBar().setTitle("Person Information");

        TextView firstNameTextView = findViewById(R.id.firstName);
        TextView lastNameTextView = findViewById(R.id.lastName);
        TextView genderTextView = findViewById(R.id.gender);

        Person currPerson = dataCache.getPersonByID(dataCache.getCurrentPersonID());
        firstNameTextView.setText(currPerson.getFirstName());
        lastNameTextView.setText(currPerson.getLastName());
        genderTextView.setText(currPerson.getGender().toUpperCase());

        // Get the person id from the intent
        events = new ArrayList<>();
        Map<String, Event> eventsList = dataCache.getEvents();
        for (Event event : eventsList.values()) {
            if (event.equals(currPerson.getPersonID())) {
                events.add((Event) event);
            }
        }

        ArrayList<Object> people = new ArrayList<>();
        Map<String, Person> peopleList = dataCache.getPersons();
        for (Person person : peopleList.values()) {
            if (person.equals(currPerson.getFatherID())
                    || person.equals(currPerson.getMotherID())) {
                people.add(person);
                dataCache.storePeople(peopleList);
            }
        }

        expandableListView = findViewById(R.id.expandableListView);
    }

    public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
        private List<Event> events;
        private List<Person> persons;

        public CustomExpandableListAdapter(List<Event> events, List<Person> persons) {
            this.events = events;
            this.persons = persons;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (groupPosition == 0) {
                return events.size();
            } else if (groupPosition == 1) {
                return persons.size();
            } else {
                throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            if (groupPosition == 0) {
                return "Events";
            } else if (groupPosition == 1) {
                return "Persons";
            } else {
                return null;
            }
        }

        // This method is called to get the child (the data) at the specified position
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            if (groupPosition == 0) {
                return events.get(childPosition);
            } else if (groupPosition == 1) {
                return persons.get(childPosition);
            } else {
                return null;
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        // This method is called to get the view for the group (the header)
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setText(getGroup(groupPosition).toString());
            textView.setTextSize(20);
            textView.setPadding(100, 50, 0, 50);
            return textView;
        }

        // This method is called to get the view for the child (the data)
        @SuppressLint("ResourceType")
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.id.expandableListView, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = view.findViewById(R.string.child);
            if (groupPosition == 0) {
                Event event = events.get(childPosition);
                textView.setText(event.getEventType());
            } else if (groupPosition == 1) {
                Person person = persons.get(childPosition);
                textView.setText(person.getFirstName());
            }
            return view;
        }
    }

    public void initializeEventView(View view, final int position) {
        // Get the views
        TextView eventDetailsTextView = view.findViewById(R.id.event_details);
        TextView associatedPersonTextView = view.findViewById(R.id.firstName);

        // Get the event and associated person
        Event event = eventsList.get(position);
        Person associatedPerson = getAssociatedPerson(event);

        // Set the event details text
        String eventDetailsText = event.getEventType() + ": " + event.getCity() + ", " +
                event.getCountry() + " (" + event.getYear() + ")";
        eventDetailsTextView.setText(eventDetailsText);

        // Set the associated person text
        String associatedPersonText = associatedPerson.getFirstName() + " " + associatedPerson.getLastName();
        associatedPersonTextView.setText(associatedPersonText);

        // Set the onClickListener to open EventActivity with the current event
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EventActivity.class);
                intent.putExtra(EventActivity.MY_EVENT_KEY, event.getEventID());
                startActivity(intent);
            }
        });
    }

    // Returns the person associated with the given event
    private Person getAssociatedPerson(Event event) {
        for (Person person : personsList) {
            if (person.getPersonID().equals(event.getEventID())) {
                return person;
            }
        }
        return null;
    }


    public void initializePersonView(View personItemView, final int childPosition) {

        Person associatedPerson = currPersonFamily;
        if (associatedPerson == null) {
            return;
        }

        ImageView personGender = personItemView.findViewById(R.id.gender);
        @SuppressLint("ResourceType") Drawable genderIcon = associatedPerson.getGender().equals("m")
                ? getResources().getDrawable(R.string.male)
                : getResources().getDrawable(R.string.female);
        personGender.setImageDrawable(genderIcon);

        TextView personName = personItemView.findViewById(R.id.firstName);
        personName.setText(associatedPerson.getFirstName() + " " + associatedPerson.getLastName());

        // Set the relationship text
        TextView personRelationship = personItemView.findViewById(R.id.expandableListView);
        String relationship = "";
        switch (childPosition) {
            case 0:
                relationship = getString(R.string.father);
                break;
            case 1:
                relationship = getString(R.string.mother);
                break;
            case 2:
                relationship = getString(R.string.spouse);
                break;
            default:
                relationship = getString(R.string.child);
                break;
        }
        personRelationship.setText(relationship);

        personItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPerson = currPersonFamily;
            }
        });
    }















}
