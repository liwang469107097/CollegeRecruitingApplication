package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.app.Activity;
import android.content.Intent;
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

public class SignupActivity extends Activity {

    private EditText userNameView, passwordView, confirmView, secureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userNameView = (EditText) findViewById(R.id.signupUsername);
        passwordView = (EditText) findViewById(R.id.signupPassword);
        confirmView = (EditText) findViewById(R.id.signupConfirmPassword);
        secureView = (EditText) findViewById(R.id.signupSecureNumber);

        Button signupButton = (Button) findViewById(R.id.signupBtn);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userNameView.getText().toString()) || TextUtils.isEmpty(passwordView.getText().toString()) ||
                        TextUtils.isEmpty(confirmView.getText().toString()) || TextUtils.isEmpty(secureView.getText().toString())) {
                    Toast toast = Toast.makeText(SignupActivity.this, "Please fill all the information", Toast.LENGTH_LONG);

                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(25);
                    toast.show();

                }
                else {
                    if (secureView.getText().toString().contains("111111")) {

                        if (!confirmView.getText().toString().contains(passwordView.getText().toString())) {
                            Toast toast = Toast.makeText(SignupActivity.this, "Not same password", Toast.LENGTH_LONG);

                            LinearLayout linearLayout = (LinearLayout) toast.getView();
                            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                            messageTextView.setTextSize(25);
                            toast.show();
                        }
                        else{
                            DbHandler db = new DbHandler(SignupActivity.this);
                            ArrayList<Users> usersList = db.getAllUsers();
                            ArrayList<String> username = new ArrayList<String>();
                            for (Users u: usersList) {
                                username.add(u.getUsername());
                            }
                            if (username.contains(userNameView.getText().toString())) {
                                Toast toast = Toast.makeText(SignupActivity.this, "Username has existed", Toast.LENGTH_LONG);

                                LinearLayout linearLayout = (LinearLayout) toast.getView();
                                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                                messageTextView.setTextSize(25);
                                toast.show();
                            }
                            else {
                                Users u = new Users(userNameView.getText().toString(), passwordView.getText().toString());
                                db.addUser(u);
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            db.close();
                        }



                    }
                    else {
                        Toast toast = Toast.makeText(SignupActivity.this, "Please enter valid secure number", Toast.LENGTH_LONG);

                        LinearLayout linearLayout = (LinearLayout) toast.getView();
                        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                        messageTextView.setTextSize(25);
                        toast.show();
                    }


                }
            }
        });
    }


}
