package com.bkteam.ohmychat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class unitsDb extends SQLiteOpenHelper {
        private static final String database="Unit_Database";
        private static final String table="units_tbl";
        private static final String col_1="id";
        private static final String col_2="Unit";
        private static final String col_3="Start_date";
        private static final String col_4="End_Date";

        public unitsDb(Context context)
        {
            super(context,database,null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+ table + "(id integer primary key autoincrement,Unit varchar(255)," +
                    "Start_date varchar(255),End_Date varchar(255))");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(" drop table if exists "+table);
        }
        public boolean registerUnit(String unit,String sdate,String edate)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(col_2,unit);
            cv.put(col_3,sdate);
            cv.put(col_4,edate);
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
        //get unit and load to spinner
        public List<String> getAllLabels()
        {
            List<String>labels=new ArrayList<String>();
            //select unit column
            String selectQuery=" SELECT " + col_2 + " FROM "+ table;
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

