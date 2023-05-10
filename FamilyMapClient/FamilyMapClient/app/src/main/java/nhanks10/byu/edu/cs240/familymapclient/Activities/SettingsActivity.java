package nhanks10.byu.edu.cs240.familymapclient.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familymapclient.R;

import nhanks10.byu.edu.cs240.familymapclient.DataCache;


public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch lifeStoryLines = findViewById(R.id.life_story_switch);
        Switch familyTreeLines = findViewById(R.id.family_tree_switch);
        Switch spouseLines = findViewById(R.id.spouse_switch);
        Switch fatherSide = findViewById(R.id.father_side_switch);
        Switch motherSide = findViewById(R.id.mothers_side_switch);
        Switch maleEvents = findViewById(R.id.male_events);
        Switch femaleEvents = findViewById(R.id.female_events);
        Button logoutButton = findViewById(R.id.logout_button);

        lifeStoryLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setLifeStoryLines(lifeStoryLines);
            }
        });

        familyTreeLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setFamilyTreeLines(familyTreeLines);
            }
        });

        spouseLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setSpouseLines(spouseLines);
            }
        });

        fatherSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setFatherSide(fatherSide);
            }
        });

        motherSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setMotherSide(motherSide);
            }
        });

        maleEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setMaleEvents(maleEvents);
            }
        });

        femaleEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCache.setFemaleEvents(femaleEvents);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }


}
