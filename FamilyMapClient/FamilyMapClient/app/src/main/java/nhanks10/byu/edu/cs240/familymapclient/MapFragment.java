package nhanks10.byu.edu.cs240.familymapclient;

import static android.app.PendingIntent.getActivity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.familymapclient.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Model.Event;
import Model.Person;

public class MapFragment extends Fragment {

    private DataCache dataCache = DataCache.getInstance();
    private List eventList;
    private GoogleMap.OnMarkerClickListener eventMarkerClickListener;
    private GoogleMap map;
    private SharedPreferences preferences;
    private LinearLayout linearLayout;
    private final Set<Marker> markers = new HashSet<>();
    private final Set<String> lines = new HashSet<String>();
    Set<Model.Event> setOfDesiredEvents;

    Person eventPerson;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);
        setHasOptionsMenu(true);
        setGenderIcon(mapView);
        createMarkers(getMap());
        return mapView;
    }

    private void setGenderIcon(View mapView) {
        Resources resources = getActivity().getResources();
        int androidIconColor = ResourcesCompat.getColor(resources, R.color.green, null);
        int genderIconSize = resources.getDimensionPixelSize(R.dimen.gender_icon_size);
        Drawable genderIcon = AppCompatResources.getDrawable(getActivity(), R.color.purple_700);
        genderIcon = DrawableCompat.wrap(genderIcon);
        DrawableCompat.setTint(genderIcon.mutate(), androidIconColor);
        genderIcon.setBounds(0, 0, genderIconSize, genderIconSize);

        View genderImageView = mapView.findViewById(R.id.bottom_icon);
        genderImageView.setId(R.id.bottom_icon);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = view.findViewById(R.id.linear_layout);
        GoogleMap googleMap = null;
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            googleMap = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private GoogleMap getMap() {
        return map;
    }

    public void createMarkers(GoogleMap map) {
        for (int i = 0; i < eventList.size(); i++) {
            Event event = (Event) eventList.get(i);
            double lat = event.getLatitude();
            double lng = event.getLongitude();
            LatLng latLng = new LatLng(lat, lng);

            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.dimen.gender_icon_size);
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(event.getEventID())
                    .snippet(event.getEventType())
                    .icon(BitmapDescriptorFactory.fromBitmap(icon));

            Marker marker = map.addMarker(options);
            marker.setTag(event);

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Event event = (Event) marker.getTag();
                    Person person = getPersonFromEvent(event);
                    String personName = getPersonName(person);
                    String eventName = event.getEventID();

                    // Show info window
                    marker.showInfoWindow();

                    // Show event and person details
                    showDetails(eventName, personName);

                    // Draw event and family lines
                    drawLines(event, person);

                    return true;
                }
            });
        }
    }

    // Get person name from event
    private Person getPersonFromEvent(Event event) {
        String personId = event.getPersonID();
        for (Object person : eventList) {
            if (person.equals(personId)) {
                return (Person) person;
            }
        }
        return null;
    }

    private void showDetails(String eventName, String personName) {
        String details = "Event: " + eventName + "\n" + "Person: " + personName;
        Toast.makeText(getActivity(), details, Toast.LENGTH_SHORT).show();
    }

    private void drawLines(Event event, Person person) {
        // Draw event line
        PolylineOptions eventLine = new PolylineOptions()
                .add(new LatLng(event.getLatitude(), event.getLongitude()),
                        new LatLng(event.getLatitude(), event.getLongitude()))
                .color((int) getColor(event))
                .width(getResources().getDimension(R.dimen.line_width));

        int[] colors = DataCache.generateColor();
        if (colors[0] == (DataCache.getCounterColor())) {
            map.addPolyline(eventLine);
        }

        // Draw family lines
        List<Event> family = getFamilyEvents(person);
        for (Event relative : family) {
            relative.getPersonID();
            PolylineOptions familyLine = new PolylineOptions()
                    .add(new LatLng(event.getLatitude(), event.getLongitude()),
                            new LatLng(event.getLatitude(), event.getLongitude()))
                    .color((int) getColor(event))
                    .width(getResources().getDimension(R.dimen.line_width));
            map.addPolyline(familyLine);
        }
        drawParentLines(person, R.dimen.line_width);
        eventLines(event);
    }

    private List<Event> getFamilyEvents(Person person) {
        List<Event> family = new ArrayList<>();
        String fatherId = person.getFatherID();
        String motherId = person.getMotherID();
        String spouseId = person.getSpouseID();
        List<Event> peopleList = dataCache.getEventsByPersonID(person.getPersonID());
        for (Event relative : peopleList) {
            if (relative.getPersonID().equals(fatherId) ||
                    relative.getPersonID().equals(motherId) ||
                    relative.getPersonID().equals(spouseId)) {
                family.add(relative);
            }
        }
        return family;
    }

    public String getPersonName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    public String getEventDetails(Event event) {
        return String.format(Locale.getDefault(), "%s: %s, %s (%d)",
                event.getEventType().toUpperCase(),
                event.getCity(),
                event.getCountry(),
                event.getYear());
    }

    public void eventLines(Event event) {
        Map<String, Event> eventsMap = dataCache.getEvents();
        ArrayList<PolylineOptions> polyLineEvents = new ArrayList<>();

        // Get the event list for the person
        for (String person : eventsMap.keySet()) {
            if (person.equals(event.getPersonID())) {
                Event eventList = eventsMap.get(person);
                LatLng prevLatLng = null;
                for (Event e : eventList) {
                    if (prevLatLng != null) {
                        LatLng currLatLng = new LatLng(e.getLatitude(), e.getLongitude());
                        PolylineOptions options = new PolylineOptions()
                                .add(prevLatLng)
                                .add(currLatLng)
                                .color((int) getColor(event))
                                .width(10);
                        polyLineEvents.add(options);
                        String details = getEventDetails(event);
                        lines.add(details);
                        dataCache.storeEvents(eventsMap);
                    }
                    prevLatLng = new LatLng(e.getLatitude(), e.getLongitude());
                }
                break;
            }
        }

        dataCache.getSortedEvents();
    }

    public void drawParentLines(Person person, int lineWidth) {
        ArrayList<PolylineOptions> parentLines = new ArrayList<>();
        Map<String, Event> eventsMap = dataCache.getEvents();
        Event personEvents = eventsMap.get(person);

        LatLng startLatLng = new LatLng(personEvents.getLatitude(), personEvents.getLongitude());

        // Draw spouse line
        if (person.getSpouseID() != null) {
            Person spouse = (Person) dataCache.getPaternalEventIDs();
            if (spouse != null && eventsMap.get(spouse) != null && !eventsMap.get(spouse).equals(person)) {
                LatLng endLatLng = new LatLng(eventsMap.get(spouse).getLatitude(), eventsMap.get(spouse).getLongitude());
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(startLatLng, endLatLng)
                        .color(getActivity().getResources().getColor(R.color.pink))
                        .width(lineWidth);
                parentLines.add(polylineOptions);
            }
        }

        // Draw parent lines
        if (lineWidth > 2) {
            if (person.getFatherID() != null) {
                Person father = (Person) dataCache.getPaternalEventIDs();
                if (father != null && eventsMap.get(father) != null && !eventsMap.get(father).equals(person)) {
                    LatLng endLatLng = new LatLng(eventsMap.get(father).getLatitude(), eventsMap.get(father).getLongitude());
                    PolylineOptions newLine = new PolylineOptions()
                            .add(startLatLng, endLatLng)
                            .color(getActivity().getResources().getColor(R.color.purple_700))
                            .width(lineWidth);
                    parentLines.add(newLine);
                    drawParentLines(father, lineWidth - 2);
                }
            }

            // Draw mother line
            if (person.getMotherID() != null) {
                Person mother = (Person) dataCache.getMaternalEventIDs();
                if (mother != null && eventsMap.get(mother) != null && !eventsMap.get(mother).equals(person)) {
                    LatLng endLatLng = new LatLng(eventsMap.get(mother).getLatitude(), eventsMap.get(mother).getLongitude());
                    PolylineOptions newLine = new PolylineOptions()
                            .add(startLatLng, endLatLng)
                            .color(getActivity().getResources().getColor(R.color.purple_200))
                            .width(lineWidth);
                    parentLines.add(newLine);
                    drawParentLines(mother, lineWidth - 2);
                }
            }
        }
    }

    private static final List<String> EVENT_TYPES = Arrays.asList("Marriage", "Death", "Birth");

    public float getColor(Event e) {
        String eventType = e.getEventType().toLowerCase(Locale.ROOT);
        switch (eventType) {
            case "marriage":
                return Color.HSVToColor(new float[]{350f, 1f, 1f});
            case "death":
                return Color.HSVToColor(new float[]{282f, 1f, 1f});
            case "birth":
                return Color.HSVToColor(new float[]{180f, 1f, 1f});
            default:
                Map<Event, Float> colorMap = getRandomColor();
                for (Map.Entry<Event, Float> entry : colorMap.entrySet()) {
                    Event event = entry.getKey();
                    String eventTypeName = event.getEventType().toLowerCase(Locale.ROOT);
                    if (eventType.equals(eventTypeName)) {
                        return entry.getValue();
                    }
                }
                return Color.HSVToColor(new float[]{60f, 1f, 1f});
        }
    }

    public Map<Event, Float> getRandomColor() {
        Map<Event, Float> colorMap = new HashMap<>();
        Random random = new Random();
        float hue;
        for (Event event : dataCache.getSortedEvents()) {
            String eventType = event.getEventType().toLowerCase(Locale.ROOT);
            if (EVENT_TYPES.contains(eventType)) {
                continue;
            }
            do {
                hue = random.nextFloat() * 360;
            } while (colorMap.containsValue(hue));
            colorMap.put(event, hue);
        }
        return colorMap;
    }

}