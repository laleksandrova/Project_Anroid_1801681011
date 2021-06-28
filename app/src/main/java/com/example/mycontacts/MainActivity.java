package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(new OnClickListenerAddContact());

        countRecords();

        readRecords();
    }

    public void countRecords() {
        int recordCount = new TableControllerContact(this).count();

        TextView textViewRecordCount = findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectContact> contacts = new TableControllerContact(this).read();

        if (contacts.size() > 0) {

            for (ObjectContact obj : contacts) {

                int id = obj.id;
                String contactFirstName = obj.firstName;
                String contactLastName = obj.lastName;

                String textViewContents = contactFirstName + " " + contactLastName;

                TextView textViewContactItem= new TextView(this);
                textViewContactItem.setPadding(20, 20, 20, 20);
                textViewContactItem.setText(textViewContents);
                textViewContactItem.setTag(Integer.toString(id));
                textViewContactItem.setTextSize(20);
                textViewContactItem.setTextColor(Color.BLACK);

                textViewContactItem.setOnLongClickListener(new OnLongClickListenerContactRecord());

                linearLayoutRecords.addView(textViewContactItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, SendSMS.class);
        startActivity(intent);
    }

}