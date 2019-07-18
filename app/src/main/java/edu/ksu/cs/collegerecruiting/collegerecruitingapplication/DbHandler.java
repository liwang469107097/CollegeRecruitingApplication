package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CollegeRecruiting.db";

    //table name
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_UNIVERSITY = "university";
    private static final String TABLE_PROFESSIONAL_TYPES = "professionalTypes";
    private static final String TABLE_USERS = "users";

    //student table columns name
    private static final String STUDENTS_KEY_ID = "id";
    private static final String STUDENTS_KEY_FIRST_NAME = "firstName";
    private static final String STUDENTS_KEY_LAST_NAME = "lastName";
    private static final String STUDENTS_KEY_EMAIL = "email";
    private static final String STUDENTS_KEY_PHONE = "phone";
    private static final String STUDENTS_KEY_MAJOR = "major";
    private static final String STUDENTS_KEY_DEGREE = "degree";
    private static final String STUDENTS_KEY_GRADUATION_DATE = "graduationDate";
    private static final String STUDENTS_KEY_POSITION_INTERESTED_IN = "positionInterestedIn";
    private static final String STUDENTS_KEY_COMMENT = "comment";
    private static final String STUDENTS_KEY_UNIVERSITY_ID = "universityId";
    private static final String STUDENTS_KEY_PROFESSIONAL_TYPE_ID = "professionalTypeId";

    //university table columns name
    private static final String UNIVERSITY_KEY_ID = "id";
    private static final String UNIVERSITY_KEY_NAME = "name";
    private static final String UNIVERSITY_KEY_PHOTO = "photo";

    //professional types table columns name
    private static final String PROFESSIONAL_TYPES_KEY_ID = "id";
    private static final String PROFESSIONAL_TYPES_KEY_NAME = "name";

    //users table columns name
    private static final String USERS_KEY_ID = "id";
    private static final String USERS_KEY_USERNAME = "username";
    private static final String USERS_KEY_PASSWORD = "password";


    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create students table
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENTS+ "("
                + STUDENTS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + STUDENTS_KEY_LAST_NAME + " TEXT,"
                + STUDENTS_KEY_FIRST_NAME + " TEXT,"
                + STUDENTS_KEY_EMAIL + " TEXT,"
                + STUDENTS_KEY_PHONE + " TEXT,"
                + STUDENTS_KEY_MAJOR + " TEXT,"
                + STUDENTS_KEY_DEGREE + " TEXT,"
                + STUDENTS_KEY_GRADUATION_DATE + " DATE,"
                + STUDENTS_KEY_POSITION_INTERESTED_IN + " TEST,"
                + STUDENTS_KEY_COMMENT + " TEXT,"
                + STUDENTS_KEY_UNIVERSITY_ID + " INTEGER,"
                + STUDENTS_KEY_PROFESSIONAL_TYPE_ID + " INTEGER,"
                + "FOREIGN KEY(" + STUDENTS_KEY_UNIVERSITY_ID + ") REFERENCES "+ TABLE_UNIVERSITY +"("+ UNIVERSITY_KEY_ID +"),"
                + "FOREIGN KEY(" + STUDENTS_KEY_PROFESSIONAL_TYPE_ID + ") REFERENCES " + TABLE_PROFESSIONAL_TYPES +"("+ PROFESSIONAL_TYPES_KEY_ID+"))";
        db.execSQL(CREATE_STUDENT_TABLE);

        //create university table
        String CREATE_UNIVERSITY_TABLE = "CREATE TABLE " + TABLE_UNIVERSITY + "("
                + UNIVERSITY_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UNIVERSITY_KEY_NAME + " TEXT,"
                + UNIVERSITY_KEY_PHOTO + " BLOB)";
        db.execSQL(CREATE_UNIVERSITY_TABLE);

        //create professional types table
        String CREATE_PROFESSIONAL_TYPES_TABLE = "CREATE TABLE " + TABLE_PROFESSIONAL_TYPES + "("
                + PROFESSIONAL_TYPES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROFESSIONAL_TYPES_KEY_NAME + " TEXT)";
        db.execSQL(CREATE_PROFESSIONAL_TYPES_TABLE);

        //create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USERS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERS_KEY_USERNAME + " TEXT,"
                + USERS_KEY_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
/*
        db.execSQL("INSERT INTO " + TABLE_UNIVERSITY + "(" + UNIVERSITY_KEY_NAME + ", " + UNIVERSITY_KEY_PHOTO +
                ") VALUES ('Harvard University', BitmapFactory.decodeResourcegetResources(), R.drawable.commercebank3)");
                */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIVERSITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFESSIONAL_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }


    //insert information to student database
    public void addStudentInformation (StudentInformation si) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENTS_KEY_LAST_NAME, si.getLastName());
        cv.put(STUDENTS_KEY_FIRST_NAME, si.getFirstName());
        cv.put(STUDENTS_KEY_EMAIL, si.getEmail());
        cv.put(STUDENTS_KEY_PHONE, si.getPhone());
        cv.put(STUDENTS_KEY_MAJOR, si.getMajor());
        cv.put(STUDENTS_KEY_DEGREE, si.getDegree());
        cv.put(STUDENTS_KEY_GRADUATION_DATE, si.getGraduationDate());
        cv.put(STUDENTS_KEY_POSITION_INTERESTED_IN, si.getPositionInterestedIn());
        cv.put(STUDENTS_KEY_COMMENT, si.getComment());
        cv.put(STUDENTS_KEY_UNIVERSITY_ID, si.getUniversityId());
        cv.put(STUDENTS_KEY_PROFESSIONAL_TYPE_ID, si.getProfessionalTypesId());

        // Inserting Row
        db.insert(TABLE_STUDENTS, null, cv);
        db.close(); // Closing database connection


    }

    //insert information to university database
    public void addUniversityInformation(University university)  {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(UNIVERSITY_KEY_NAME, university.getName());
        cv.put(UNIVERSITY_KEY_PHOTO, Utility.getBytes(university.getPhoto()));

        db.insert(TABLE_UNIVERSITY, null, cv);
    }

    //insert information to professional type database
    public void addProfessionalType(ProfessionalType pt)  {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PROFESSIONAL_TYPES_KEY_NAME, pt.getName());

        db.insert(TABLE_PROFESSIONAL_TYPES, null, cv);
        db.close();
    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(USERS_KEY_USERNAME, user.getUsername());
        cv.put(USERS_KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, cv);
        db.close();
    }

    public ArrayList<Users> getAllUsers() {
        ArrayList<Users> usersList = new ArrayList<Users>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuerie = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(sqlQuerie, null);

        while(cursor.moveToNext()) {
            Users user = new Users();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            usersList.add(user);
        }

        db.close();

        return usersList;
    }

    public ArrayList<University> getAllUniversities() {
        ArrayList<University> universities = new ArrayList<University>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_UNIVERSITY;
        Cursor cursor = db.rawQuery(sqlQuerie, null);

        while(cursor.moveToNext()) {
            University university = new University();
            university.setId(Integer.parseInt(cursor.getString(0)));
            university.setName(cursor.getString(1));
            university.setPhoto(Utility.getPhoto(cursor.getBlob(2)));
            universities.add(university);
        }

        db.close();

        return universities;
    }


    public ArrayList<ProfessionalType> getAllProfessionalType() {
        ArrayList<ProfessionalType> professionalTypeList = new ArrayList<ProfessionalType>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_PROFESSIONAL_TYPES;
        Cursor cursor = db.rawQuery(sqlQuerie, null);

        while(cursor.moveToNext()) {
            ProfessionalType professionalType = new ProfessionalType();
            professionalType.setId(Integer.parseInt(cursor.getString(0)));
            professionalType.setName(cursor.getString(1));

            professionalTypeList.add(professionalType);
        }

        db.close();

        return professionalTypeList;
    }

    public University getUniversityByName (String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_UNIVERSITY;
        Cursor cursor = db.query(TABLE_UNIVERSITY, new String[]{UNIVERSITY_KEY_ID,
                UNIVERSITY_KEY_NAME, UNIVERSITY_KEY_PHOTO}, UNIVERSITY_KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        University university = new University();
        university.setId(Integer.parseInt(cursor.getString(0)));
        university.setName(cursor.getString(1));
        university.setPhoto(Utility.getPhoto(cursor.getBlob(2)));
        db.close();

        return university;
    }

    public University getUniversityById (int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_UNIVERSITY;
        Cursor cursor = db.query(TABLE_UNIVERSITY, new String[]{UNIVERSITY_KEY_ID,
                        UNIVERSITY_KEY_NAME, UNIVERSITY_KEY_PHOTO}, UNIVERSITY_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        University university = new University();
        university.setId(Integer.parseInt(cursor.getString(0)));
        university.setName(cursor.getString(1));
        university.setPhoto(Utility.getPhoto(cursor.getBlob(2)));
        db.close();

        return university;
    }



    public ProfessionalType getProfessionalTypesByName (String name) {

        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_PROFESSIONAL_TYPES;
        Cursor cursor = db.query(TABLE_PROFESSIONAL_TYPES, new String[]{PROFESSIONAL_TYPES_KEY_ID,
                        PROFESSIONAL_TYPES_KEY_NAME}, PROFESSIONAL_TYPES_KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ProfessionalType pt = new ProfessionalType();
        pt.setId(Integer.parseInt(cursor.getString(0)));
        pt.setName(cursor.getString(1));
        db.close();
        return pt;
    }
    public ProfessionalType getProfessionalTypesById (int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_PROFESSIONAL_TYPES;
        Cursor cursor = db.query(TABLE_PROFESSIONAL_TYPES, new String[]{PROFESSIONAL_TYPES_KEY_ID,
                        PROFESSIONAL_TYPES_KEY_NAME}, PROFESSIONAL_TYPES_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ProfessionalType pt = new ProfessionalType();
        pt.setId(Integer.parseInt(cursor.getString(0)));
        pt.setName(cursor.getString(1));
        db.close();
        return pt;
    }

    public StudentInformation getStudentInformationById (int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String sqlQuerie = "SELECT * FROM " + TABLE_STUDENTS;
        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{STUDENTS_KEY_ID,
                        STUDENTS_KEY_LAST_NAME, STUDENTS_KEY_FIRST_NAME, STUDENTS_KEY_EMAIL,STUDENTS_KEY_PHONE,
                STUDENTS_KEY_MAJOR, STUDENTS_KEY_DEGREE, STUDENTS_KEY_GRADUATION_DATE, STUDENTS_KEY_POSITION_INTERESTED_IN,
                STUDENTS_KEY_UNIVERSITY_ID, STUDENTS_KEY_PROFESSIONAL_TYPE_ID, STUDENTS_KEY_COMMENT}, STUDENTS_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        StudentInformation si = new StudentInformation();
        si.setId(Integer.parseInt(cursor.getString(0)));
        si.setLastName(cursor.getString(1));
        si.setFirstName(cursor.getString(2));
        si.setEmail(cursor.getString(3));
        si.setPhone(cursor.getString(4));
        si.setMajor(cursor.getString(5));
        si.setDegree(cursor.getString(6));
        si.setGraduationDate(cursor.getString(7));
        si.setPositionInterestedIn(cursor.getString(8));
        si.setUniversityId(Integer.parseInt(cursor.getString(9)));
        si.setProfessionalTypesId(Integer.parseInt(cursor.getString(10)));
        si.setComment(cursor.getString(11));

        db.close();
        return si;
    }

    public List<StudentInformation> getAllStudentsInformation(){
        List<StudentInformation> studentInformationsList = new ArrayList<StudentInformation>();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

            while (cursor.moveToNext()) {
                StudentInformation si = new StudentInformation();
                si.setId(Integer.parseInt(cursor.getString(0)));
                si.setLastName(cursor.getString(1));
                si.setFirstName(cursor.getString(2));
                si.setEmail(cursor.getString(3));
                si.setPhone(cursor.getString(4));
                si.setMajor(cursor.getString(5));
                si.setDegree(cursor.getString(6));
                si.setGraduationDate(cursor.getString(7));
                si.setPositionInterestedIn(cursor.getString(8));
                si.setComment(cursor.getString(9));
                si.setUniversityId(Integer.parseInt(cursor.getString(10)));
                si.setProfessionalTypesId(Integer.parseInt(cursor.getString(11)));
                studentInformationsList.add(si);
            }
            cursor.close();
            db.close();
        return studentInformationsList;
    }

    public void updateStudentsInformationDetails(StudentInformation si) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STUDENTS_KEY_LAST_NAME, si.getLastName());
        cv.put(STUDENTS_KEY_FIRST_NAME, si.getFirstName());
        cv.put(STUDENTS_KEY_EMAIL, si.getEmail());
        cv.put(STUDENTS_KEY_PHONE, si.getPhone());
        cv.put(STUDENTS_KEY_MAJOR, si.getMajor());
        cv.put(STUDENTS_KEY_DEGREE, si.getDegree());
        cv.put(STUDENTS_KEY_GRADUATION_DATE, si.getGraduationDate());
        cv.put(STUDENTS_KEY_POSITION_INTERESTED_IN, si.getPositionInterestedIn());
        cv.put(STUDENTS_KEY_COMMENT, si.getComment());
        cv.put(STUDENTS_KEY_UNIVERSITY_ID, si.getUniversityId());
        cv.put(STUDENTS_KEY_PROFESSIONAL_TYPE_ID, si.getProfessionalTypesId());

        db.update(TABLE_STUDENTS, cv, STUDENTS_KEY_ID + " = ?",
                new String[] { String.valueOf(si.getId()) });
        db.close();
    }

    public void updateStudentsInformation(List<StudentInformation> siList) {
        String sqlUpdate = "";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv;
        for (StudentInformation si : siList) {
            if(si.getComment() != "") {
                cv = new ContentValues();

                cv.put(STUDENTS_KEY_LAST_NAME, si.getLastName());
                cv.put(STUDENTS_KEY_FIRST_NAME, si.getFirstName());
                cv.put(STUDENTS_KEY_EMAIL, si.getEmail());
                cv.put(STUDENTS_KEY_PHONE, si.getPhone());
                cv.put(STUDENTS_KEY_MAJOR, si.getMajor());
                cv.put(STUDENTS_KEY_DEGREE, si.getDegree());
                cv.put(STUDENTS_KEY_GRADUATION_DATE, si.getGraduationDate());
                cv.put(STUDENTS_KEY_POSITION_INTERESTED_IN, si.getPositionInterestedIn());
                cv.put(STUDENTS_KEY_COMMENT, si.getComment());
                cv.put(STUDENTS_KEY_UNIVERSITY_ID, si.getUniversityId());
                cv.put(STUDENTS_KEY_PROFESSIONAL_TYPE_ID, si.getProfessionalTypesId());

                db.update(TABLE_STUDENTS, cv, STUDENTS_KEY_ID + " = ?",
                        new String[] { String.valueOf(si.getId()) });
            }
        }
        db.close();
    }

    public void deleteStudentsInformationByid(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, STUDENTS_KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteStudentsInformation(StudentInformation si) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, STUDENTS_KEY_ID + " = ?",
                new String[] { String.valueOf(si.getId()) });
        db.close();
    }



}
