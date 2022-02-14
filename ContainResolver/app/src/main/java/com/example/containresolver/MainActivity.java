package com.example.containresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = "me.hsgamer.contentproviderdemo.provider";
    public static final String BASE_PATH = "students";
    public static final String URL = "content://" + AUTHORITY + "/" + BASE_PATH;
    public static final Uri CONTENT_URI = Uri.parse(URL);



    static final String ID = "student_id";
    static final String STUDENT_NAME = "student_name";
    static final String STUDENT_YEAR = "student_year";


    private final String[] mColumnProjection = new String[] {
            ID,
            STUDENT_NAME,
            STUDENT_YEAR
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView studentList = (TextView) findViewById(R.id.list_view);
        Button loadButton = (Button) findViewById(R.id.loadButton);
        ContentResolver contentResolver = getContentResolver();

        loadButton.setOnClickListener(view -> {

//            retrieve students records
            try(Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null))
            {

                int idIndex = cursor.getColumnIndexOrThrow(ID);
                int nameIndex = cursor.getColumnIndexOrThrow(STUDENT_NAME);
                int yearIndex = cursor.getColumnIndexOrThrow(STUDENT_YEAR);

                if (cursor.moveToFirst()) {
                    StringBuilder strBuild = new StringBuilder();
                    while (!cursor.isAfterLast()) {
                        strBuild.append("\n")
                                .append(cursor.getString(idIndex))
                                .append(" - ")
                                .append(cursor.getString(nameIndex))
                                .append(" - ")
                                .append(cursor.getString(yearIndex));
                        cursor.moveToNext();
                    }
                    studentList.setText(strBuild.toString());
                } else {
                    studentList.setText("No Student Found!");
                }
            }
        });


    }
}