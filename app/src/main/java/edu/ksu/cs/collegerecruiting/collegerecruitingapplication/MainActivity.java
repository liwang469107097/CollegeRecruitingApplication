package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private EditText userNameView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.addUniversityInformation(new University("Harvard University", BitmapFactory.decodeResource(
                getResources(), R.drawable.commercebank3)));
        dbHandler.addUniversityInformation(new University("Kansas State University", BitmapFactory.decodeResource(
                getResources(), R.drawable.commercebank1)));
        dbHandler.addUniversityInformation(new University("Stanford University", BitmapFactory.decodeResource(
                getResources(), R.drawable.commercebank2)));
        dbHandler.addProfessionalType(new ProfessionalType("Accounting"));
        dbHandler.addProfessionalType(new ProfessionalType("Computer Engineering"));
        dbHandler.addProfessionalType(new ProfessionalType("Finance"));

        dbHandler.close();
        */


        userNameView = (EditText) findViewById(R.id.account);
        passwordView = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });


        /*
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.addUniversityInformation(new University("Harvard University", BitmapFactory.decodeResource(
                getResources(), R.drawable.commercebank3)));
        dbHandler.addUniversityInformation(new University("Kansas State University", BitmapFactory.decodeResource(
                getResources(), R.drawable.commercebank1)));
        dbHandler.addUniversityInformation(new University("Stanford University", BitmapFactory.decodeResource(
                getResources(), R.drawable.commercebank2)));
        dbHandler.addProfessionalType(new ProfessionalType("Accounting"));
        dbHandler.addProfessionalType(new ProfessionalType("Computer Engineering"));
        dbHandler.addProfessionalType(new ProfessionalType("Finance"));

        dbHandler.close();
        */
    }

    private void Login() {

        //get username and password.
        String userName = userNameView.getText().toString();
        passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        String password = passwordView.getText().toString();
        passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        DbHandler db = new DbHandler(MainActivity.this);
        ArrayList<Users> usersList = db.getAllUsers();
        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> passwords = new ArrayList<String>();
        for (Users u: usersList) {
            users.add(u.getUsername());
            passwords.add(u.getPassword());
        }


        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            if (users.contains(userName) && passwords.contains(password)) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);//jump to management activity.
                startActivity(intent);

            }
            else {
                //Pop-up message
                passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());

                Toast toast = Toast.makeText(MainActivity.this, "Invalid username and password, please reenter", Toast.LENGTH_LONG);

                LinearLayout linearLayout = (LinearLayout) toast.getView();
                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                messageTextView.setTextSize(25);
                toast.show();
            }
        }
        else {

            passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Toast toast = Toast.makeText(MainActivity.this, "Please enter the username and password", Toast.LENGTH_LONG);

            LinearLayout linearLayout = (LinearLayout) toast.getView();
            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
            messageTextView.setTextSize(25);
            toast.show();

        }
    }

    public void signup(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);//jump to management activity.
        startActivity(intent);
    }

    /*
    public void launchIntroductionActivity(View view) {
        Intent intent = new Intent(this, IntroductionActivity.class);
        startActivity(intent);
    }

    public void launchLoginActivity(View view) {
        Intent intent = new Intent(this, .class);
        startActivity(intent);
    }
    */
}
