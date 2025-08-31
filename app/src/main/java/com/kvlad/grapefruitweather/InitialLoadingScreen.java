package com.kvlad.grapefruitweather;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InitialLoadingScreen extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcher;

    private void proceedToMainMenu() {
        Intent intent = new Intent(InitialLoadingScreen.this, MainActivity.class);
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
                    finish();
                }
        );

        new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                Log.d("Sleep", "Sleep interruped exception: " + e.getMessage());
            }
            proceedToMainMenu();
        }).start();
    }
}