package com.bkteam.ohmychat;

import android.content.Context;

import java.util.ArrayList;

public class TableHelper {

    //DECLARATIONS
    Context c;
    private String[] spaceProbeHeaders={"reg","year","course","gender","unit"};
    private String[][] spaceProbes;

    //CONSTRUCTOR
    public TableHelper(Context c) {
        this.c = c;
    }

    //RETURN TABLE HEADERS
    public String[] getSpaceProbeHeaders()
    {
        return spaceProbeHeaders;
    }

    //RETURN TABLE ROWS
    public  String[][] getSpaceProbes()
    {
        ArrayList<Student> spacecrafts=new StudentDb(c).retrieveSpacecrafts();
        Student s;

        spaceProbes= new String[spacecrafts.size()][5];

        for (int i=0;i<spacecrafts.size();i++) {

             s=spacecrafts.get(i);

            spaceProbes[i][0]=s.getRegistration_number();
            spaceProbes[i][1]=s.getYear();
            spaceProbes[i][2]=s.getCourse();
            spaceProbes[i][3]=s.getGender();
            spaceProbes[i][4]=s.getUnit();
        }

        return spaceProbes;





    }
}





