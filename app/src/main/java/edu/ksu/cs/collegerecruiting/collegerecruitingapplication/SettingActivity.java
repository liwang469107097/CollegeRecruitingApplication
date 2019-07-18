package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends Activity {

    private Button setUpBtn, addUniversityBtn, addProfessionalBtn, generateBtn;
    private Spinner spUniversity, spProfessionalType;
    private Bitmap bitmap;
    private ArrayList<University> universities;
    private ArrayList<String> universityList = new ArrayList<String>();
    private ArrayList<ProfessionalType> professionals;
    private ArrayList<String> profissionalList = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        universityList.add("None");
        profissionalList.add("None");
        DbHandler dbHandler = new DbHandler(SettingActivity.this);
        universities = dbHandler.getAllUniversities();
        professionals = dbHandler.getAllProfessionalType();
        dbHandler.close();
        for (University university : universities) {
            universityList.add(university.getName());
        }

        for (ProfessionalType p : professionals) {
            profissionalList.add(p.getName());
        }

        spUniversity = (Spinner) findViewById(R.id.spinnerUniversity);
        spProfessionalType = (Spinner) findViewById(R.id.spinnerProfessionalType);




        addUniversityBtn = (Button) findViewById(R.id.addUniversityBtn);
        addUniversityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.activity_window, (ViewGroup) findViewById(R.id.dialog));


                final AlertDialog  dialog = new AlertDialog.Builder(SettingActivity.this).setTitle("Add University Information")
                        .setView(layout).setNegativeButton("Cancel", null)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etUniversityName = (EditText)layout.findViewById(R.id.etname);
                                String universityName = etUniversityName.getText().toString();
                               // selectImage();

                                /*
                                addImageBtn = (Button) findViewById(R.id.addImageBtn);
                                addImageBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        selectImage();
                                    }
                                });*/


                                //dbHandler.addUniversityInformation(new University(universityName, bitmap));
                               // dbHandler.close();
                                if(!universityList.contains(universityName)) {
                                    universityList.add(universityName);
                                    DbHandler db = new DbHandler(SettingActivity.this);
                                    db.addUniversityInformation(new University(universityName, bitmap));
                                    db.close();
                                }
                                else {
                                    Toast toast = Toast.makeText(SettingActivity.this, "The university's name has existed", Toast.LENGTH_LONG);
                                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                                    messageTextView.setTextSize(25);
                                    toast.show();
                                }


                                /*
                                DbHandler dbHandler = new DbHandler(SettingActivity.this);
                                dbHandler.addUniversityInformation(new University("Harvard University", BitmapFactory.decodeResource(
                                        getResources(), R.drawable.commercebank3)));
                                dbHandler.addUniversityInformation(new University("Kansas State University", BitmapFactory.decodeResource(
                                        getResources(), R.drawable.commercebank1)));
                                dbHandler.addUniversityInformation(new University("Stanford University", BitmapFactory.decodeResource(
                                        getResources(), R.drawable.commercebank2)));
                                dbHandler.addProfessionalType(new ProfessionalType("Accounting"));
                                dbHandler.addProfessionalType(new ProfessionalType("Computer Engineering"));
                                dbHandler.addProfessionalType(new ProfessionalType("Finance"));

                                dbHandler.close();*/
                            }


                        }).show();



                /*WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800;
                params.height = 1200;
                dialog.getWindow().setAttributes(params);*/

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingActivity.this, R.layout.support_simple_spinner_dropdown_item, universityList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spUniversity.setAdapter(adapter);

        addProfessionalBtn = (Button) findViewById(R.id.addProfessionalBtn);
        addProfessionalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.activity_professional_window, (ViewGroup) findViewById(R.id.professionalDialog));


                final AlertDialog  dialog = new AlertDialog.Builder(SettingActivity.this).setTitle("Add Professional Information")
                        .setView(layout).setNegativeButton("Cancel", null)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etProfessionalName = (EditText)layout.findViewById(R.id.windowProfessionalEditName);
                                String professionalName = etProfessionalName.getText().toString();

                                if(!profissionalList.contains(professionalName)) {
                                    profissionalList.add(professionalName);
                                    DbHandler db = new DbHandler(SettingActivity.this);
                                    db.addProfessionalType(new ProfessionalType(professionalName));
                                    db.close();
                                }
                                else {
                                    Toast toast = Toast.makeText(SettingActivity.this, "The professional's name has existed", Toast.LENGTH_LONG);

                                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                                    messageTextView.setTextSize(25);
                                    toast.show();
                                }
                            }
                        }).show();

            }
        });


        ArrayAdapter<String> professionalAdapter = new ArrayAdapter<String>(SettingActivity.this, R.layout.support_simple_spinner_dropdown_item, profissionalList);
        professionalAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        professionalAdapter.notifyDataSetChanged();
        spProfessionalType.setAdapter(professionalAdapter);

        setUpBtn = (Button)findViewById(R.id.setUpBtn);
        setUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String university = spUniversity.getSelectedItem().toString();
                String professionalType = spProfessionalType.getSelectedItem().toString();
                if (university != "None" &&professionalType != "None") {
                    DbHandler dbHandler = new DbHandler(SettingActivity.this);

                    //get the data from database
                    University u = dbHandler.getUniversityByName(university);
                    ProfessionalType pt = dbHandler.getProfessionalTypesByName(professionalType);

                    dbHandler.close();

                    //send the information to introduction activity
                    Intent intent = new Intent(SettingActivity.this, IntroductionActivity.class);
                    intent.putExtra("universityId", u.getId());
                    intent.putExtra("universityName", u.getName());
                    intent.putExtra("photo", Utility.getBytes(u.getPhoto()));
                    intent.putExtra("professionalTypeId", pt.getId());
                    intent.putExtra("professionalName", pt.getName());

                    startActivity(intent);
                }
                else    {
                    Toast toast = Toast.makeText(SettingActivity.this, "Please select a university name and professional name", Toast.LENGTH_LONG);

                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(25);
                    toast.show();
                }
            }
        });

        generateBtn = (Button)findViewById(R.id.generateBtn);
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String university = spUniversity.getSelectedItem().toString();
                String professionalType = spProfessionalType.getSelectedItem().toString();
                if (university != "None" &&professionalType != "None"){
                    DbHandler dbHandler = new DbHandler(SettingActivity.this);
                    University u = dbHandler.getUniversityByName(university);
                    ProfessionalType pt = dbHandler.getProfessionalTypesByName(professionalType);
                    dbHandler.close();
                    Intent intent = new Intent(SettingActivity.this, ManagementActivity.class);
                    intent.putExtra("universityId", u.getId());
                    intent.putExtra("professionalTypeId", pt.getId());
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(SettingActivity.this, "Please select a university name and professional name", Toast.LENGTH_LONG);

                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(25);
                    toast.show();
                }


            }
        });
    }

    public void addImage(View view) {
        selectImage();
    }
    static final int REQUEST_IMAGE_OPEN = 1;

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == RESULT_OK) {
            try {
                Uri fullPhotoUri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }


    /*public void setUp(View view){
        String university = spUniversity.getSelectedItem().toString();
        String professionalType = spProfessionalType.getSelectedItem().toString();
        DbHandler dbHandler = new DbHandler(SettingActivity.this);

        //get the data from database
        University u = dbHandler.getUniversityByName(university);
        ProfessionalType pt = dbHandler.getProfessionalTypesByName(professionalType);

        dbHandler.close();

        //send the information to introduction activity
        Intent intent=new Intent(SettingActivity.this, IntroductionActivity.class);
        intent.putExtra("universityName", u.getName());
        intent.putExtra("photo", Utility.getBytes(u.getPhoto()));
        intent.putExtra("professionalName", pt.getName());

        //send the information to information activity
        Intent intent2 = new Intent(SettingActivity.this, InformationActivity.class);
        intent.putExtra("universityId", u.getId());
        intent.putExtra("professionalTypeId", pt.getId());
    }*/
}
