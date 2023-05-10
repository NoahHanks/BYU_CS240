package nhanks10.byu.edu.cs240.familymapclient.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.familymapclient.R;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Request.LoginRequest;
import Request.RegisterRequest;
import nhanks10.byu.edu.cs240.familymapclient.Activities.MainActivity;

public class LoginFragment extends Fragment {

    private static final String LOG_TAG = "LoginFragment";
    private static final String LOGIN_SUCCESS_KEY = "loginSuccess";
    private static final String AUTHTOKEN_KEY = "authToken";
    private static final String PERSON_ID_KEY = "personID";
    private static final String DATA_SUCCESS_KEY = "dataSuccess";
    private static final String REGISTER_SUCCESS_KEY = "registerSuccess";
    
    private final String UNSUCCESSFUL_LOGIN = "Unsuccessful login.";
    private final String UNSUCCESSFUL_REGISTER = "Unsuccessful register.";
    private FragmentFirstBinding binding;
    private EditText editHost;
    private EditText editPort;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editEmail;
    private RadioButton male;
    private Button loginButton;
    private Button registerButton;
    private View genderSelected;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String host;
    private String port;
    private boolean successMessage;
    private String userFullName;

    protected ExecutorService executorService;
    private Listener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreate(bundle");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "in onCreateView(bundle");
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        editHost = view.findViewById(R.id.server_IP_address);
        editPort = view.findViewById(R.id.server_port_number);
        editUsername = view.findViewById(R.id.username);
        editPassword = view.findViewById(R.id.password);
        editFirstName = view.findViewById(R.id.first_name);
        editLastName = view.findViewById(R.id.last_name);
        editEmail = view.findViewById(R.id.email);
        loginButton = view.findViewById(R.id.login_button);
        registerButton = view.findViewById(R.id.register_button);
        male = view.findViewById(R.id.radioGender1);

        editHost.addTextChangedListener(textWatcher);
        editPort.addTextChangedListener(textWatcher);
        editUsername.addTextChangedListener(textWatcher);
        editPassword.addTextChangedListener(textWatcher);
        editFirstName.addTextChangedListener(textWatcher);
        editLastName.addTextChangedListener(textWatcher);
        editEmail.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener((v1) -> {
            @SuppressLint("HandlerLeak") Handler loginHandler = new Handler() {
                @Override
                public void handleMessage(Message message) {
                    Bundle bundle = message.getData();
                    Boolean success = bundle.getBoolean(LOGIN_SUCCESS_KEY, false);
                    if (success) {
                        String authToken = bundle.getString(AUTHTOKEN_KEY);
                        String personID = bundle.getString(PERSON_ID_KEY);
                        userFullName = bundle.getString("fullName");

                        startRetrievalTask(authToken, personID);
                        listener.notifyDone();
                        Toast.makeText(getActivity(), userFullName, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), UNSUCCESSFUL_LOGIN, Toast.LENGTH_LONG).show();
                    }
                }
            };
            LoginRequest request = new LoginRequest(username
                    , password);
            LoginTask task = new LoginTask(loginHandler, request, host
                    , port);
            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(task);
        });

        registerButton.setOnClickListener((v2) -> {
            @SuppressLint("HandlerLeak") Handler registerHandler = new Handler() {
                @Override
                public void handleMessage(Message message) {
                    Bundle bundle = message.getData();
                    Boolean success =  bundle.getBoolean(REGISTER_SUCCESS_KEY, false);
                    if(success) {
                        String authToken = bundle.getString(AUTHTOKEN_KEY);
                        String personID = bundle.getString(PERSON_ID_KEY);
                        startRetrievalTask(authToken,personID);

                        listener.notifyDone();
                    } else {
                        Toast.makeText(getContext(), UNSUCCESSFUL_REGISTER, Toast.LENGTH_LONG).show();
                    }
                }
            };
            RadioButton radioButton;
            RadioGroup genderGroup = null;
            int id = genderGroup.getCheckedRadioButtonId();
            radioButton = view.findViewById(id);
            String gender = radioButton.getText().toString();
            Character genderChar = gender.charAt(0);

            RegisterRequest registerRequest = new RegisterRequest(username
                    , password, email
                    , firstName, lastName
                    , genderChar.toString().toLowerCase(Locale.ROOT));
            RegisterTask registerTask = new RegisterTask(registerHandler,registerRequest
                    , host, port);
            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(registerTask);
        });

        return view;
    }


    private void startRetrievalTask(String authToken, String personID) {
        @SuppressLint("HandlerLeak") Handler retrieveDataHandler = new Handler() {
            @Override
            public void handleMessage(Message message){
                Bundle bundle = message.getData();
                Boolean success = bundle.getBoolean(DATA_SUCCESS_KEY, false);
                if (success) {
                    listener.notifyDone();
                } else {
                    Toast.makeText(getContext(),"Failed to download data", Toast.LENGTH_LONG).show();
                }
            }
        };
        RetrievalTask retrievalTask = new RetrievalTask(retrieveDataHandler
                , host, port
                , authToken, personID);
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(retrievalTask);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            host = editHost.getText().toString().trim();
            port = editPort.getText().toString().trim();
            username = editUsername.getText().toString().trim();
            password = editPassword.getText().toString().trim();
            firstName = editFirstName.getText().toString().trim();
            lastName = editLastName.getText().toString().trim();
            email = editEmail.getText().toString().trim();

            loginButton.setEnabled(!host.isEmpty() && !port.isEmpty() && !username.isEmpty() && !password.isEmpty());
            registerButton.setEnabled(!host.isEmpty() && !port.isEmpty()
                    && !username.isEmpty() && !password.isEmpty() && !firstName.isEmpty()
                    && !lastName.isEmpty() && !email.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    void checkFieldsForEmptyValues(){
        String hostField = host;
        String portField = port;
        String usernameField = username;
        String passwordField = password;
        String firstNameField = firstName;
        String lastNameField = lastName;
        String emailAddressField = email;

        if(hostField.equals("") || portField.equals("") || usernameField.equals("") || passwordField.equals("")){
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
        } else if (firstNameField.equals("") || lastNameField.equals("") || emailAddressField.equals("")){
            loginButton.setEnabled(true);
            registerButton.setEnabled(false);
        }
        else {
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);
        }
    }


    public void registerListener(MainActivity mainActivity) {
        this.listener = listener;
    }

    public interface Listener {
        void notifyDone();
    }
}
