package nhanks10.byu.edu.cs240.familymapclient.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import Model.Event;
import Model.Person;
import nhanks10.byu.edu.cs240.familymapclient.DataCache;

public class SearchActivity extends AppCompatActivity {

    private static final int PERSON_VIEW_TYPE = 1;
    DataCache cache = DataCache.getInstance();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Event> events = new ArrayList<>();
        List<Person> people = new ArrayList<>();
        searchView = findViewById(R.id.search_text);

        Map<String, Event> eventMap = cache.getEvents();
        if (eventMap != null) {
            events.addAll((Collection<? extends Event>) eventMap);
        }
        Map<String, Person> personMap = cache.getPersons();
        if (personMap != null) {
            people.addAll((Collection<? extends Person>) personMap);
        }

        SearchAdapter adapter = new SearchAdapter(people, events);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

        private static final int PERSON_VIEW_TYPE = 1;
        private static final int EVENT_VIEW_TYPE = 2;

        private List<Event> events;
        private List<Person> persons;

        public SearchAdapter(List<Person> persons, List<Event> events) {
            this.events = events;
            this.persons = persons;
        }

        @Override
        public int getItemViewType(int position) {
            return position < persons.size() ? PERSON_VIEW_TYPE : EVENT_VIEW_TYPE;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view;
            if (viewType == PERSON_VIEW_TYPE) {
                view = inflater.inflate(R.layout.person_item, parent, false);
            } else {
                view = inflater.inflate(R.layout.event_item, parent, false);
            }
            return new SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if (position < persons.size()) {
                holder.bindPerson(persons.get(position));
            } else {
                holder.bindEvent(events.get(position - persons.size()));
            }
        }

        @Override
        public int getItemCount() {
            return (events.size() + persons.size());
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder {
            private TextView eventText;
            private TextView eventLocation;
            private TextView eventDetails;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                eventText = itemView.findViewById(R.id.event_text);
                eventLocation = itemView.findViewById(R.id.event_item_type_and_location);
                eventDetails = itemView.findViewById(R.id.event_details);
            }

            public void bindPerson(Person person) {
                eventText.setText(person.getFirstName() + " " + person.getLastName());

            }

            public void bindEvent(Event event) {
                eventText.setText(event.getEventID());
                eventLocation.setText(event.getCity() + ", " + event.getCountry());
                eventDetails.setText(event.getYear());
            }
        }
    }


    public class ItemClickListener implements View.OnClickListener {

        private int viewType;
        private Person person;
        private Model.Event event;

        public ItemClickListener(int viewType, Person person, Model.Event event) {
            this.viewType = viewType;
            this.person = person;
            this.event = event;
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            if (viewType == PERSON_VIEW_TYPE) {
                intent = new Intent(view.getContext(), PersonActivity.class);
                intent.putExtra(PersonActivity.MY_PERSON_KEY, person.getPersonID());
            } else {
                intent = new Intent(view.getContext(), EventActivity.class);
                intent.putExtra(EventActivity.MY_EVENT_KEY, event.getEventID());
            }
            view.getContext().startActivity(intent);
        }
    }






}
