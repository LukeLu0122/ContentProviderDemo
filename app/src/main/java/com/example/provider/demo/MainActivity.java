package com.example.provider.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.provider.demo.provider.StudentsProvider;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();
        values.put(StudentsProvider.NAME, ((EditText)findViewById(R.id.editText2)).getText().toString());
        values.put(StudentsProvider.GRADE, ((EditText)findViewById(R.id.editText3)).getText().toString());
        //[*]
        Uri uri = getContentResolver().insert(StudentsProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view) {
        // Retrieve student records
        String URL = "content://com.example.MyApplication.StudentsProvider";

        Uri students = Uri.parse(URL);
        CursorLoader loader = new CursorLoader(this, students, null, null, null, "name");
        //Cursor c = managedQuery(students, null, null, null, "name");
        Cursor c = loader.loadInBackground();

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(StudentsProvider._ID)) +
                                ", " +  c.getString(c.getColumnIndex( StudentsProvider.NAME)) +
                                ", " + c.getString(c.getColumnIndex( StudentsProvider.GRADE)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

//    public static class StudentsProvider {
//        static final String PROVIDER_NAME = "com.example.MyApplication.StudentsProvider";
//        static final String URL = "content://" + PROVIDER_NAME + "/students";
//        static final Uri CONTENT_URI = Uri.parse(URL);
//        static final String _ID = "_id";
//        static final String NAME = "name";
//        static final String GRADE = "grade";
//    }
}