package nhanks10.byu.edu.cs240.familymapclient.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.familymapclient.R;

import nhanks10.byu.edu.cs240.familymapclient.MapFragment;

public class EventActivity extends AppCompatActivity {

    public static final String MY_EVENT_KEY = "MyEventKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Get the event id from the intent
        Intent intent = getIntent();
        String eventId = intent.getStringExtra(MY_EVENT_KEY);
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);

        // Create the fragment
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = new MapFragment();
        fragment.setArguments(bundle);

        // Add the fragment to the activity
        manager.beginTransaction()
                .add(R.id.fragmentFrameLayout, fragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // This method is called when the user presses the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
