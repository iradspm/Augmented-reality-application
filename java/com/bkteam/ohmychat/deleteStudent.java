package com.bkteam.ohmychat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class deleteStudent extends Fragment {
    Button delete;
    TextInputEditText reg;
    deleteStudent(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.delete_dialog, container, false);
        reg = v.findViewById(R.id.regNo);
        delete = v.findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentDb studentDb = new StudentDb(getContext());
                String regNo = reg.getText().toString();
                if (TextUtils.isEmpty(regNo)) {
                    reg.setError(" Student registration number is required to delete");
                } else {
                    int result = studentDb.deleteStudent(regNo);
                    Toast.makeText(getContext(), result + ": Student deleted", Toast.LENGTH_SHORT).show();
                    reg.setText(" ");
                }
            }
        });
        return v;
    }
}
