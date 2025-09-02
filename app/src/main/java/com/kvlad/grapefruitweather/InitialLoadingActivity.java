package com.kvlad.grapefruitweather;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


// Activity that checks if API key is valid and calls either Add_OWM_API_Key_Activity
// or MainActivity based on the outcome.
public class InitialLoadingActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcher;
    private SharedPreferences sharedPref;

    private void proceedToMainMenu() {
        Intent intent = new Intent(InitialLoadingActivity.this, MainActivity.class);
        launcher.launch(intent);
    }

    private void proceedToAPIKeyScreen() {
        Intent intent = new Intent(InitialLoadingActivity.this, AddOWMAPIKeyActivity.class);
        launcher.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_initial_loading_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Processes result from AddLocationActivity.
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getData() != null) {
                        if (result.getData().getStringExtra("task").equals("SET_API_KEY")) {
                            proceedToMainMenu();
                        }
                    } else {
                        finish();
                    }
                }
        );

        sharedPref = InitialLoadingActivity.this.getSharedPreferences("preferences", MODE_PRIVATE);
        if (sharedPref.getString("OWM_API_KEY", null) == null) {
            proceedToAPIKeyScreen();
        } else {
            proceedToMainMenu();
        }

    }
}