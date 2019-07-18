package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ksu.cs.collegerecruiting.collegerecruitingapplication.StudentInformation;

public class ManagementActivity extends Activity {

    private TableLayout tableLayout;
    private int universityId, professionalTypeId;

    private static final int WRITE_PERMISSION = 0x01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        //tableLayout = (TableLayout) findViewById(R.id.tableLayout_information);
       // requestWritePermission();


        Intent i = getIntent();
        universityId = i.getIntExtra("universityId", 0);
        professionalTypeId = i.getIntExtra("professionalTypeId", 0);

        //createColumns();
        //fillData();
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<Map<String, String>> data = getData();
        SimpleAdapter aa = new SimpleAdapter(this, data, R.layout.activity_new_management,
                new String[]{"id", "lastName", "fistName", "email", "phone", "major", "degree", "graduationDate",
                        "positionInterestedIn", "university", "professionalType", "comment"}, new int[]{R.id.managementId, R.id.managementLastName, R.id.managementFirstName,
        R.id.managementEmail, R.id.managementPhone, R.id.managementMajor, R.id.managementDegree, R.id.managementGraduationDate,
        R.id.managementPositionInterestedIn, R.id.managementUniversity, R.id.managementProfessionalType, R.id.managementComment});
        listView.setAdapter(aa);
    }

    public ArrayList<Map<String, String>> getData() {
        Map<String, String> value;
        ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
        DbHandler db = new DbHandler(this);
        List<StudentInformation> sList = db.getAllStudentsInformation();
        University u;
        ProfessionalType pt;


        for (StudentInformation s: sList) {
            value = new HashMap<String, String>();
            u = db.getUniversityById(s.getUniversityId());
            pt = db.getProfessionalTypesById(s.getProfessionalTypesId());

            value.put("id", String.valueOf(s.getId()));
            value.put("lastName", s.getLastName());
            value.put("fistName", s.getFirstName());
            value.put("email", s.getEmail());
            value.put("phone", s.getPhone());
            value.put("major", s.getMajor());
            value.put("degree", s.getDegree());
            value.put("graduationDate", s.getGraduationDate());
            value.put("positionInterestedIn", s.getPositionInterestedIn());
            value.put("university", u.getName());
            value.put("professionalType", pt.getName());
            value.put("comment", s.getComment());
            data.add(value);
        }

        db.close();
        return data;
    }


    public void updateData(View view) {
        TextView idView = (TextView) findViewById(R.id.managementId);
        EditText commentView = (EditText) findViewById(R.id.managementComment);
        DbHandler dbHandler = new DbHandler(this);
        StudentInformation si = dbHandler.getStudentInformationById(Integer.valueOf(idView.getText().toString()));
        si.setComment(commentView.getText().toString());
        dbHandler.updateStudentsInformationDetails(si);
        dbHandler.close();
        Toast toast = Toast.makeText(ManagementActivity.this, "Update Succesfully", Toast.LENGTH_LONG);

        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(25);
        toast.show();
    }


    public void deleteData(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.activity_delete, (ViewGroup) findViewById(R.id.delete));


        final AlertDialog dialog = new AlertDialog.Builder(ManagementActivity.this).setTitle("Delete Student Information")
                .setView(layout).setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText idView = (EditText) layout.findViewById(R.id.deleteId);

                        DbHandler dbHandler = new DbHandler(ManagementActivity.this);

                        if (idView.getText().toString() == "") {
                            Toast toast = Toast.makeText(ManagementActivity.this, "Please Enter ID Number", Toast.LENGTH_LONG);

                            LinearLayout linearLayout = (LinearLayout) toast.getView();
                            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                            messageTextView.setTextSize(25);
                            toast.show();
                        }
                        else {
                            dbHandler.deleteStudentsInformationByid(Integer.valueOf(idView.getText().toString()));
                            dbHandler.close();
                            finish();
                            startActivity(getIntent());
                        }

                        //recreate();
                    }
                }).show();
    }


    public void addData(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.activity_add_information, (ViewGroup) findViewById(R.id.addInformation));


        final AlertDialog dialog = new AlertDialog.Builder(ManagementActivity.this).setTitle("Add Student Information")
                .setView(layout).setNegativeButton("Cancel", null)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText addInformationLastName = (EditText)layout.findViewById(R.id.addInformationLastName);
                        EditText addInformationFirstName = (EditText)layout.findViewById(R.id.addInformationFirstName);
                        EditText addInformationEmail = (EditText)layout.findViewById(R.id.addInformationEmail);
                        EditText addInformationPhone = (EditText)layout.findViewById(R.id.addInformationPhone);
                        EditText addInformationMajor = (EditText)layout.findViewById(R.id.addInformationMajor);
                        EditText addInformationDegree = (EditText)layout.findViewById(R.id.addInformationDegree);
                        EditText addInformationGraduationDate = (EditText)layout.findViewById(R.id.addInformationGraduationDate);
                        EditText addInformationPositionInteretedIn = (EditText)layout.findViewById(R.id.addInformationPositionInterestedIn);
                        EditText addInformationComment = (EditText)layout.findViewById(R.id.addInformationComment);

                        StudentInformation si = new StudentInformation(addInformationLastName.getText().toString(), addInformationFirstName.getText().toString(),
                                addInformationEmail.getText().toString(), addInformationPhone.getText().toString(), addInformationMajor.getText().toString(),
                                addInformationDegree.getText().toString(), addInformationGraduationDate.getText().toString(), addInformationPositionInteretedIn.getText().toString(),
                                addInformationComment.getText().toString(), universityId, professionalTypeId);

                        DbHandler db = new DbHandler(ManagementActivity.this);
                        db.addStudentInformation(si);
                        recreate();
                    }
                }).show();
    }

    public void exportToCSV(View view) {
        DbHandler db = new DbHandler(ManagementActivity.this);
        List<StudentInformation> siList = db.getAllStudentsInformation();
        University u;
        ProfessionalType pt;



        StringBuffer buffer = new StringBuffer();
        buffer.append("Last Name,First Name,Email,Phone,Major,Degree,Graduation Date,Position Interested In,University,Professional Type,Comment\r\n");
        for(StudentInformation si:siList){
            u = db.getUniversityById(si.getUniversityId());
            pt = db.getProfessionalTypesById(si.getProfessionalTypesId());
            buffer.append(si.getLastName() + "," + si.getFirstName() + "," + si.getEmail() + "," + si.getPhone() + "," + si.getMajor()
                    + "," + si.getDegree() + "," + si.getGraduationDate() + "," + si.getPositionInterestedIn() + "," + u.getName() + "," + pt.getName()
                    + "," + si.getComment() + "\r\n");
        }
        try {

            String data = buffer.toString();

            String filename = "studentinformation.csv";

            String state;
            state = Environment.getExternalStorageState();
            if(Environment.MEDIA_MOUNTED.equals(state)) {
                File Root = Environment.getExternalStorageDirectory();
                File dir = new File(Root.getAbsolutePath()+"/Download");
                if(!dir.exists())
                {
                    dir.mkdir();
                }

                File file = new File(dir, "studentinformation.csv");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(data.getBytes());
                fileOutputStream.close();
                Toast.makeText(getApplicationContext(), "Export Successfully", Toast.LENGTH_LONG).show();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == WRITE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void requestWritePermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION);
        }
    }
*/

    /*
    private void createColumns() {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        //ID Column
        TextView textViewId = new TextView(this);
        textViewId.setText("Id");
        textViewId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewId.setPadding(20, 20, 20, 20);
        tableRow.addView(textViewId);

        //Last Name Column
        TextView textViewLastName = new TextView(this);
        textViewLastName.setText("Last Name");
        textViewLastName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewLastName.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewLastName);

        //First Name Column
        TextView textViewFirstName = new TextView(this);
        textViewFirstName.setText("First Name");
        textViewFirstName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewFirstName.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewFirstName);

        //Email Column
        TextView textViewEmail = new TextView(this);
        textViewEmail.setText("Email");
        textViewEmail.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewEmail.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewEmail);

        //Phone Number Column
        TextView textViewPhone = new TextView(this);
        textViewPhone.setText("Phone Number");
        textViewPhone.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPhone.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewPhone);

        //Major Column
        TextView textViewMajor = new TextView(this);
        textViewMajor.setText("Major");
        textViewMajor.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewMajor.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewMajor);

        //Degree Column
        TextView textViewDegree = new TextView(this);
        textViewDegree.setText("Degree");
        textViewDegree.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewDegree.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewDegree);

        //Graduation Date Column
        TextView textViewGraduationDate = new TextView(this);
        textViewGraduationDate.setText("Graduation Date");
        textViewGraduationDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewGraduationDate.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewGraduationDate);

        //Position Interested In Column
        TextView textViewPosition = new TextView(this);
        textViewPosition.setText("Position Interested In");
        textViewPosition.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewPosition.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewPosition);

        //Comment Column
        TextView textViewComment = new TextView(this);
        textViewComment.setText("Comment");
        textViewComment.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textViewComment.setPadding(20, 20, 20, 20);;
        tableRow.addView(textViewComment);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
    }

    private void fillData() {

        for (StudentInformation si: studentInformationModel.findAll()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            //ID Column


            //Last Name Column
            TextView textViewLastName = new TextView(this);
            textViewLastName.setText(si.getLastName());
            textViewLastName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewLastName.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewLastName);

            //First Name Column
            TextView textViewFistName = new TextView(this);
            textViewFistName.setText(si.getFirstName());
            textViewFistName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewFistName.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewFistName);

            //Email Column
            TextView textViewEmail = new TextView(this);
            textViewEmail.setText(si.getEmail());
            textViewEmail.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewEmail.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewEmail);

            //Phone Number Column
            TextView textViewPhone = new TextView(this);
            textViewPhone.setText(si.getPhone());
            textViewPhone.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewPhone.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewPhone);

            //Major Column
            TextView textViewMajor = new TextView(this);
            textViewMajor.setText(si.getMajor());
            textViewMajor.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewMajor.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewMajor);

            //Degree Column
            TextView textViewDegree = new TextView(this);
            textViewDegree.setText(si.getDegree());
            textViewDegree.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewDegree.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewDegree);

            //Graduation Date Column
            TextView textViewGraduationDate = new TextView(this);
            textViewGraduationDate.setText(si.getGraduationDate());
            textViewGraduationDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewGraduationDate.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewGraduationDate);

            //Position Interested In Column
            TextView textViewPosition = new TextView(this);
            textViewPosition.setText(si.getPositionInterestedIn());
            textViewPosition.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewPosition.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewPosition);

            //Comment Column
            TextView textViewComment = new TextView(this);
            textViewComment.setText(si.getComment());
            textViewComment.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            textViewComment.setPadding(5, 5, 5, 10);
            tableRow.addView(textViewComment);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));
        }
    }*/
}