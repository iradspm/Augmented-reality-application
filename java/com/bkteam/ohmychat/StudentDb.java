package com.bkteam.ohmychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class StudentDb extends SQLiteOpenHelper {
    private static final String database = "Student_Database";
    private static final String table = "student_tbl";
    private static final String col_1 = "id";
    private static final String col_2 = "regNo";
    private static final String col_3 = "yearOfStudy";
    private static final String col_4 = "course";
    private static final String col_5 = "gender";
    private static final String col_6= "unit";

    public StudentDb(Context context) {
        super(context, database, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table + "(id integer primary key autoincrement,regNo text,yearOfStudy text,course text,gender text,unit text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" drop table if exists " + table);
    }

    public boolean registerStudent(String reg, String year, String course, String gender,String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_2, reg);
        cv.put(col_3, year);
        cv.put(col_4, course);
        cv.put(col_5, gender);
        cv.put(col_6, unit);
        long result = db.insert(table, null, cv);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String loginStudent(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(table, null, "email=?", new String[]{email}, null, null, null);
        if (c.getCount() < 1) {
            c.close();
            return "Does not exist";
        }
        c.moveToFirst();
        String password = c.getString(c.getColumnIndex("password"));
        c.close();
        return password;
    }

    public boolean updateStudent(String reg, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col_3, password);
        int result = db.update(table, cv, "regNo=?", new String[]{reg});
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + table, null);
        return res;
    }

    public Cursor getSingleData(String reg) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(table, new String[]{col_2, col_3, col_4, col_5, col_6}, col_1 + "=?", new String[]{String.valueOf(reg)}, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public Integer deleteStudent(String reg) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i=db.delete(table,"regNo=?",new String[]{reg});
        return i;
    }

    //get students data from getter and setter
    public ArrayList<Student> retrieveSpacecrafts() {
        ArrayList<Student> students = new ArrayList<>();

        String[] columns = {col_1, col_2, col_3, col_4, col_5, col_6};

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.query(table, columns, null, null, null, null, null);

            Student s;

            if (c != null) {
                while (c.moveToNext()) {
                    int id=c.getInt(0);
                    String s_reg = c.getString(1);
                    String s_year = c.getString(2);
                    String s_course = c.getString(3);
                    String s_gender = c.getString(4);
                    String s_unit = c.getString(5);



                    s = new Student(id,s_reg,s_year,s_course,s_gender,s_unit);
                    s.setId(id);
                    s.setRegistration_number(s_reg);
                    s.setYear(s_year);
                    s.setCourse(s_course);
                    s.setGender(s_gender);
                    s.setUnit(s_unit);

                    students.add(s);
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return students;
    }
}

