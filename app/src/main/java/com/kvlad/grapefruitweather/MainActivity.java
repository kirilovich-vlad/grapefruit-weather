package com.kvlad.grapefruitweather;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mainDatabase;
    private RecyclerView recViewSavedLocationList;
    private SavedLocationAdapter savedLocationAdapter;

    private FloatingActionButton btnAddLocation;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Processes result from AddLocationActivity.
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        savedLocationAdapter.getLastItemFromDB();
                    }
                }
        );

        mainDatabase = new DatabaseHelper(this);
        recViewSavedLocationList = findViewById(R.id.recViewSavedLocationList);
        recViewSavedLocationList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        savedLocationAdapter = new SavedLocationAdapter(this);
        recViewSavedLocationList.setAdapter(savedLocationAdapter);
        btnAddLocation = findViewById(R.id.btnAddLocation);

        // Launches AddLocationActivity when FAB is pressed.
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
                 launcher.launch(intent);
            }
        });
    }

}