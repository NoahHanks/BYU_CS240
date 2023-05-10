package nhanks10.byu.edu.cs240.familymapclient.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.familymapclient.R;

import nhanks10.byu.edu.cs240.familymapclient.Login.LoginFragment;
import nhanks10.byu.edu.cs240.familymapclient.MapFragment;
import nhanks10.byu.edu.cs240.familymapclient.databinding.ActivityMainBinding;


// Main activity
public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfig;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private LoginFragment loginFragment;
    private MapFragment mapFragment;

    // Fragment manager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragmentFrameLayout);

        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            loginFragment.registerListener(this);

            fragmentManager.beginTransaction()
                    .add(R.id.fragmentFrameLayout, loginFragment)
                    .commit();
        } else {
            if (loginFragment instanceof LoginFragment) {
                loginFragment.registerListener(this);
            }
        }
    }

    // Called when the login fragment is done
    @Override
    public void notifyDone() {
        Fragment fragment = new MapFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentFrameLayout, fragment)
                .commit();
    }

}