package com.bkteam.ohmychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class courseDb extends SQLiteOpenHelper {
        private static final String database="Course_Database";
        private static final String table="course_tbl";
        private static final String col_1="id";
        private static final String col_2="course_id";
        private static final String col_3="course_name";

        public courseDb(Context context)
        {
            super(context,database,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+ table + "(id integer primary key autoincrement,course_id varchar(255)," +
                    "course_name varchar(255))");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(" drop table if exists "+table);
        }
        public boolean registerCourse(String cid,String name)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(col_2,cid);
            cv.put(col_3,name);
            long result=db.insert(table,null,cv);
            db.close();
            if(result==-1)
            {
                return false;
            }else
            {
                return true;
            }
        }

    public List<String> getAllCourse() {
        List<String>labels=new ArrayList<String>();
        //select course_name column
        String selectQuery=" SELECT " + col_3 + " FROM "+ table;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        //loop thru all rows
        if(cursor.moveToFirst())
        {
            do{
                labels.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close connection
        cursor.close();
        db.close();
        return labels;
    }
}

