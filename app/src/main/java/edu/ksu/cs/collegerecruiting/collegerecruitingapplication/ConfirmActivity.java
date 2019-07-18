package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfirmActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    public void launchManagementActivity(View view) {
        Intent intent = new Intent(this, ManagementActivity.class);
        startActivity(intent);
    }

    public void launchSettingActivity(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
