package com.example.collegenexus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);


        EditText usernameEditText = findViewById(R.id.editTextText);
        EditText passwordEditText = findViewById(R.id.editTextText2);
        Button enterButton = findViewById(R.id.button3);

        // Set the predefined username and password
        final String correctUsername = "un";
        final String correctPassword = "pw";

        // Set onClickListener for the Enter button
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To get the entered username and password
                String enteredUsername = usernameEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();

                if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
                    // Redirect to AdminDashboardActivity if correct
                    Intent intent = new Intent(AdminPortalActivity.this, AdminDashboard.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(AdminPortalActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
