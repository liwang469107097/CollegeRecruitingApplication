package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InformationActivity extends Activity {

    private EditText lastNameView, firstNameView, emailView, phoneView, majorView, graduationDateView, positionInterestedInView;
    private Spinner degreeView;
    private String comment;
    private int universityId, professionalTypeId;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        lastNameView = (EditText) findViewById(R.id.last_name);

        firstNameView = (EditText) findViewById(R.id.first_name);
        emailView = (EditText) findViewById(R.id.email);
        phoneView = (EditText) findViewById(R.id.phone);
        majorView = (EditText) findViewById(R.id.major);
        degreeView = (Spinner) findViewById(R.id.information_degree);
        graduationDateView = (EditText) findViewById(R.id.expected_graduate_date);
        positionInterestedInView = (EditText) findViewById(R.id.position_interested_in);
        comment = "";



        //accept university id and professional type id from setting activity
        Intent i = getIntent();
        universityId = i.getIntExtra("universityId", 0);
        professionalTypeId = i.getIntExtra("professionalTypeId", 0);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastNameView.getText().toString().isEmpty() || firstNameView.getText().toString().isEmpty() || emailView.getText().toString().isEmpty() ||
                        phoneView.getText().toString().isEmpty() || majorView.getText().toString().isEmpty() || degreeView.getSelectedItem().toString().contains("None") ||
                        graduationDateView.getText().toString().isEmpty() || positionInterestedInView.getText().toString().isEmpty()) {
                    Toast toast= Toast.makeText(InformationActivity.this, "Please fill in all the information", Toast.LENGTH_LONG);

                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(25);
                    toast.show();
                }
                else {
                    new AlertDialog.Builder(InformationActivity.this).setTitle("Confirm Information").setItems(
                            new String[] { "Last Name: " + lastNameView.getText().toString(), "First Name: " + firstNameView.getText().toString(),
                                    "Email: " + emailView.getText().toString(), "Phone: " + phoneView.getText().toString(), "Major: " + majorView.getText().toString(),
                                    "Degree: " + degreeView.getSelectedItem().toString(), "GraduationDate: " + graduationDateView.getText().toString(), "Position Interested In: " + positionInterestedInView.getText().toString()},
                            null).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DbHandler db = new DbHandler(InformationActivity.this);
                            db.addStudentInformation(new StudentInformation(lastNameView.getText().toString(), firstNameView.getText().toString(),
                                    emailView.getText().toString(), phoneView.getText().toString(), majorView.getText().toString(),
                                    degreeView.getSelectedItem().toString(), graduationDateView.getText().toString(), positionInterestedInView.getText().toString(),
                                    comment, universityId, professionalTypeId));
                            db.close();
                            Toast toast= Toast.makeText(InformationActivity.this, "Submit Successful", Toast.LENGTH_LONG);

                            LinearLayout linearLayout = (LinearLayout) toast.getView();
                            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                            messageTextView.setTextSize(25);
                            toast.show();
                            finish();
                        }
                    }).setNegativeButton("Cancel", null).show();
                }

            }
        });


        /*
        //send the information to management activity
        Intent intent = new Intent(InformationActivity.this, ManagementActivity.class);
        intent.putExtra("lastName", lastName.toString());
        intent.putExtra("firstName", firstName.toString());
        intent.putExtra("lastName", email.toString());
        intent.putExtra("lastName", phone.toString());
        intent.putExtra("lastName", major.toString());
        intent.putExtra("lastName", d);
        intent.putExtra("lastName", graduationDate.toString());
        intent.putExtra("lastName", positionInterestedIn.toString());
        intent.putExtra("lastName", comment);
        intent.putExtra("universityId", universityId);
        intent.putExtra("professionalTypeId", professionalTypeId);

        */
    }


}
