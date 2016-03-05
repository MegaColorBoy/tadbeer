package com.heba.tadbeer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.heba.tadbeer.Util.ApiCallback;
import com.heba.tadbeer.Util.ApiHelper;
import com.heba.tadbeer.Util.Tbr;
import com.heba.tadbeer.classes.User;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "TBR";
    LoginActivityFragment loginActivityFragment;
    RegisterFragment registerFragment;
    ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goto_login(null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    public void goto_login(View view){
        Log.d("TBR: ", "Login");
        FragmentManager fm = getSupportFragmentManager();
        this.loginActivityFragment = (this.loginActivityFragment == null) ? new LoginActivityFragment() : this.loginActivityFragment;
        fm.beginTransaction().replace(R.id.fragment_container, loginActivityFragment).commit();

    }
    public void goto_signup(View view){
        FragmentManager fm = getSupportFragmentManager();
        this.registerFragment = (this.registerFragment == null) ? new RegisterFragment() : this.registerFragment;
        fm.beginTransaction().replace(R.id.fragment_container, registerFragment).commit();
    }
    public void login (View view){
        //Setup API Helper
        apiHelper = ((Tbr)this.getApplication()).getApiHelper();
        String email = ((EditText)findViewById(R.id.text_view_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.text_view_password)).getText().toString();

        apiHelper.login(email, password, new ApiCallback() {
            @Override
            public void onSuccess(Boolean success, JSONObject data) {
                try {
                    JSONObject user = data.getJSONObject("User");
                    ((Tbr) getApplication()).setMe(new User(user.getInt("Id"), user.getString("Firstname"), user.getString("Lastname"), user.getString("Email")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
            }

            @Override
            public void onError(Boolean success, String error) {
                ((TextView)findViewById(R.id.text_view_error_text)).setText(error);
            }
        });
    }
    public void register (View view){

        String email = ((EditText)findViewById(R.id.text_view_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.text_view_signup_password)).getText().toString();
        String pwconfirm = ((EditText)findViewById(R.id.text_view_signup_confirm_password)).getText().toString();

        //check submission
        boolean valid = true;
        String error = "";
        if(email == null || email == "" || email.indexOf("@") < 0 /**REGEXP CHEcK **/){
            valid = false;
            error = getString(R.string.invalid_email_address);
        }
        else if(password == null || password.length() < 8){
            valid = false;
            error = getString(R.string.error_password_format);
        }

        ((TextView)findViewById(R.id.text_view_signup_error)).setText(error);

        if(valid){
            apiHelper = ((Tbr)this.getApplication()).getApiHelper();
            apiHelper.register(email, password, new ApiCallback() {
                @Override
                public void onSuccess(Boolean success, JSONObject data) {
                    Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(in);
                }

                @Override
                public void onError(Boolean success, String error) {
                    ((TextView)findViewById(R.id.text_view_signup_error)).setText(error);
                }
            });
        }
    }
}
