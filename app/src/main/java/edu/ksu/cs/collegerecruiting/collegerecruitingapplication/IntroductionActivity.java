package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroductionActivity extends Activity {

    private int universityId, professionalTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        TextView universityView = (TextView) findViewById(R.id.addUniversityName);
        TextView professionalView = (TextView) findViewById(R.id.addProfessional);

        ImageView iv =(ImageView) findViewById(R.id.imageView);
        Intent i = getIntent();
        iv.setImageBitmap(Utility.getPhoto(i.getByteArrayExtra("photo")));
        universityId = i.getIntExtra("universityId", 0);
        String universityName = i.getStringExtra("universityName");
        professionalTypeId = i.getIntExtra("professionalTypeId", 0);
        String professionalName = i.getStringExtra("professionalName");

        universityView.setText(universityName);
        professionalView.setText(professionalName);

    }

    public void returnToLastActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void returnToNextActivity(View view) {
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("universityId", universityId);
        intent.putExtra("professionalTypeId", professionalTypeId);
        startActivity(intent);
    }
}
