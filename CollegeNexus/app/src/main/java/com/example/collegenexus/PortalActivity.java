package com.example.collegenexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);


        Button adminButton = findViewById(R.id.button);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PortalActivity.this, AdminPortalActivity.class);
                startActivity(intent);
            }
        });


        Button studentButton = findViewById(R.id.button2);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortalActivity.this, EventsListStudent.class);
                startActivity(intent);
            }
        });

    }
}

