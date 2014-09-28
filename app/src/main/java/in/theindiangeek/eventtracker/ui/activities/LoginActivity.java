package in.theindiangeek.eventtracker.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.theindiangeek.eventtracker.R;

public class LoginActivity extends Activity {

    EditText mLoginName;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginName = (EditText) findViewById(R.id.login_name_edittext);
        mLoginButton = (Button) findViewById(R.id.login_name_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userName = mLoginName.getText().toString().trim();

                if (userName.isEmpty()) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("User Name cannot be blank")
                            .setMessage("No seriously, you cannot have a blank user name")
                            .setCancelable(true)
                            .setNeutralButton("Okay", null)
                            .create()
                            .show();
                } else {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name", userName.toLowerCase()).commit();

                    Intent intent = new Intent(getBaseContext(), EventListingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
    }
}
